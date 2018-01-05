package com.anywave.quanbo.http;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.anywave.quanbo.App;
import com.anywave.quanbo.activity.HomeActivity;
import com.anywave.quanbo.bean.PlayList;
import com.anywave.quanbo.bean.WifiBodelBean;
import com.anywave.quanbo.utils.MyToast;
import com.anywave.quanbo.utils.Util;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class MyHttp {

    private static final String TAG = "MyHttp";


    //  public static void getHkList(){
    //        RequestParams params = new RequestParams(Constants.hklist);
    //        params.setAsJsonContent(true);
    //
    //        params.addHeader("content-type", "application/json");
    //
    //        x.http().get(params, new Callback.CommonCallback<String>() {
    //
    //            @Override
    //            public void onCancelled(CancelledException arg0) {
    //            }
    //
    //            @Override
    //            public void onError(Throwable ex, boolean isOnCallback) {
    //            }
    //
    //            @Override
    //            public void onFinished() {
    //            }
    //
    //            @Override
    //            public void onSuccess(String arg0) {
    //            }
    //
    //        });
    //    }

    public static void getPlayList() {


        RequestParams params = new RequestParams(Constants.playlist);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {
                PlayList playList;
                playList = new Gson().fromJson(arg0, PlayList.class);
            }

        });

    }

    public static void getCode(String user) {

        RequestParams params = new RequestParams(Constants.CODE);
        params.setAsJsonContent(true);

        params.addBodyParameter("phoneNumber", user);
        params.addHeader("content-type", "application/json");

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
            }

        });
    }


    public static void postLogin(String c, final String n, final Context context) {

        //        WifiManager  wifiMan = (WifiManager)App.context.getSystemService(Context.WIFI_SERVICE) ;
        //        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        //        String mac = wifiInf.getMacAddress();

        String mac = Util.getMacAddr();
        //b4:0b:44:10:d4:33

        Log.e(TAG, "postLogin: " + mac);

        //        String code = "452115";
        //        String num = "18726209726";
        String postBody = "{\"code\":\"" + c + "\",\"phoneNumber\":\"" + n + "\",\"umac\":\"" + mac + "\"}";
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        final Request request = new Request.Builder()//创建Request 对象。
                .url(Constants.MSM_SIGNIN)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        postBody))// post json提交
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e(TAG, "onResponse: " + response.toString());
                String json = response.body().string();
                Log.e(TAG, "onClick: " + json);


                //x-auth-token
                Headers responseHeaders = response.headers();

                int responseHeadersLength = responseHeaders.size();
                for (int i = 0; i < responseHeadersLength; i++) {
                    String headerName = responseHeaders.name(i);
                    String headerValue = responseHeaders.get(headerName);
                    Log.e(TAG, "postLogin: " + "TName:" + headerName + "Value:" + headerValue);

                    if (headerName.equals("x-auth-token")) {
                        App.sp.edit().putString("token", headerValue).commit();
                        ;
                        App.token = headerValue;
                        Log.e(TAG, "App.token: " + "TName:" + headerName + "Value:" + headerValue);
                        break;
                    }
                }
                App.isLogin = true;
                App.currentUser = n;
                App.startActivity(context, HomeActivity.class);
                ((Activity) context).finish();

                //                String msg = n+"##"+;


                MyHttp.wifi();
            }
        });

    }

    public static void postLoginPhone(String pw, final String n, final Context context) {
        String mac = Util.getMacAddr();
        //        String code = "452115";
        //        String num = "18726209726";
        String postBody = "{\"phoneNumber\":\"" + n + "\",\"password\":\"" + pw + "\",\"umac\":\"" + mac + "\"}";
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        final Request request = new Request.Builder()//创建Request 对象。
                .url(Constants.PHONE_SIGNIN)
                .post(RequestBody.create(
                        MediaType.parse("application/json"),
                        postBody))// post json提交
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                Log.e(TAG, "onClick: " + json);
                //                Log.e(TAG, "onClick test: "+json.contains("error") );

                if (json.contains("error")) {

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToast.getToast(context, "密码错误");
                        }
                    });

                } else {
                    //x-auth-token
                    Headers responseHeaders = response.headers();

                    int responseHeadersLength = responseHeaders.size();
                    for (int i = 0; i < responseHeadersLength; i++) {
                        String headerName = responseHeaders.name(i);
                        String headerValue = responseHeaders.get(headerName);
                        Log.e(TAG, "postLogin: " + "TName:" + headerName + "Value:" + headerValue);

                        if (headerName.equals("x-auth-token")) {
                            App.sp.edit().putString("token", headerValue).commit();

                            App.token = headerValue;
                            Log.e(TAG, "App.token: " + "TName:" + headerName + "Value:" + headerValue);
                            break;
                        }
                    }
                    App.isLogin = true;
                    App.currentUser = n;
                    App.startActivity(context, HomeActivity.class);
                    ((Activity) context).finish();

                    MyHttp.wifi();
                }
            }
        });

    }

    public static void getLoginout() {

        RequestParams params = new RequestParams(Constants.MSM_SIGNOUT);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(Callback.CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {

            }

        });
    }


    public static void postDataWithParame() {

        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("phoneNumber", "18726209726");//传递键值对参数
        final Request request = new Request.Builder()//创建Request 对象。
                .url(Constants.CODE)
                .post(formBody.build())//传递请求体
                .header("content-type", "application/json")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.e(TAG, "onResponse: " + response.toString());
            }
        });
    }


    public static void wifi() {

        RequestParams params = new RequestParams(Constants.wifi);
        params.setAsJsonContent(true);

        String mac = Util.getMacAddr();

        Log.e(TAG, "wifi: " + mac);
        //http://220.248.15.134:8887/wifiabc/appauth?umac=11:22:33:44:55:66& type=login& token=aabbccddeafeafadfad
        //        params.addBodyParameter("umac", mac);

        params.addBodyParameter("type", "login");
        params.addBodyParameter("token", "aabbccddeafeafadfad");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {


                Log.e(TAG, "wifi: " + arg0);//
            }

        });
    }


    private static boolean haveRequested;

    public static void getWifiModel() {
        if (haveRequested) {
            return;
        }
        System.out.println("leo getWifiModel");
        haveRequested = true;

 /*       WifiBodelBean wifiBodelBean1 = new Gson().fromJson("{\"code\":\"200\",\"echo\":\"echo9\",\"gw_mac\":\"94:40:a2:1a:07:bc\",\"umac\":\"60:6d:c7:1c:96:85\",\"uip\":\"192.168.0.35\",\"mode\":\"0\"}", WifiBodelBean.class);
     //   System.out.println("leo wifi模式Bean " + wifiBodelBean);
        if (wifiBodelBean1.getCode().equals("200")) {
            if (wifiBodelBean1.getEcho().contains(String.valueOf(9))) {

                App.IsWifiModel = wifiBodelBean1.getMode().equals("1");
                System.out.println("leo wifi模式 : " +  App.IsWifiModel);
                //System.out.println("leo isWifiModel = "+ App.IsWifiModel);
            }
        }
*/

        final int i = new Random().nextInt(100000);
        RequestParams params = new RequestParams(Constants.wifimodel);
        params.setAsJsonContent(true);
        params.addBodyParameter("queryid", String.valueOf(i));

        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String wifiBodelBean) {
                System.out.println("leo wifi模式 onCache: ");
                return false;
            }

            @Override

            public void onSuccess(String wifiBodelBean) {
                if (!TextUtils.isEmpty(wifiBodelBean)) {
                    try {
                        WifiBodelBean wifiBodelBean1 = new Gson().fromJson(wifiBodelBean, WifiBodelBean.class);
                        System.out.println("leo wifi模式Bean " + wifiBodelBean);
                        if (wifiBodelBean1.getCode().equals("200")) {
                            if (wifiBodelBean1.getEcho().contains(String.valueOf(i))) {
                                App.IsWifiModel = wifiBodelBean1.getMode().equals("1");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                System.out.println("leo wifi模式 onError: " + throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                System.out.println("leo wifi模式 onCancelled: ");
            }

            @Override
            public void onFinished() {
                System.out.println("leo wifi模式 onFinished: ");


            }
        });
    }


    public static void save() {
        RequestParams params = new RequestParams(Constants.save);
        params.setAsJsonContent(true);


        //http://220.248.15.134:8887/wifiabc/appauth?umac=11:22:33:44:55:66& type=login& token=aabbccddeafeafadfad
        params.addBodyParameter("title", "测试新闻标题1");
        params.addBodyParameter("url", "http://118.31.2.247:89/news_detail.html");
        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {


                Log.e(TAG, "wifi: " + arg0);//
            }

        });
    }

   /* public static void save(String url){
        RequestParams params = new RequestParams(Constants.save);
        params.setAsJsonContent(true);


        //http://220.248.15.134:8887/wifiabc/appauth?umac=11:22:33:44:55:66& type=login& token=aabbccddeafeafadfad
        params.addBodyParameter("title", "测试新闻标题1");
        params.addBodyParameter("url", url);
        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {


                Log.e(TAG, "wifi: "+arg0 );//
            }

        });
    }*/

    public static void save(String url, String name) {
        RequestParams params = new RequestParams(Constants.save);
        params.setAsJsonContent(true);


        //http://220.248.15.134:8887/wifiabc/appauth?umac=11:22:33:44:55:66& type=login& token=aabbccddeafeafadfad
        params.addBodyParameter("title", name);
        params.addBodyParameter("url", url);
        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);

        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {


                Log.e(TAG, "wifi: " + arg0);//
            }

        });
    }


    public static void getStations(String url) {

        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {

                Log.e(TAG, "onSuccess: " + arg0);

            }

        });
    }


    /**
     * gps
     *
     * @param url
     */
    public static void get(String url) {

        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {

                Log.e(TAG, "onSuccess: " + arg0);

            }

        });
    }


    /**
     * Wi-Fi连接成功后调用
     *
     * @param url
     */
    public static void wificonnection(String url) {
        RequestParams params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.addHeader("content-type", "application/json");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
            }
        });
    }

}
