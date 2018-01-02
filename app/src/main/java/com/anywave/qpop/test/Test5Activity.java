package com.anywave.qpop.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.activity.DialogLoginActivity;
import com.anywave.qpop.activity.NewsDetailActivity;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Test5Activity extends Activity {

    @BindView(R.id.wv)
    WebView wb;
    private static final String TAG = "Test5Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);
        ButterKnife.bind(this);


        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportMultipleWindows(true);
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
//        wb.loadUrl("http://118.31.2.247:89/news_list.html");
        wb.loadUrl("http://220.248.15.134:8887/userlog/news");


        wb.setWebViewClient(new CustomWebViewClientTest());
    }
}

final class CustomWebViewClientTest extends WebViewClient {
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
