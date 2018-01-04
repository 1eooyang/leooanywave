package com.anywave.quanbo.bean;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class WifiBodelBean {


    /**
     * id : 1
     * name : 上海火车站
     * lat : 31.183465
     * lng : 121.407916
     */


    private String gw_mac;
    private String umac;
    private String uip;
    private String code;
    private String mode;
    private String echo;


    public String getGw_mac() {
        return gw_mac;
    }

    public void setGw_mac(String gw_mac) {
        this.gw_mac = gw_mac;
    }

    public String getUmac() {
        return umac;
    }

    public void setUmac(String umac) {
        this.umac = umac;
    }

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }
}
