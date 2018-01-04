package com.anywave.quanbo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.anywave.quanbo.event.ExitAppEvent;
import com.anywave.quanbo.event.OnOff;
import com.anywave.quanbo.event.PermissionEvent;
import com.anywave.quanbo.event.WifiStateEvent;
import com.anywave.quanbo.http.MyHttp;
import com.anywave.quanbo.utils.Util;
import com.gxw.wificonnhelperlib.utils.WifiAdmin;
import com.gxw.wificonnhelperlib.utils.WifiConnListener;
import com.gxw.wificonnhelperlib.utils.WifiConnector;
import com.gxw.wificonnhelperlib.utils.bean.WifiBeanConn;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class WifiConnectionService2 extends Service implements WifiConnListener {

    public static final int SCAN_WIFI = 1; //10 seconds
    public static final int CHECK_WIFI = 2;
    public static final int CHECK_WIFI_ON = 3;
    public static final int NOTIFY_CONDITION = 6;
    public static final int CONNECT_START = 7;
    public static final int CONNECT_SUCCESS = 8;
    public static final int CONNECT_FAILED = 9;
    private volatile int currentState = CONNECT_START;
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE};

    private WifiAdmin mWifiAdmin;

    private WifiConnector mWifiConnector;

    private long currentTime;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_WIFI:

                    ScanWifi();

                    break;
                case CHECK_WIFI:

                    checkWifi();

                    break;

                case CHECK_WIFI_ON:

                    switch (mWifiAdmin.checkState()) {
                        case WifiManager.WIFI_STATE_ENABLED:
                        case WifiManager.WIFI_STATE_ENABLING:

                            System.out.println("leo wifi打开了A CHECK_WIFI_ON");
                            if (!isInBackGround) {//如果在后台断开 或者 打开WIFI  那么不做处理
                                checkWifi();
                            } else {
                                mHandler.sendEmptyMessageDelayed(CHECK_WIFI_ON, 2000);
                            }
                            break;
                        default:
                            mHandler.sendEmptyMessageDelayed(CHECK_WIFI_ON, 2000);
                            System.out.println("leo wifi关闭中A");

                            break;


                    }

                    break;


                case NOTIFY_CONDITION:

                    mWifiConnector.notifyCondition();

                    break;

            }

        }
    };
    private wifiOnOffRecever mWifiOnOffRecever;
    private volatile boolean isInBackGround;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mWifiAdmin = new WifiAdmin(this);
        mWifiAdmin.openWifi();
        mWifiConnector = new WifiConnector(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WifiConnected(OnOff event) {
        System.out.println("leo Event : " + event.isDisConnecetWifi());
       /* App.isWifi = event.isWifiConnect();
        if (App.isWifi) {
            App.startActivity(HomeActivity.class);
           // finish();
        }*/

        if (event.isDisConnecetWifi()) {
            System.out.println("leo 退出后台");
            isInBackGround = true;


            /*String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);
            if (Util.isWifi(connectWifiSSID)) {*/
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(CHECK_WIFI_ON, 2000);
            if (!App.IsWifiModel) {
                mWifiAdmin.forget();
            }
            //  }


        } else {
            isInBackGround = false;
            System.out.println("leo 恢复前台 ");
            mHandler.removeCallbacksAndMessages(null);
            if (mWifiAdmin != null) {


                String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);//当前连接的wifi ssid

                System.out.println("leo ScanWifi : " + connectWifiSSID);

                if (Util.isWifi(connectWifiSSID)) {//如果当前
                    EventBus.getDefault().post(WifiStateEvent.getInstance(true));
                    App.isWifi = true;
                    System.out.println("leo 已经连接了 : " + connectWifiSSID);

                    mHandler.removeMessages(CHECK_WIFI);
                    mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 2000);

                } else {


                    if (TextUtils.isEmpty(connectWifiSSID)) {
                        mHandler.removeMessages(CHECK_WIFI);
                        mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 1000);
                    } else {

                        ScanWifi();
                    }

                }
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AppExited(ExitAppEvent event) {//如果是wifi模式下  用户手动退出APP  那么需要忘记我们的WIFI密码
        System.out.println("leo Event 退出APP");
        mHandler.removeCallbacksAndMessages(null);
        if (App.IsWifiModel && mWifiAdmin != null) {
            mWifiAdmin.forget();
        }

    }


    @Override
    public void onWifiConnectStart(String ssid, String bssid) {
        currentState = CONNECT_START;
        System.out.println("leo 开始连接 : " + ssid);
        EventBus.getDefault().post(WifiStateEvent.getStartInstance());

        if (mHandler != null) {
            mHandler.removeMessages(SCAN_WIFI);//开始连接状态  不扫描信号
        }

    }

    @Override
    public void onWifiConnecting(String ssid) {


        System.out.println("leo1 正在连接 : " + ssid);
        if (currentState == CONNECT_FAILED) {
            mHandler.removeMessages(SCAN_WIFI);
            mHandler.sendEmptyMessageDelayed(SCAN_WIFI, 1000);
        }

        /*mHandler.removeMessages(2);
        mHandler.sendEmptyMessageDelayed(2, 500);*/
    }

    @Override
    public void onWifiConnectSuccess(String ssid, String bssid) {
        currentState = CONNECT_SUCCESS;
        System.out.println("leo1 连接成功 : " + ssid);
        if (Util.isWifi(ssid)) {
            EventBus.getDefault().post(WifiStateEvent.getInstance(true));
            App.isWifi = true;
            MyHttp.getWifiModel();
        }
        mHandler.removeCallbacksAndMessages(null);
        //  mHandler.removeMessages(NOTIFY_CONDITION);
        // mHandler.removeMessages(CHECK_WIFI);
        mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 2000);//轮询检测wifi是否正常连接
    }

    @Override
    public void onWifiConnnectFail(String ssid, String bssid, int reason) {
        currentState = CONNECT_FAILED;
        //    mHandler.removeMessages(NOTIFY_CONDITION);

     /*   if (Util.isWifi(ssid)) {
            String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);
            System.out.println("leo onWifiConnnectFail : " +connectWifiSSID);
           // mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 2000);
            ScanWifi();
            return;
        }*/

        App.isWifi = false;
        System.out.println("leo1 连接失败 : " + ssid + " reason : " + reason);
        if (reason == 1) {
            if (Util.isWifi(ssid)) {
                EventBus.getDefault().post(WifiStateEvent.getInstance(false));
            }
            System.out.println("leo1 密码错误");
        } else {
            mWifiAdmin.clearAll();
            if (Util.isWifi(ssid)) {
                EventBus.getDefault().post(WifiStateEvent.getInstance(false));
            }

            ScanWifi();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        mWifiOnOffRecever = new wifiOnOffRecever();
        registerReceiver(mWifiOnOffRecever, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));


        String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);//当前连接的wifi ssid

        System.out.println("leo ScanWifi : " + connectWifiSSID);

        if (Util.isWifi(connectWifiSSID)) {//如果当前

            System.out.println("leo 已经连接了 : " + connectWifiSSID);
            mHandler.removeMessages(CHECK_WIFI);
            mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 2000);
            EventBus.getDefault().post(WifiStateEvent.getInstance(true));

            MyHttp.getWifiModel();

        } else {

            ScanWifi();

        }


        return START_STICKY;
    }


    private class wifiOnOffRecever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                //获取当前的wifi状态int类型数据
                int mWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (mWifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:

                        System.out.println("leo wifi已打开");
                        // EventBus.getDefault().post(OnWifiCloseEvent.getInstance(false));
                        //已打开
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        System.out.println("leo wifi打开中");
                        //打开中
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        System.out.println("leo wifi已关闭");
                        //已关闭
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        System.out.println("leo wifi关闭中");
                        if (mHandler != null) {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.sendEmptyMessageDelayed(CHECK_WIFI_ON, 2000);
                        }

                        EventBus.getDefault().post(WifiStateEvent.getInstance(false));

                        //关闭中
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        System.out.println("leo wifi未知");
                        //未知
                        break;
                }
            }
        }
    }

    private int requestTime;

    public void checkWifi() {

        String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);//当前连接的wifi ssid

        System.out.println("leo ScanWifi : " + connectWifiSSID);

        if (Util.isWifi(connectWifiSSID)) {//如果当前
            MyHttp.getWifiModel();
            System.out.println("leo 已经连接了 : " + connectWifiSSID);
            mHandler.removeMessages(CHECK_WIFI);
            mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 2000);

        } else {


            if (TextUtils.isEmpty(connectWifiSSID)) {
                requestTime ++;
                if (requestTime >= 5) {
                    requestTime = 0;
                    ScanWifi();
                } else {
                    mHandler.removeMessages(CHECK_WIFI);
                    mHandler.sendEmptyMessageDelayed(CHECK_WIFI, 1000);
                }

            } else {

                ScanWifi();
            }

        }

    }


    private void ScanWifi() {

        //如果没有连接  那么就扫描附近的wifi信号
        mWifiAdmin.startScan();

        ArrayList<ScanResult> wifiListAll = mWifiAdmin.getWifiListAll();
        //   System.out.println("leo wifiListAll : " + wifiListAll.toString());
        if (wifiListAll.size() == 0) {
            System.out.println("leo 没有扫描到信号或者权限 ");

            mWifiAdmin.openWifi();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (App.currentWifiActivity != null && !App.currentWifiActivity.getClass().getName().contains("SplashActivity")) {
                        EventBus.getDefault().post(new PermissionEvent());
                    }
                    // return;
                }
            }

            mHandler.sendEmptyMessageDelayed(SCAN_WIFI, 2000);

            //  }


        } else {
            //检查是否有qpopwifi
            boolean hasResult = false;
            for (ScanResult scanResult : wifiListAll) {
                //if (Util.isWifi(scanResult.SSID)) {
                if (!TextUtils.isEmpty(scanResult.SSID) && Util.isWifi(scanResult.SSID)) {
                    //检测到了
                    WifiConfiguration exsits = mWifiAdmin.isExsits(scanResult.SSID);
                    // 是否有历史配置
                    hasResult = true;
                    if (exsits != null) {

                        mWifiConnector.addWifiConfig(exsits).connectWithConfig(this);

                    } else {
                        //  mWifiConnector.notifyDisConnect();

                        if (!mWifiAdmin.hasPwd(scanResult)) {
                            //不需要密码连接
                            System.out.println("leo1 不需要密码连接 : " + scanResult);
                            mWifiConnector.addWifiBeanConn(new WifiBeanConn("", scanResult)).connectWithSSID(this);

                        } else {
                            //需要密码连接
                            System.out.println("leo1 需要密码连接 : " + scanResult);
                            mWifiConnector.addWifiBeanConn(new WifiBeanConn("wwkj2017", scanResult)).connectWithSSID(this);

                        }
                    }
                    break;
                }

            }
            if (!hasResult) {
                System.out.println("leo 处理没有扫描到指定wifi");
                mHandler.removeMessages(SCAN_WIFI);
                mHandler.sendEmptyMessageDelayed(SCAN_WIFI, 2000);
                //  EventBus.getDefault().post(WifiStateEvent.getInstance(false));
            }
        }

    }


    public void disConnectWifi() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        String connectWifiSSID = mWifiAdmin.getConnectWifiSSID(this);
        if (Util.isWifi(connectWifiSSID)) {
            mWifiAdmin.forget();
        }
    }

    public void reConnectWifi() {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(3);
        }
    }


    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        EventBus.getDefault().unregister(this);

        if (mWifiConnector != null)
            mWifiConnector.removeReceive();

        if (mWifiOnOffRecever != null) {
            unregisterReceiver(mWifiOnOffRecever);
        }

        if (App.IsWifiModel && mWifiAdmin != null) {
            mWifiAdmin.forget();
        }


        super.onDestroy();
    }
}
