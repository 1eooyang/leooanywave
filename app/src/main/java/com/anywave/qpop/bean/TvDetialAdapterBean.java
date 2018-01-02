package com.anywave.qpop.bean;

import android.widget.CheckBox;

/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class TvDetialAdapterBean {

    CheckBox checkBox;
    int status;
    String url;

//    public TvDetialAdapterBean(CheckBox checkBox) {
//        this.checkBox = checkBox;
//    }


    public TvDetialAdapterBean(CheckBox checkBox, String url) {
        this.checkBox = checkBox;
        this.url = url;
    }

//    public TvDetialAdapterBean(CheckBox checkBox, int status) {
//        this.checkBox = checkBox;
//        this.status = status;
//    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
