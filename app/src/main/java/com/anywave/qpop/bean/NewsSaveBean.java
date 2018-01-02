package com.anywave.qpop.bean;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class NewsSaveBean {


    /**
     * type : NEWSLETTER
     * referenceId : null
     * name : 测试新闻标题1
     * thumbnail : null
     * url : http://m.google.com
     */

    private String type;
    private Object referenceId;
    private String name;
    private Object thumbnail;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Object referenceId) {
        this.referenceId = referenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
