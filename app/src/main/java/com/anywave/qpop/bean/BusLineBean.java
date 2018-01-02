package com.anywave.qpop.bean;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class BusLineBean {


    /**
     * lck : 已锁定
     * longti : 121.23822
     * lati : 31.10414
     * position : 东经:121.23822 北纬:31.10414
     */

    private String lck;
    private double longti;
    private double lati;
    private String position;

    public String getLck() {
        return lck;
    }

    public void setLck(String lck) {
        this.lck = lck;
    }

    public double getLongti() {
        return longti;
    }

    public void setLongti(double longti) {
        this.longti = longti;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
