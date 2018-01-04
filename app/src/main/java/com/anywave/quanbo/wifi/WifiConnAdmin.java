package com.anywave.quanbo.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.anywave.quanbo.App;
import com.anywave.quanbo.R;
import com.anywave.quanbo.activity.MyEvent;
import com.anywave.quanbo.activity.WifiActivity;
import com.anywave.quanbo.http.Constants;
import com.anywave.quanbo.http.MyHttp;
import com.anywave.quanbo.utils.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

public class WifiConnAdmin {

    private static final String TAG = WifiConnAdmin.class
            .getSimpleName();

    private int error_times = 0;

    private int max_allows_times = 5;

    private boolean waitProc = true;

    private int joinNum = 0;
    private int netID;

    /**
     *
     */
    public synchronized void close() {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);
        if (Util.isWifi(wifiManager.getConnectionInfo().getSSID())) {
            wifiManager.removeNetwork(netID);
            wifiManager.saveConfiguration();
            wifiManager.disconnect();
            wifiManager.setWifiEnabled(false);
        }
    }


    // 定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
    public enum WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    private static WifiConnAdmin instance;

    /**
     * 获得GetInstance
     * @return
     */
    public static WifiConnAdmin getInstance() {
        if (instance!=null) return instance;
        synchronized (WifiConnAdmin.class) {
            if (instance==null) {
                instance = new WifiConnAdmin();
            }
        }
        return instance;
    }


    /**
     * 提供一个外部接口，传入要连接的无线网
     * @param type
     */
    public synchronized void connect(WifiCipherType type) {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        System.out.println("leo connect : " + info.getSSID());
        boolean connected = Util.isWifi(info.getSSID());
        //已经连接
        if (!connected) {
            if (joinNum==0) {
                joinNum++;
                Thread thread = new Thread(new ConnectRunnable(type));
                thread.start();
            }
        }
    }

    /**
     * 查看以前是否也配置过这个网络
     * @return
     */
    private WifiConfiguration isExsits() {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);

        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
        if (existingConfigs == null) {
            return null;
        }
        for (WifiConfiguration existingConfig : existingConfigs) {

            if (Util.isWifi(existingConfig.SSID)) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 创建Wifi Info
     * @param Type
     * @return
     */
    private WifiConfiguration createWifiInfo(WifiCipherType Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        Random random = new Random();
        boolean b = random.nextBoolean();
        if (b) {
            config.SSID = "\"" + App.context.getString(R.string.wifi1) + "\"";
        } else {
            config.SSID = "\"" + App.context.getString(R.string.wifi2) + "\"";
        }
        // config.SSID = SSID;
        // nopass
        if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        return config;
    }

    /**
     * 打开wifi功能
     * @return
     */
    private boolean openWifi() {
        boolean bRet = true;
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);

        if (!wifiManager.isWifiEnabled()) {
            bRet = wifiManager.setWifiEnabled(true);
        }
        return bRet;
    }

    /**
     * 关闭WIFI
     */
    private void closeWifi() {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);

        wifiManager.disconnect();
    }

    /**
     *
     */
    class ConnectRunnable implements Runnable {

        private WifiCipherType type;

        public ConnectRunnable(WifiCipherType type) {
            this.type = type;
        }

        @Override
        public synchronized void run() {
            try {
                WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);

                String sid = wifiManager.getConnectionInfo().getSSID();
               // System.out.println("leo ConnectionSSID = " + sid);
                if (Util.isWifi(sid)) {
                    return;
                }
               // System.out.println("leo1 openwifi");
                // 打开wifi
                openWifi();
                // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
                // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
                while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                    try {
                        // 为了避免程序一直while循环，让它睡个100毫秒检测……
                        Thread.sleep(100);
                      //  System.out.println("leo2 openwifi");

                    } catch (InterruptedException ie) {
                      //  System.out.println("leo3 "+ ie.toString());
                       // Log.e(TAG, ie.toString());
                    }
                }
                WifiConfiguration tempConfig = isExsits();

                if (tempConfig != null) {
                    // wifiManager.removeNetwork(tempConfig.networkId);
                    netID = tempConfig.networkId;
                    boolean b = wifiManager.enableNetwork(tempConfig.networkId, true);
                    int wifiState = wifiManager.getWifiState();
                    System.out.println("leo enableNetwork ssid = "+ tempConfig.networkId + " enable = " + b + " wifistate = " + (wifiState==WifiManager.WIFI_STATE_ENABLED));
                    if (b && wifiState==WifiManager.WIFI_STATE_ENABLED) {
                        EventBus.getDefault().post(new MyEvent());
                        MyHttp.wificonnection(Constants.wificonnection);
                    } else {
                        procError();
                    }
                } else {
                    WifiConfiguration wifiConfig = createWifiInfo(type);
                    if (wifiConfig == null) {
                        return;
                    }
                    netID = wifiManager.addNetwork(wifiConfig);
                    boolean enabled = wifiManager.enableNetwork(netID, true);
                    System.out.println("leo enableNetwork ssid = "+ wifiConfig.SSID + " enable = " + enabled );
                    boolean connected = wifiManager.reconnect();
                    System.out.println("leo wifiManager.reconnect() : " + connected);
                    if (enabled && connected) {
                        EventBus.getDefault().post(new MyEvent());
                        MyHttp.wificonnection(Constants.wificonnection);
                    } else {
                        procError();
                    }
               }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                joinNum--;
            }
        }

    }

    /**
     *
     * @param wepKey
     * @return
     */
    private static boolean isHexWepKey(String wepKey) {
        final int len = wepKey.length();

        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }

        return isHex(wepKey);
    }

    /**
     *
     * @param key
     * @return
     */
    private static boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f')) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取ssid的加密方式
     * @param context
     * @param ssid
     * @return
     */
    public static WifiCipherType getCipherType(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        List<ScanResult> list = wifiManager.getScanResults();

        for (ScanResult scResult : list) {

            if (!TextUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                String capabilities = scResult.capabilities;
                // Log.i("hefeng","capabilities=" + capabilities);

                if (!TextUtils.isEmpty(capabilities)) {

                    if (capabilities.contains("WPA")
                            || capabilities.contains("wpa")) {
                        Log.i("hefeng", "wpa");
                        return WifiCipherType.WIFICIPHER_WPA;
                    } else if (capabilities.contains("WEP")
                            || capabilities.contains("wep")) {
                        Log.i("hefeng", "wep");
                        return WifiCipherType.WIFICIPHER_WEP;
                    } else {
                        Log.i("hefeng", "no");
                        return WifiCipherType.WIFICIPHER_NOPASS;
                    }
                }
            }
        }
        return WifiCipherType.WIFICIPHER_INVALID;
    }

    /**
     * 处理错误
     */
    private void procError() {
        error_times ++ ;
        if (error_times == max_allows_times) {
            App.startActivity2(WifiActivity.class);
            error_times = 0;
        }
    }


    /**
     * 禁止
     */
    public void disconnectWifi() {
       // System.out.println("leo disconnectWifi");
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.disableNetwork(netID);
    }

    public boolean isconnection() {
        WifiManager wifiManager = (WifiManager) App.context.getSystemService(Context.WIFI_SERVICE);
        String sid = wifiManager.getConnectionInfo().getSSID();
     //   System.out.println("leo isconnection : " + sid + "  enable : "+(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED));
        return sid != null && Util.isWifi(sid) && wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }
}