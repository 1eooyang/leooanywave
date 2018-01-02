package com.anywave.qpop.event;

/**
 * Created by leo on 2017/12/21.
 */

public class onoffEvent {

    private onoffEvent() {}


    public boolean isDisConnecetWifi() {
        return disConnecetWifi;
    }

    private void setDisConnecetWifi(boolean disConnecetWifi) {
        this.disConnecetWifi = disConnecetWifi;
    }

    private boolean disConnecetWifi;


    private static onoffEvent Instance;

    public synchronized static onoffEvent getInstance(boolean disConnecetWifi){
        if (Instance == null) {
            Instance = new onoffEvent();
        }
        Instance.setDisConnecetWifi(disConnecetWifi);
        return Instance;
    }
}
