package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anywave.qpop.R;
import com.anywave.qpop.http.MyHttp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClickLoginActivity extends Activity {


    @BindView(R.id.tv_click_login)
    TextView tvClickLogin;
    @BindView(R.id.ll_save)
    LinearLayout llSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_click_login, R.id.ll_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                startActivity(new Intent(ClickLoginActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.tv_click_login:
                startActivity(new Intent(ClickLoginActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.ll_save:
                startActivity(new Intent(ClickLoginActivity.this, AboutActivity.class));

//                MyHttp.wifi();
                break;
        }
    }
}
