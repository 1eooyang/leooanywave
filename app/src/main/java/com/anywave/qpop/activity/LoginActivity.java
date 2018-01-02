package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.http.Constants;
import com.anywave.qpop.http.MyHttp;
import com.anywave.qpop.utils.MyToast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.ib_close)
    ImageButton ibClose;
    @BindView(R.id.tv_login_code)
    TextView tvLoginCode;
    @BindView(R.id.tv_login_pw)
    TextView tvLoginPw;
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
    @BindView(R.id.fl_code)
    FrameLayout flCode;
    @BindView(R.id.tv_login_pw_in)
    TextView tvLoginPwIn;
    @BindView(R.id.fl_pw)
    FrameLayout flPw;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_look)
    TextView tvLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ib_close, R.id.tv_login_code, R.id.tv_login_pw, R.id.tv_obtain_code, R.id.tv_login, R.id.tv_look,R.id.fl_pw,R.id.tv_login_pw_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.ib_close:
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                break;
            case R.id.tv_login_code:
                tvCode.setText("验证码");
                tvObtainCode.setVisibility(View.VISIBLE);
//                etPw.setHint("请输入验证码");
                tvHint.setVisibility(View.INVISIBLE);
                tvLoginCode.setTextColor(Color.parseColor("#FF52CC9A"));
                tvLoginPw.setTextColor(Color.parseColor("#FF000000"));

                etCode.setVisibility(View.VISIBLE);
                etPw.setVisibility(View.GONE);

                flCode.setVisibility(View.VISIBLE);
//                tvLogin.setVisibility(View.VISIBLE);
                flPw.setVisibility(View.GONE);

                break;
            case R.id.tv_login_pw:

                tvCode.setText("密   码");
                tvObtainCode.setVisibility(View.GONE);
//                etPw.setHint("请输入密码");
                tvHint.setVisibility(View.VISIBLE);
                tvLoginPw.setTextColor(Color.parseColor("#FF52CC9A"));
                tvLoginCode.setTextColor(Color.parseColor("#FF000000"));

                etPw.setVisibility(View.VISIBLE);
                etCode.setVisibility(View.GONE);

                flCode.setVisibility(View.GONE);
//                tvLogin.setVisibility(View.GONE);
                flPw.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_obtain_code:
                if (etUser.getText().toString().length() != 11) {
                    MyToast.getToast(this,"手机号输入错误");
                } else {


                    tvObtainCode.setClickable(false);
                tvObtainCode.setTextColor(Color.parseColor("#44000000"));

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

//                MyHttp.getCode(etUser.getText().toString());
                RequestParams params = new RequestParams(Constants.CODE);
                params.setAsJsonContent(true);

                params.addBodyParameter("phoneNumber", etUser.getText().toString());
                params.addHeader("content-type", "application/json");


                Callback.CommonCallback<String> commonCallback = new Callback.CommonCallback<String>() {

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


                    }

                };
                Callback.Cancelable post = x.http().post(params, commonCallback);

                Log.e(TAG, "getCode: ");
        }

                break;
            case R.id.tv_login:

                if (etCode.getText().toString().length() == 6) {
                    MyHttp.postLogin(etCode.getText().toString(), etUser.getText().toString(), this);
                } else {
                    Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_login_pw_in:
                if (etUser.getText().toString().length() != 11) {
                    Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    MyHttp.postLoginPhone(etPw.getText().toString(), etUser.getText().toString(), this);
                }
                break;

            case R.id.tv_look:

                App.startActivity(this,HomeActivity.class);
                break;
        }
    }

}
