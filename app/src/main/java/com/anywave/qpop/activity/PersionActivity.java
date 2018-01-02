package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.bean.Members;
import com.anywave.qpop.http.Constants;
import com.anywave.qpop.utils.CleanMessage;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersionActivity extends Activity {

//    @BindView(R.id.back)
//    ImageButton back;
    @BindView(R.id.ll_logout)
    LinearLayout llLogout;
    @BindView(R.id.ll_save)
    LinearLayout llSave;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.pw)
    TextView pw;
    @BindView(R.id.ll_addpw)
    LinearLayout llAddpw;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    Boolean isChangePw;
    @BindView(R.id.current_user)
    TextView currentUser;
    Members members;
    private static final String TAG = "PersionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_persion);
        ButterKnife.bind(this);


//        if (App.currentPersion == null) {
//            App.currentPersion = getIntent().getStringExtra("params");
//        }else {
//
//        }
//
//        httpMb(App.currentPersion);

        try {
            tvClear.setText(CleanMessage.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (App.currentPersion == null) {
            RequestParams params = new RequestParams(Constants.MEMBERS);
            params.setAsJsonContent(true);

            params.addHeader("content-type", "application/json");
            params.addHeader("x-auth-token", App.token);

            x.http().get(params, new Callback.CommonCallback<String>() {

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


                    App.currentPersion = arg0;

                    httpMb(App.currentPersion);

                }
            });
        }else {
            httpMb(App.currentPersion);

        }



    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onResume() {
        super.onResume();
        App.isCurrentApp = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.isCurrentApp = false;
    }

    private void httpMb(String data) {
        members = new Gson().fromJson(data,Members.class);
        final String num = members.getPhoneNumber();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                App.currentUser = members.getPhoneNumber();
                String start = num.substring(0,3);
                String end = num.substring(7);
                currentUser.setText(start+"****"+end);

                if (members.isHasPassword()) {
                    pw.setText("修改密码");
                }else {
                    pw.setText("添加密码");
                }
            }
        });
       /* RequestParams params = new RequestParams(Constants.MEMBERS);
        params.setAsJsonContent(true);

        params.addHeader("content-type", "application/json");
        params.addHeader("x-auth-token", App.token);

        x.http().get(params, new Callback.CommonCallback<String>() {

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
//                Toast.makeText(PersionActivity.this, "success"+arg0, Toast.LENGTH_SHORT).show();

                Log.e(TAG, "onSuccess: "+arg0 );

                members = new Gson().fromJson(arg0,Members.class);
                final String num = members.getPhoneNumber();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        App.currentUser = members.getPhoneNumber();
                        String start = num.substring(0,3);
                        String end = num.substring(7);
                        currentUser.setText(start+"****"+end);

                        if (members.isHasPassword()) {
                            pw.setText("修改密码");
                        }else {
                            pw.setText("添加密码");
                        }
                    }
                });
            }


        });*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isChangePw = intent.getBooleanExtra("change_pw", false);
        if (isChangePw) {
            pw.setText("修改密码");
        }
    }


    @OnClick({R.id.ll_save, R.id.ll_clear, R.id.ll_addpw, R.id.ll_about, R.id.ll_logout, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_save:
                startActivity(new Intent(PersionActivity.this, SaveActivity.class));
//                finish();
                break;
            case R.id.ll_clear:
                tvClear.setText("0KB");
                CleanMessage.clearAllCache(App.context);
                Toast.makeText(this, "已清除缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_addpw:
                if (pw.getText().equals("修改密码")) {
                    startActivity(new Intent(PersionActivity.this, ChangePWActivity.class));
                } else {
                    startActivity(new Intent(PersionActivity.this, AddPWActivity.class));
                }
                break;

            case R.id.ll_about:
                startActivity(new Intent(PersionActivity.this, AboutActivity.class));

                break;
            case R.id.ll_logout:
                startActivity(new Intent(PersionActivity.this, DialogActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
