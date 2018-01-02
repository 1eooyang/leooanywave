package com.anywave.qpop.bean;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class BusBean {


    /**
     * id : 1
     * name : 上海火车站
     * lat : 31.183465
     * lng : 121.407916
     */

    private int id;
    private String name;
    private String lat;
    private String lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
