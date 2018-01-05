package com.anywave.quanbo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anywave.quanbo.App;
import com.anywave.quanbo.LoadingView;
import com.anywave.quanbo.R;
import com.anywave.quanbo.adapter.LiveAdapter;
import com.anywave.quanbo.bean.PlayList;
import com.anywave.quanbo.event.ExitAppEvent;
import com.anywave.quanbo.event.WifiStateEvent;
import com.anywave.quanbo.http.Constants;
import com.anywave.quanbo.utils.Util;
import com.anywave.quanbo.view.dragbutton.SuspendButtonLayout;
import com.google.gson.Gson;
import com.gxw.wificonnhelperlib.utils.WifiAdmin;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

public class HomeActivity extends Activity {

    boolean isNewsLoading;

    @BindView(R.id.live)
    CheckBox live;
    @BindView(R.id.news)
    CheckBox news;
    //    @BindView(R.id.wifi)
    //    ImageButton wifi;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.lv_live)
    ListView lvLive;
    //    @BindView(R.id.bus)
    //    ImageButton bus;
    @BindView(R.id.point_live)
    ImageView pointLive;
    @BindView(R.id.point_news)
    ImageView pointNews;
    @BindView(R.id.ll_live)
    LinearLayout llLive;
    //    @BindView(R.id.ll_news)
    //    LinearLayout llNews;
    LiveAdapter liveAdapter;
    @BindView(R.id.ib_logo)
    ImageButton ibLogo;
    @BindView(R.id.ll_wifi)
    LinearLayout llWifi;
    @BindView(R.id.wb)
    WebView wb;
    @BindView(R.id.ll_wb)
    LinearLayout llWb;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    PlayList playList;
    List<PlayList.DataBean.ListBean> listBeen;

    @BindView(R.id.loading_view)
    LoadingView mLoadView;

    private Timer timer;

    private static final String TAG = "HomeActivity";
    //    @BindView(R.id.bus)
    //    DragFloatingActionButton bus;
    @BindView(R.id.layout)
    SuspendButtonLayout layout;

    private WifiAdmin admin;
    private List<ScanResult> slist;
    Context context;
    private int position;
    private long currentTimeMillis;


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        super.onDestroy();
        EventBus.getDefault().unregister(this);
        App.homeActivity = null;

    }
    private long preClickTime;

    @Override
    public void onBackPressed() {


     long ClickTime =  System.currentTimeMillis();
        if (ClickTime - preClickTime < 2000) {
            EventBus.getDefault().post(new ExitAppEvent());
            System.exit(0);
            //super.onBackPressed();
        } else {
            preClickTime = ClickTime;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MyEvent event) {
        getPlayList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WifiConnected(WifiStateEvent event) {
        if (!isShowed) {
            return;
        }
        if (event.isStartConnectWifi()) {

            Toast.makeText(App.context, "开始连接Wifi", Toast.LENGTH_LONG).show();
        } else {
           /* if (App.isLiveBackFromHome) {
                return;
            }*/



            App.isWifi = event.isWifiConnect();
            if (!App.isWifi) {
                //if (isShowed) {
                    App.startActivity(this, WifiActivity.class);
                    finish();
                //}
            } else {

              //  MyHttp.getWifiModel();


               /* if (!App.IsWifiModel) {
                    if (App.isLiveBackFromHome) {
                        App.isLiveBackFromHome = false;
                        //long l = System.currentTimeMillis() - currentTimeMillis;
                  *//*      if (l > 1100) {
                            mHandler.sendEmptyMessage(1);
                        } else {*//*
                            mHandler.sendEmptyMessageDelayed(1, 1100);
                       // }
                    }
                }*/


                getPlayList();
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.homeActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        View rootView = getLayoutInflater().inflate(R.layout.activity_dialog_bus_remind, null);

        layout.setPosition(true, 1500);

        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.contains("[tag]")) {
                    String localPath = url.replaceFirst("^http.*[tag]\\]", "");
                    try {
                        InputStream is = getApplicationContext().getAssets().open(localPath);
                        Log.d(TAG, "shouldInterceptRequest: localPath " + localPath);
                        String mimeType = "text/javascript";
                        if (localPath.endsWith("css")) {
                            mimeType = "text/css";
                        }
                        return new WebResourceResponse(mimeType, "UTF-8", is);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }

            }

        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e(TAG, "onProgressChanged: " + newProgress);
                if (newProgress >= 100) {
                    // 切换页面
                    isNewsLoading = true;
                }
            }
        });

        wb.setWebViewClient(new CustomWebViewClient());
        live.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lvLive.setVisibility(View.VISIBLE);
                    wb.setVisibility(View.GONE);
                    news.setChecked(false);
                    pointLive.setVisibility(View.VISIBLE);
                    pointNews.setVisibility(View.INVISIBLE);
                } else {
                    news.setChecked(true);
                }
            }
        });

        news.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lvLive.setVisibility(View.GONE);
                    llWb.setVisibility(View.VISIBLE);
                    wb.setVisibility(View.VISIBLE);

                    if (!isNewsLoading) {
                        //http://220.248.15.134:8887
                        //                        wb.loadUrl("http://220.248.15.134:8887/userlog/news");
                        //                        wb.loadUrl("http://192.168.0.1/userlog/news");
                        wb.loadUrl(Constants.news);
                    }


                    live.setChecked(false);
                    pointNews.setVisibility(View.VISIBLE);
                    pointLive.setVisibility(View.INVISIBLE);
                } else {
                    live.setChecked(true);
                }
            }
        });

        lvLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (!App.isLogin) {
                    App.startActivity(HomeActivity.this, DialogLoginActivity.class);
                } else {
                    position = i;
                    startToLiveDetail(position);
               }
            }
        });



        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlayList();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 结束刷新
                                srl.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        lvLive.setVisibility(View.VISIBLE);
        wb.setVisibility(View.GONE);
        news.setChecked(false);
        pointLive.setVisibility(View.VISIBLE);
        pointNews.setVisibility(View.INVISIBLE);
        //wifi();

        timer = new Timer();

    }

    private void startToLiveDetail(int i) {
        int week = Util.getDayofweek();//1-7 周日-周六
        int currentweek = -1;//0-6
        switch (week) {
            case 1:
                currentweek = 6;
                break;
            default:
                currentweek = week - 2;
                break;
        }
        App.startActivityParams(LiveDetailActivity.class, listBeen.get(i).getId(), listBeen.get(i).getPlayUrl());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    private PopupWindow mPopupWindow;

    private void showPopupWindowBasic() {
        View rootView = getLayoutInflater().inflate(R.layout.activity_dialog_bus_remind, null);
        TextView mPopupText = (TextView) rootView.findViewById(R.id.tv_confirm);
        mPopupText.setText("PopupTextBasic");

        mPopupWindow = new PopupWindow(rootView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAsDropDown(ibLogo);
    }

    private void popLogin() {
        if (!App.isLogin) {
            App.startActivity(this, DialogLoginActivity.class);
        }
    }

    private void httpMb() {
        App.startActivity(this, PersionActivity.class);
    }

    private void getPlayList() {
        System.out.println("leo getPlayList");
        RequestParams params = new RequestParams(Constants.playlist);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
                Log.e(TAG, "onCancelled: "+arg0.getLocalizedMessage());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: "+ex.getLocalizedMessage());
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: ");
            }

            @Override
            public void onSuccess(String arg0) {

                Log.e(TAG, "onSuccess: " + arg0);
                final String a = arg0.substring(9, arg0.length() - 1);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        playList = new Gson().fromJson(a, PlayList.class);
                        listBeen = playList.getData().getList();

                        liveAdapter = new LiveAdapter(App.context, (ArrayList) listBeen);
                        lvLive.setAdapter(liveAdapter);
                        liveAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {

        App.currentWifiActivity = this;

        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        isShowed = false;
    }

private boolean isShowed;

    @Override
    protected void onResume() {

        super.onResume();
        isShowed = true;
        System.out.println("leo onpostresume");

        getPlayList();
        App.isCurrentApp = true;
        MobclickAgent.onResume(this);
        currentTimeMillis = System.currentTimeMillis();

     /*   mLoadView.setStatue(LoadingView.LOADING);
        new Thread(new Runnable() {
            @Override
            public void run() {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                       *//* WifiAdmin wifiAdmin = new WifiAdmin(HomeActivity.this);
                        wifiAdmin.get*//*
                        WifiConnAdmin wca = WifiConnAdmin.getInstance();
                        boolean b=  wca.isconnection();
                        if (b) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //getPlayList();
                                    mLoadView.setStatue(LoadingView.GONE);
                                }
                            });
                            return;
                        }
                    }
                },0,200);
            }
        }).start();*/

        // mLoadView.setRefrechListener(this);


    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            startToLiveDetail(position);
            System.out.println("leo startToLiveDetail");
          //  App.isLiveBackFromHome = false;
            //finish();
        }
    };


   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {



            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        timer.purge();

        App.isCurrentApp = false;

        try {
            MobclickAgent.onPause(this);
        } catch (Exception ex) {

        }
    }

    public void refreshList() {
        getPlayList();
    }

    @OnClick({R.id.my, R.id.live, R.id.news,/* R.id.bus,*/ R.id.ll_live, R.id.ll_news, R.id.ll_wifi, R.id.ib_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_logo:
                isLogin();
                break;
            case R.id.my:

                isLogin();

                break;
            case R.id.live:
                break;
            case R.id.news:
                break;
            case R.id.ll_wifi:

               // if (App.isWifi) {
                    Toast.makeText(this, "已连接公交WiFi", Toast.LENGTH_SHORT).show();
                /*} else{
                    startActivity(new Intent(HomeActivity.this, ConnectActivity.class));
                }*/

                break;
            case R.id.ll_live:

                if (!live.isChecked()) {
                    live.setChecked(true);
                }

                break;
            case R.id.ll_news:
                if (!news.isChecked()) {
                    news.setChecked(true);
                }
                wb.loadUrl(Constants.news);
                break;
        }
    }

    public static void openBus() {

        if (App.currentBusStations >= 0) {
            App.startActivityParams(DialogBusconfirmActivity.class, App.currentBusStationsName);
        } else {
            App.startActivity2(DialogBusActivity.class);
        }
    }

    private void isLogin() {

        if (App.isLogin) {
            //            startActivity(new Intent(HomeActivity.this, PersionActivity.class));

            httpMb();


        } else {
            startActivity(new Intent(HomeActivity.this, ClickLoginActivity.class));
        }

    }

   /* //刷新界面方法
    @Override
    public void refresh() {
        mLoadView.setStatue(LoadingView.GONE);

    }*/
}

final class CustomWebViewClient extends WebViewClient {
    private static final String TAG = "CustomWebViewClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO 添加判断条件，使用页面名称 http://m.xx.com/new_detail.html
        //        view.loadUrl(url);

        Log.e(TAG, "shouldOverrideUrlLoading: " + url);

        //        if (url.contains("news_detail.html")) {

        if (!App.isLogin) {

            App.startActivity2(DialogLoginActivity.class);
        } else

            App.startActivityParams(NewsDetailActivity.class, url);

        //            return true;
        //        }

        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        //        view.loadUrl("javascript:window.java_obj.getSource('<head>'+" +
        //                "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

}

