package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.anywave.qpop.http.MyHttp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePWActivity extends Activity {

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_pw)
    EditText etPw;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_obtain_code)
    TextView tvObtainCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private static final String TAG = "ChangePWActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        ButterKnife.bind(this);
        etUser.setText(App.currentUser);
        etUser.setKeyListener(null);



    }

    @OnClick({R.id.back, R.id.tv_code, R.id.tv_obtain_code, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_code:
                break;
            case R.id.tv_obtain_code:
                tvObtainCode.setClickable(false);
                tvObtainCode.setTextColor(Color.parseColor("#88888888"));

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        int count = 60;
                        while (true) {
                            try {
                                sleep(1000);
                                count--;
                                final int finalCount = count;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvObtainCode.setText(finalCount + "秒后重发");
                                    }
                                });

                                if (count <= 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvObtainCode.setClickable(true);
                                            tvObtainCode.setTextColor(Color.parseColor("#52CC9A"));
                                            tvObtainCode.setText("获取验证码");
                                        }
                                    });
                                    break;
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();


                MyHttp.getCode(etUser.getText().toString());


                break;
            case R.id.tv_login:


                RequestParams params = new RequestParams(Constants.TEST);
                params.setAsJsonContent(true);

                params.addBodyParameter("phoneNumber", etUser.getText().toString());
                params.addBodyParameter("code", etCode.getText().toString());
                params.addHeader("content-type", "application/json");
//

                x.http().post(params, new Callback.CommonCallback<String>() {

                    @Override
                    public void onCancelled(Callback.CancelledException arg0) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                        Toast.makeText(ChangePWActivity.this, "输入错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onSuccess(String arg0) {

                        Log.e(TAG, "onSuccess: "+arg0 );
                        if (arg0==null){
                            App.code = etCode.getText().toString();
                            Intent intent =new Intent(ChangePWActivity.this, ChangeNextPWActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(ChangePWActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            etCode.setText(null);
                        }

                    }

                });


                break;
        }
    }
}
