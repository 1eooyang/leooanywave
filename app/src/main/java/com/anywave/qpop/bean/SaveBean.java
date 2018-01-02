package com.anywave.qpop.bean;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class SaveBean {


    /**
     * url : http://m.google.com
     * favorited : false
     */

    private String url;
    private boolean favorited;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

}
