package com.anywave.quanbo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.anywave.quanbo.App;
import com.anywave.quanbo.R;
import com.anywave.quanbo.WifiConnectionService2;
import com.anywave.quanbo.event.WifiStateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

//leoyang

public class SplashActivity extends Activity {


    // 要申请的权限
   private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        setContentView(R.layout.activity_splash);
        App.currentWifiActivity = this;
        EventBus.getDefault().register(this);

        Intent intent = new Intent(SplashActivity.this, WifiConnectionService2.class);
        App.context.startService(intent);
        //  System.out.println("leo startWifiConnectionService");
        // final WifiAdmin wifiAdmin = new WifiAdmin(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 321);
                return;
            }
        }
        jump();
    }

    private void jump() {



        Observable.timer(4, TimeUnit.SECONDS).
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (App.isWifi) {

                            if (App.isLogin) {
                                App.startActivity(SplashActivity.this, HomeActivity.class);
                            } else {
                                App.startActivity(SplashActivity.this, LoginActivity.class);
                            }

                        } else {
                            App.startActivity(SplashActivity.this,WifiActivity.class);
                        }

                        SplashActivity.this.finish();

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WifiConnected(WifiStateEvent event) {
        if (event.isStartConnectWifi()) {
            //Toast.makeText(App.context, "开始连接wifi", Toast.LENGTH_SHORT).show();
        } else {

            App.isWifi = event.isWifiConnect();
           /* if (App.isWifi) {
                MyHttp.getWifiModel();
            }*/
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (Build.VERSION.SDK_INT >= 23) {
            jump();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
