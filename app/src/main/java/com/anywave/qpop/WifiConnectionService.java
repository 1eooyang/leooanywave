package com.anywave.qpop;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.anywave.qpop.wifi.WifiConnAdmin;

import java.util.Timer;
import java.util.TimerTask;

public class WifiConnectionService extends Service implements SharedPreferences.OnSharedPreferenceChangeListener{
    private Timer timer;
    private boolean ws;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences myshared = App.context.getSharedPreferences("wificonnnection", 0);

        myshared.registerOnSharedPreferenceChangeListener(this);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               // System.out.println("leo schedule");
                openOrClose();
            }
        },0,1000);

    }


 /*   @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WifiConnAdmin.getInstance().connect(WifiConnAdmin.WifiCipherType.WIFICIPHER_NOPASS);






        return super.onStartCommand(intent, flags, startId);



    }*/




    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //连接或拒绝连接
        if (key.equals("ws")) {
            ws = sharedPreferences.getBoolean("ws",false);
            if (ws) {
                WifiConnAdmin.getInstance().connect(WifiConnAdmin.WifiCipherType.WIFICIPHER_NOPASS);
            } else {
                WifiConnAdmin.getInstance().close();
            }
        }
    }

    /**
     *
     */
    public void openOrClose() {
        SharedPreferences myshared = App.context.getSharedPreferences("wificonnnection", 0);
        boolean b = myshared.getBoolean("ws",false);
        System.out.println("leo ws = " +b);
        if (b) {
            WifiConnAdmin.getInstance().connect(WifiConnAdmin.WifiCipherType.WIFICIPHER_NOPASS);
        } else {
            WifiConnAdmin.getInstance().close();
        }
    }
}
