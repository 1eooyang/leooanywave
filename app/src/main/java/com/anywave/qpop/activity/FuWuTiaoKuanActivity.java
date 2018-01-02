package com.anywave.qpop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.http.Constants;
import com.anywave.qpop.http.MyHttp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FuWuTiaoKuanActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fu_wu_tiao_kuan);
        ButterKnife.bind(this);

        webview.loadUrl("file:///android_asset/userguide.html");
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
//                startActivity(new Intent(AboutActivity.this, PersionActivity.class));
                finish();
                break;
        }
    }
}
