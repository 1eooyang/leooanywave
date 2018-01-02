package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.http.Constants;
import com.anywave.qpop.http.MyHttp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailActivity extends Activity {

    String name = "";
    String url;
    boolean isSave;
    boolean isSaveActivity;
//    @BindView(R.id.back)
//    ImageButton back;
    @BindView(R.id.wv_detail)
    WebView wb;
    @BindView(R.id.cb_news_collection)
    CheckBox cbNewsCollection;
    private static final String TAG = "NewsDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("params");
        isSave = getIntent().getBooleanExtra("is",false);
        isSaveActivity = getIntent().getBooleanExtra("isSaveActivity",false);

        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
        wb.getSettings().setDomStorageEnabled(true);//缓存
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url)
            {
                if (url.contains("[tag]"))
                {
                    String localPath = url.replaceFirst("^http.*[tag]\\]", "");
                    try
                    {
                        InputStream is = getApplicationContext().getAssets().open(localPath);
                        Log.d(TAG, "shouldInterceptRequest: localPath " + localPath);
                        String mimeType = "text/javascript";
                        if (localPath.endsWith("css"))
                        {
                            mimeType = "text/css";
                        }
                        return new WebResourceResponse(mimeType, "UTF-8", is);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
                else
                {
                    return null;
                }

            }

        });
        wb.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e(TAG, "onProgressChanged: "+newProgress );
                if (newProgress >= 100) {
                    // 切换页面
                }
            }
        });
//        wb.loadUrl("https://m.toutiao.com");
        wb.loadUrl(url);

        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle: " + title);

                name = title;

            }

        };

        // 设置setWebChromeClient对象

        wb.setWebChromeClient(wvcc);


        wb.setWebViewClient(new CustomWebViewClientDetail());

        cbNewsCollection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        cbNewsCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbNewsCollection.isChecked()) {

                    MyHttp.save(url,name);

                }else {

                    MyHttp.save(url,name);

                }
            }
        });


        cbNewsCollection.setChecked(isSave);


        RequestParams params = new RequestParams(Constants.check);
        params.setAsJsonContent(true);
//        params.addBodyParameter("url", "http://118.31.2.247:89/news_detail.html");
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

                if (arg0.contains("true")) {
                    cbNewsCollection.setChecked(true);

                }else {
                    cbNewsCollection.setChecked(false);
                }


                Log.e(TAG, "onSuccess: " + arg0 );
            }

        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        url = getIntent().getStringExtra("params");
        isSave = getIntent().getBooleanExtra("is",false);
        isSaveActivity = getIntent().getBooleanExtra("isSaveActivity",false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isSaveActivity) {
            App.startActivity(this,SaveActivity.class);
        }
        finish();
    }

    @OnClick({R.id.back, R.id.cb_news_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

//                if (isSaveActivity) {
//                    App.startActivity(SaveActivity.class);
//                }
                finish();
                break;
            case R.id.cb_news_collection:

//                RequestParams params = new RequestParams(Constants.save);
//                params.setAsJsonContent(true);
//
//                params.addBodyParameter("title", "测试新闻标题1");
//                params.addBodyParameter("url", "http://118.31.2.247:89/news_detail.html");
//                params.addHeader("content-type", "application/json");
//                params.addHeader("x-auth-token", App.token);
//
//                x.http().post(params, new Callback.CommonCallback<String>() {
//
//                    @Override
//                    public void onCancelled(CancelledException arg0) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(String arg0) {
//
//                        Log.e(TAG, "onSuccess: "+arg0 );
//                    }
//                });
                break;
        }
    }
}

final class CustomWebViewClientDetail extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        // TODO 添加判断条件，使用页面名称 http://m.xx.com/new_detail.html
        view.loadUrl(url);

        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {

        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

        super.onReceivedError(view, request, error);
    }
}