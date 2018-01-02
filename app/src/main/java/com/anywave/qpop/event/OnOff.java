package com.anywave.qpop.event;

/**
 * Created by Administrator on 2017/12/21.
 */

public class OnOff {

    private OnOff() {}


    public boolean isDisConnecetWifi() {
        return disConnecetWifi;
    }

    private void setDisConnecetWifi(boolean disConnecetWifi) {
        this.disConnecetWifi = disConnecetWifi;
    }

    private boolean disConnecetWifi;


    private static OnOff Instance;

    public synchronized static OnOff getInstance(boolean disConnecetWifi){
        if (Instance == null) {
            Instance = new OnOff();
        }
        Instance.setDisConnecetWifi(disConnecetWifi);
        return Instance;
    }

}
