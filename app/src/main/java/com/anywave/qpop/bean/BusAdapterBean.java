package com.anywave.qpop.bean;

import android.widget.CheckBox;

/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class BusAdapterBean {

    CheckBox checkBox;

    public BusAdapterBean(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
