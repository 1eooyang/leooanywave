package com.anywave.qpop.event;

/**
 * Created by leo on 2017/12/21.
 */

public class WifiStateEvent {

    private WifiStateEvent() {}

    public boolean isWifiConnect() {
        return isWifiConnect;
    }

    private void setWifiConnect(boolean wifiConnect) {
        isWifiConnect = wifiConnect;
    }

    private boolean isWifiConnect;

    public boolean isStartConnectWifi() {
        return isStartConnectWifi;
    }

    private void setStartConnectWifi(boolean startConnectWifi) {
        isStartConnectWifi = startConnectWifi;
    }

    private boolean isStartConnectWifi;

    private static WifiStateEvent Instance;

    public synchronized static WifiStateEvent getInstance(boolean isWifiConnect){
        if (Instance == null) {
            Instance = new WifiStateEvent();
        }
        Instance.setWifiConnect(isWifiConnect);
        Instance.setStartConnectWifi(false);
        return Instance;
    }


    public synchronized static WifiStateEvent getStartInstance(){
        if (Instance == null) {
            Instance = new WifiStateEvent();
        }
        Instance.setStartConnectWifi(true);
        return Instance;
    }


}
