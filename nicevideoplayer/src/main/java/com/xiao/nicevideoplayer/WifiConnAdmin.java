package com.xiao.nicevideoplayer;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;


import java.util.List;
import java.util.Random;

public class WifiConnAdmin {

    private static final String TAG = WifiConnAdmin.class
            .getSimpleName();

    private WifiManager wifiManager;

    private int error_times = 0;

    private int max_allows_times = 5;

    private boolean waitProc = true;
    private int netID;

    public void close() {
        if (Util.isWifi(wifiManager.getConnectionInfo().getSSID())) {
            wifiManager.setWifiEnabled(false);
            wifiManager.disconnect();
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

    public void init(Context context) {
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

    }



    // 构造函数
    private WifiConnAdmin() {
    }

    /**
     * 提供一个外部接口，传入要连接的无线网
     * @param type
     */
    public synchronized void connect(WifiCipherType type) {
        WifiInfo info = wifiManager.getConnectionInfo();

        //已经连接
        if (Util.isWifi(info.getSSID())) {
            //
        } else {
            Thread thread = new Thread(new ConnectRunnable(type));
            thread.start();
        }
    }

    /**
     * 查看以前是否也配置过这个网络
     * @return
     */
    private WifiConfiguration isExsits() {
        List<WifiConfiguration> existingConfigs = wifiManager
                .getConfiguredNetworks();
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

            config.SSID = "\"" + "Qpop_2_ZT" + "\"";
        } else {
            config.SSID = "\"" + "Qpop_1_ZT" + "\"";
        }
        // config.SSID = SSID;
        // nopass
        if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(KeyMgmt.NONE);
        }
        return config;
    }

    /**
     * 打开wifi功能
     * @return
     */
    private boolean openWifi() {
        boolean bRet = true;
        if (!wifiManager.isWifiEnabled()) {
            bRet = wifiManager.setWifiEnabled(true);
        }
        return bRet;
    }

    /**
     * 关闭WIFI
     */
    private void closeWifi() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
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
            String sid = wifiManager.getConnectionInfo().getSSID();
            if (Util.isWifi(sid)) {
                return;
            }
            // 打开wifi
            openWifi();
            // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
            // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
            while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                try {
                    // 为了避免程序一直while循环，让它睡个100毫秒检测……
                    Thread.sleep(100);

                } catch (InterruptedException ie) {
                    Log.e(TAG, ie.toString());
                }
            }

            WifiConfiguration tempConfig = isExsits();

            if (tempConfig != null) {
                // wifiManager.removeNetwork(tempConfig.networkId);
                netID = tempConfig.networkId;
                boolean b = wifiManager.enableNetwork(tempConfig.networkId,
                        true);
                if (b) {
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
                boolean connected = wifiManager.reconnect();
                if (enabled && connected) {
                } else {
                    procError();
                }
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
            error_times = 0;
        }
    }


    /**
     * 禁止
     */
    public void disconnectWifi() {
        wifiManager.disableNetwork(netID);
    }

    public boolean isconnection() {
        String sid = wifiManager.getConnectionInfo().getSSID();
        if (sid==null) {
            return false;
        }
        if (Util.isWifi(sid)) {
            return true;
        }
        return false;
    }
}