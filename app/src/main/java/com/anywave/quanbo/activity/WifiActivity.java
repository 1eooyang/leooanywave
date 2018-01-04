package com.anywave.quanbo.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anywave.quanbo.App;
import com.anywave.quanbo.R;
import com.anywave.quanbo.event.ExitAppEvent;
import com.anywave.quanbo.event.PermissionEvent;
import com.anywave.quanbo.event.WifiStateEvent;
import com.anywave.quanbo.http.MyHttp;
import com.anywave.quanbo.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WifiActivity extends Activity {

    @BindView(R.id.my)
    ImageButton my;
    @BindView(R.id.live)
    ImageButton live;
    @BindView(R.id.news)
    ImageButton news;
    @BindView(R.id.wifi)
    ImageButton wifi;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_connect)
    TextView tvConnect;

    private static final String TAG = "WifiActivity";
    private boolean hasRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.currentWifiActivity = this;
        // if (Util.isWifi(getConnectWifiSsid())){
        if (App.isWifi) {

            App.startActivity(this, HomeActivity.class);
            finish();
        }
        //  }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        App.isCurrentApp = true;

        /*if (App.isWifi) {
            App.startActivity(HomeActivity.class);
            finish();
        }*/

    }


    @Override
    protected void onPause() {
        super.onPause();

        App.isCurrentApp = false;

        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "onDestroy: ");

        App.isWifi = false;
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WifiConnected(WifiStateEvent event) {
        if (event.isStartConnectWifi()) {
            Toast.makeText(this, "正在连接wifi", Toast.LENGTH_SHORT).show();
        } else {

            App.isWifi = event.isWifiConnect();
            if (App.isWifi) {
                MyHttp.getWifiModel();
                App.startActivity(this, HomeActivity.class);
                finish();
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PermissionEvent(PermissionEvent event) {
        if (!hasRequest) {
            hasRequest = true;
            PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, cccc);
        }
    }

    private PermissionUtils.PermissionGrant cccc = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            System.out.println("leo onPermissionGranted : " + requestCode);
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23)
            PermissionUtils.requestPermissionsResult(this,requestCode,permissions,grantResults,cccc);
    }

    private long preClickTime;

    @Override
    public void onBackPressed() {

        long ClickTime =  System.currentTimeMillis();
        if (ClickTime - preClickTime < 2000) {

            EventBus.getDefault().post(new ExitAppEvent());

            super.onBackPressed();
        } else {
            preClickTime = ClickTime;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_connect)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
        startActivity(intent);
        //        finish();
    }

    public static String getConnectWifiSsid() {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.e("wifiInfo", wifiInfo.toString());
        Log.e("SSID", wifiInfo.getSSID());

        return wifiInfo.toString();
    }
}
