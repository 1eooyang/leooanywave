package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;
import com.anywave.qpop.http.MyHttp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogDeleteActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    String url;
    String name;
    public static boolean confirmDelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_delete);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("params");
        name = getIntent().getStringExtra("name");

        confirmDelect = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        confirmDelect = false;
    }

    @OnClick({R.id.back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confirm:

                confirmDelect = true;

                MyHttp.save(url,name);

                App.startActivity(this,SaveActivity.class);
                finish();
                break;
        }
    }
}
