package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogLoginActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:

                App.startActivity(this,LoginActivity.class);
                finish();
                break;
        }
    }
}
