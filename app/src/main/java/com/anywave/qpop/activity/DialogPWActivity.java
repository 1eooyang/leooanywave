package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogPWActivity extends Activity {

    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_pw);
        ButterKnife.bind(this);
        this.setFinishOnTouchOutside(false);
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        App.isLogin = false;
        startActivity(new Intent(DialogPWActivity.this, ClickLoginActivity.class));
        finish();
    }
}
