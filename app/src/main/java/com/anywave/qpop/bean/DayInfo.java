package com.anywave.qpop.bean;


public class DayInfo extends BaseBean {

    String day;
    boolean isChecked;

    public DayInfo() {
    }

    public DayInfo(String day, boolean isChecked) {
        this.day = day;
        this.isChecked = isChecked;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
