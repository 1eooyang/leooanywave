package com.anywave.quanbo.event;

/**
 * Created by Administrator on 2017/12/23/023.
 */

public class OnWifiCloseEvent {

    private OnWifiCloseEvent() {}


    public boolean isClosed() {
        return isClosed;
    }

    private void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    private boolean isClosed;


    private static OnWifiCloseEvent Instance;

    public synchronized static OnWifiCloseEvent getInstance(boolean isClosed){
        if (Instance == null) {
            Instance = new OnWifiCloseEvent();
        }
        Instance.setClosed(isClosed);
        return Instance;
    }


}
