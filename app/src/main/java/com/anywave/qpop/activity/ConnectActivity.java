package com.anywave.qpop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.anywave.qpop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConnectActivity extends Activity {


    @BindView(R.id.tv_connect)
    TextView tvConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.close, R.id.tv_connect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:

                startActivity(new Intent(ConnectActivity.this, HomeActivity.class));

                break;
            case R.id.tv_connect:
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);
                finish();
                break;
        }
    }
}
