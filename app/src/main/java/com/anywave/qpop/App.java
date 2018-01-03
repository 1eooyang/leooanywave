package com.anywave.qpop;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.anywave.qpop.activity.HomeActivity;
import com.anywave.qpop.activity.SplashActivity;
import com.anywave.qpop.adapter.BusAdapter;
import com.anywave.qpop.event.OnOff;
import com.anywave.qpop.wifi.WifiConnAdmin;

import org.greenrobot.eventbus.EventBus;
import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.Map;
import java.util.Timer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

//import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/9/30 0030.
 */
public class App extends Application {
    public static Context context;
    public static String currentUser;
    public static String code;
    public static Boolean isLogin;
    public static String token="";

    //public static boolean isLiveBackFromHome;
//    public static Map<Integer,Boolean> currentDetailMap;
    public static Map<Integer,Boolean>[] currentDetailMapArray;
    public static String currentPersion;
    public static int currentLiveDetailDay;//day

    public static boolean not_native;//TYPE_NATIVE

    private static Timer mytimer;

    public static volatile boolean isWifi;//not
    public static boolean IsWifiModel = true;//not

    public static boolean isCurrentApp;

    public static boolean isSuccessWifi;//

    public static boolean isDragButton;//

    public static Activity currentWifiActivity;

    public static SharedPreferences sp ;
    private static final String TAG = "App";

    public static int currentBusStations = -1;//
    public static String currentBusStationsName ;//currentBusStationsName

    public static BusAdapter busAdapter;

    public static int currentbacktime;
    public static String wifi1 = "Xiao";
    public static String wifi2= "pop";

    public static boolean isClicked;
    public static SplashActivity splashactivity;
    public static HomeActivity homeActivity;

    private Timer timer;

    private volatile int mCount = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        isLogin =false;
        context=this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        currentDetailMapArray = new Map[7];
        sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
//        final WifiConnAdmin wifi = WifiConnAdmin.getInstance();
//        wifi.connect(WifiConnAdmin.WifiCipherType.WIFICIPHER_NOPASS);
        if (sp.getString("token",null) != null) {
            isLogin = true;
            token = sp.getString("token",null);
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mCount++;
                if (!activity.getLocalClassName().equals("activity.SplashActivity")) {
                    if (mCount == 1) {
                        EventBus.getDefault().post(OnOff.getInstance(false));
                    }
                }
                System.out.println("leo onActivityStarted : " +activity.getLocalClassName() + " mCount = " +mCount);
              /*  SharedPreferences myshared = App.context.getSharedPreferences("wificonnnection", 0);
                SharedPreferences.Editor edit = myshared.edit();
                edit.putBoolean("ws",true);
                edit.commit();*/
            }

            @Override
            public void onActivityResumed(Activity activity) {
               // System.out.println("leo onActivityResumed : " +activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
               // System.out.println("leo onActivityPaused : " +activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mCount--;

                System.out.println("leo onActivityStopped : " +activity.getLocalClassName() + " mCount = " +mCount);
                if (mCount==0) {
                    EventBus.getDefault().post(OnOff.getInstance(true));
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // System.out.println("leo onActivityDestroyed : " +activity.getLocalClassName());
            }


            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                //System.out.println("leo onActivitySaveInstanceState : " +activity.getLocalClassName());
            }
        });

    }


        @Override
    public void onTerminate() {
        super.onTerminate();
        WifiConnAdmin.getInstance().disconnectWifi();
    }

    public static void startActivity(Context context,Class clazz){
        Intent intent = new Intent(context,clazz);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity2(Class clazz){
        Intent intent = new Intent(context,clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityParams(Class clazz,String p){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("params",p);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //收藏
    public static void startActivityParams(Class clazz,String p,boolean isSave){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("params",p);
        intent.putExtra("is",isSave);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityParams(Class clazz,String p,String name){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("params",p);
        intent.putExtra("name",name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //收藏
    public static void startActivityParams(Class clazz,String p,boolean isSave,boolean isSaveActivity){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("params",p);
        intent.putExtra("is",isSave);
        intent.putExtra("isSaveActivity",isSaveActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityParams(Class clazz,boolean p){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("is",p);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityParams(Class clazz,int p){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("id",p);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityParams(Class clazz,int p,String url){
        Intent intent = new Intent(context,clazz);
        intent.putExtra("id",p);
        intent.putExtra("url",url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
