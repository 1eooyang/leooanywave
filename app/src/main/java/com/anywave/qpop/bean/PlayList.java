package com.anywave.qpop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class PlayList {


    /**
     * status : 200
     * data : {"videoUrl":"","list":[{"id":81,"imgUrl":"/nodejspackage/programme20171011154906/upload_15d910999ec5347c2c5458aa6e329f58.jpg","title":"CCTV-13","current":"新闻直播间","next":"共同关注  18:00 - 19:00","currentStart":"17:00","currentEnd":"18:00","playUrl":"http://192.168.0.1:9090/hls/ch1.m3u8"},{"id":82,"imgUrl":"/nodejspackage/programme20171011154906/upload_2ae72694c124d662fa572393d986f783.jpg","title":"CCTV-1","current":"中国诗词大会Ⅱ:第九场","next":"新闻联播  19:00 - 19:30","currentStart":"17:18","currentEnd":"19:00","playUrl":"http://192.168.0.1:9090/hls/ch2.m3u8"},{"id":83,"imgUrl":"/nodejspackage/programme20171011154906/upload_a0900d25f10aba83c944b18c98413ef9.jpg","title":"CCTV-4","current":"向往的生活","next":"远方的家:\"一带一路\"特别节目  17:15 - 18:00","currentStart":"16:30","currentEnd":"17:35","playUrl":"http://192.168.0.1:9090/hls/ch3.m3u8"},{"id":84,"imgUrl":"/nodejspackage/programme20171011154906/upload_00a9536cd2b7b57fda0f952d1533627e.jpg","title":"央视频道","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch4.m3u8"},{"id":85,"imgUrl":"/nodejspackage/programme20171011154906/upload_3b74a946c489d52799d3d9f86ff70a0d.jpg","title":"广州4","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch5.m3u8"},{"id":86,"imgUrl":"/nodejspackage/programme20171011154906/upload_55f40d8a8fb3745d328c0b3bd91c4472.jpg","title":"广州3","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch6.m3u8"},{"id":87,"imgUrl":"/nodejspackage/programme20171011154906/upload_a7ef7e42acda5b89db5f3e888f8a6e6f.jpg","title":"广州1","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch7.m3u8"},{"id":88,"imgUrl":"/nodejspackage/programme20171011154906/upload_2f7edb016abb49c87fc2800e58753d6e.jpg","title":"广州2","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch8.m3u8"}]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * videoUrl :
         * list : [{"id":81,"imgUrl":"/nodejspackage/programme20171011154906/upload_15d910999ec5347c2c5458aa6e329f58.jpg","title":"CCTV-13","current":"新闻直播间","next":"共同关注  18:00 - 19:00","currentStart":"17:00","currentEnd":"18:00","playUrl":"http://192.168.0.1:9090/hls/ch1.m3u8"},{"id":82,"imgUrl":"/nodejspackage/programme20171011154906/upload_2ae72694c124d662fa572393d986f783.jpg","title":"CCTV-1","current":"中国诗词大会Ⅱ:第九场","next":"新闻联播  19:00 - 19:30","currentStart":"17:18","currentEnd":"19:00","playUrl":"http://192.168.0.1:9090/hls/ch2.m3u8"},{"id":83,"imgUrl":"/nodejspackage/programme20171011154906/upload_a0900d25f10aba83c944b18c98413ef9.jpg","title":"CCTV-4","current":"向往的生活","next":"远方的家:\"一带一路\"特别节目  17:15 - 18:00","currentStart":"16:30","currentEnd":"17:35","playUrl":"http://192.168.0.1:9090/hls/ch3.m3u8"},{"id":84,"imgUrl":"/nodejspackage/programme20171011154906/upload_00a9536cd2b7b57fda0f952d1533627e.jpg","title":"央视频道","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch4.m3u8"},{"id":85,"imgUrl":"/nodejspackage/programme20171011154906/upload_3b74a946c489d52799d3d9f86ff70a0d.jpg","title":"广州4","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch5.m3u8"},{"id":86,"imgUrl":"/nodejspackage/programme20171011154906/upload_55f40d8a8fb3745d328c0b3bd91c4472.jpg","title":"广州3","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch6.m3u8"},{"id":87,"imgUrl":"/nodejspackage/programme20171011154906/upload_a7ef7e42acda5b89db5f3e888f8a6e6f.jpg","title":"广州1","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch7.m3u8"},{"id":88,"imgUrl":"/nodejspackage/programme20171011154906/upload_2f7edb016abb49c87fc2800e58753d6e.jpg","title":"广州2","current":"","next":"","currentStart":"","currentEnd":"","playUrl":"http://192.168.0.1:9090/hls/ch8.m3u8"}]
         */

        private String videoUrl;
        private List<ListBean> list;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 81
             * imgUrl : /nodejspackage/programme20171011154906/upload_15d910999ec5347c2c5458aa6e329f58.jpg
             * title : CCTV-13
             * current : 新闻直播间
             * next : 共同关注  18:00 - 19:00
             * currentStart : 17:00
             * currentEnd : 18:00
             * playUrl : http://192.168.0.1:9090/hls/ch1.m3u8
             */

            private int id;
            private String imgUrl;
            private String title;
            private String current;
            private String next;
            private String currentStart;
            private String currentEnd;
            private String playUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCurrent() {
                return current;
            }

            public void setCurrent(String current) {
                this.current = current;
            }

            public String getNext() {
                return next;
            }

            public void setNext(String next) {
                this.next = next;
            }

            public String getCurrentStart() {
                return currentStart;
            }

            public void setCurrentStart(String currentStart) {
                this.currentStart = currentStart;
            }

            public String getCurrentEnd() {
                return currentEnd;
            }

            public void setCurrentEnd(String currentEnd) {
                this.currentEnd = currentEnd;
            }

            public String getPlayUrl() {
                return playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }
        }
    }
}
