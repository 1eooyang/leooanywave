package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:


                App.isLogin = false;
                App.currentPersion =null;
                App.sp.edit().putString("token",null).apply();
                startActivity(new Intent(DialogActivity.this, ClickLoginActivity.class));
                finish();

             /*   WifiConnAdmin.getInstance().disconnectWifi();
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
                        MyToast.getToast("注销失败");
                    }

                    @Override
                    public void onFinished() {
                    }

                    @Override
                    public void onSuccess(String arg0) {

                    }
                });*/

                //


                break;
        }
    }
}
