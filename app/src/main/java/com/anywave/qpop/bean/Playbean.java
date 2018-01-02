package com.anywave.qpop.bean;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class Playbean {

    String url;
    int status;
    long position;

    public Playbean(String url, int status,long position) {
        this.url = url;
        this.status = status;
        this.position = position;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getPosition() {
        return position;
    }
}
