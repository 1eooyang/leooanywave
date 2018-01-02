package com.anywave.qpop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.anywave.qpop.App;
import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBusconfirmActivity extends Activity {

    @BindView(R.id.tv_bus_zhan)
    TextView tvBusZhan;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog_busconfirm);
        ButterKnife.bind(this);

        String s = getIntent().getStringExtra("params");

        if (s == null) {
            s = "中山北路曹杨路";
        }


        tvBusZhan.setText("我们将在您即将到达“" + s +
                "”时提醒您下车，接下来尽情的观看视频，浏览资讯吧");
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {


//        App.startActivity(HomeActivity.class);
        finish();
    }
}
