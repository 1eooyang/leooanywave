package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.http.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNextPWActivity extends Activity {

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.et_pw)
    EditText etPw;
    @BindView(R.id.tv_code)
    TextView tvCode;
//    @BindView(R.id.et_pw_confirm)
//    EditText etPwConfirm;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private static final String TAG = "ChangeNextPWActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_next_pw);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_code, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                break;
            case R.id.tv_login:


                if (!etPw.getText().toString().equals(etCode.getText().toString())) {
                    Toast.makeText(this, "密码不相同", Toast.LENGTH_SHORT).show();

                }else if( etPw.getText().length()<6){
                    Toast.makeText(this, "密码大于等于6位数", Toast.LENGTH_SHORT).show();
                }else {

                    RequestParams params = new RequestParams(Constants.updatepassword);
                    params.setAsJsonContent(true);

                    params.addBodyParameter("phoneNumber", App.currentUser);
                    params.addBodyParameter("password", etPw.getText().toString());
                    params.addBodyParameter("code", App.code);
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

                            Log.e(TAG, "onSuccess: " + arg0 );
                            if (arg0 == null){
                                Intent intent =new Intent(ChangeNextPWActivity.this, DialogPWActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(ChangeNextPWActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                                etCode.setText(null);
                            }
                        }
                    });

            }

                break;
        }
    }
}
