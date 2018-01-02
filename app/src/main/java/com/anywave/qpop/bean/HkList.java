package com.anywave.qpop.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class HkList {


    /**
     * ErrorCode : 200
     * Message : 取回看列表成功
     * Data : {"channel":{"id":105,"name":"CCTV-13实时","playkey":"ch1","imageurl":"/nodejspackage/programme20171012151058/upload_3af72310cd589ae039eacc91fb4f8342.jpg","createtime":"2017-01-01T00:00:00.000Z","channelpath":"programme20171012151058","innerid":"59a693a46768762d060e27d1"},"data":[{"id":2058,"channelid":105,"actname":"午夜新闻","actpart":1,"actstart":0,"actend":60,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"00:00","endTimeStr":"01:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507737600000&end_time=1507741200000&channel=ch1","isPlaying":-1},{"id":2059,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":60,"actend":80,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:00","endTimeStr":"01:20","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507741200000&end_time=1507742400000&channel=ch1","isPlaying":-1},{"id":2060,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":80,"actend":96,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:20","endTimeStr":"01:36","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507742400000&end_time=1507743360000&channel=ch1","isPlaying":-1},{"id":2091,"channelid":105,"actname":"法治在线","actpart":1,"actstart":96,"actend":120,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:36","endTimeStr":"02:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507743360000&end_time=1507744800000&channel=ch1","isPlaying":-1},{"id":2062,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":120,"actend":153,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"02:00","endTimeStr":"02:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507744800000&end_time=1507746780000&channel=ch1","isPlaying":-1},{"id":2063,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":153,"actend":180,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"02:33","endTimeStr":"03:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507746780000&end_time=1507748400000&channel=ch1","isPlaying":-1},{"id":2064,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":180,"actend":224,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"03:00","endTimeStr":"03:44","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507748400000&end_time=1507751040000&channel=ch1","isPlaying":-1},{"id":2065,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":224,"actend":240,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"03:44","endTimeStr":"04:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507751040000&end_time=1507752000000&channel=ch1","isPlaying":-1},{"id":2066,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":240,"actend":273,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"04:00","endTimeStr":"04:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507752000000&end_time=1507753980000&channel=ch1","isPlaying":-1},{"id":2067,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":273,"actend":300,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"04:33","endTimeStr":"05:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507753980000&end_time=1507755600000&channel=ch1","isPlaying":-1},{"id":2068,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":300,"actend":317,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:00","endTimeStr":"05:17","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507755600000&end_time=1507756620000&channel=ch1","isPlaying":-1},{"id":2069,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":317,"actend":333,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:17","endTimeStr":"05:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507756620000&end_time=1507757580000&channel=ch1","isPlaying":-1},{"id":2070,"channelid":105,"actname":"法治在线","actpart":1,"actstart":333,"actend":360,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:33","endTimeStr":"06:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507757580000&end_time=1507759200000&channel=ch1","isPlaying":-1},{"id":2071,"channelid":105,"actname":"朝闻天下","actpart":1,"actstart":360,"actend":378,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"06:00","endTimeStr":"06:18","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507759200000&end_time=1507760280000&channel=ch1","isPlaying":-1},{"id":2072,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":540,"actend":600,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"09:00","endTimeStr":"10:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507770000000&end_time=1507773600000&channel=ch1","isPlaying":-1},{"id":2073,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":600,"actend":660,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"10:00","endTimeStr":"11:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507773600000&end_time=1507777200000&channel=ch1","isPlaying":-1},{"id":2074,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":660,"actend":720,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"11:00","endTimeStr":"12:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507777200000&end_time=1507780800000&channel=ch1","isPlaying":-1},{"id":2075,"channelid":105,"actname":"新闻30分","actpart":1,"actstart":720,"actend":753,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"12:00","endTimeStr":"12:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507780800000&end_time=1507782780000&channel=ch1","isPlaying":-1},{"id":2076,"channelid":105,"actname":"法治在线","actpart":1,"actstart":753,"actend":780,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"12:33","endTimeStr":"13:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507782780000&end_time=1507784400000&channel=ch1","isPlaying":-1},{"id":2077,"channelid":105,"actname":"不忘初心继续前进(7)","actpart":1,"actstart":780,"actend":829,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:00","endTimeStr":"13:49","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507784400000&end_time=1507787340000&channel=ch1","isPlaying":-1},{"id":2078,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":829,"actend":840,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:49","endTimeStr":"14:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507787340000&end_time=1507788000000&channel=ch1","isPlaying":-1},{"id":2079,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":830,"actend":840,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:50","endTimeStr":"14:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507787400000&end_time=1507788000000&channel=ch1","isPlaying":-1},{"id":2080,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":840,"actend":900,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"14:00","endTimeStr":"15:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507788000000&end_time=1507791600000&channel=ch1","isPlaying":-1},{"id":2081,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":900,"actend":960,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"15:00","endTimeStr":"16:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507791600000&end_time=1507795200000&channel=ch1","isPlaying":-1},{"id":2082,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":960,"actend":1020,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"16:00","endTimeStr":"17:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507795200000&end_time=1507798800000&channel=ch1","isPlaying":0},{"id":2083,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":1020,"actend":1080,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"17:00","endTimeStr":"18:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507798800000&end_time=1507802400000&channel=ch1","isPlaying":1},{"id":2084,"channelid":105,"actname":"共同关注","actpart":1,"actstart":1080,"actend":1140,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"18:00","endTimeStr":"19:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507802400000&end_time=1507806000000&channel=ch1","isPlaying":1},{"id":2085,"channelid":105,"actname":"新闻联播","actpart":1,"actstart":1140,"actend":1170,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:00","endTimeStr":"19:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507806000000&end_time=1507807800000&channel=ch1","isPlaying":1},{"id":2086,"channelid":105,"actname":"天气预报","actpart":1,"actstart":1170,"actend":1179,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:30","endTimeStr":"19:39","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507807800000&end_time=1507808340000&channel=ch1","isPlaying":1},{"id":2087,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":1179,"actend":1200,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:39","endTimeStr":"20:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507808340000&end_time=1507809600000&channel=ch1","isPlaying":1},{"id":2088,"channelid":105,"actname":"东方时空","actpart":1,"actstart":1200,"actend":1260,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"20:00","endTimeStr":"21:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507809600000&end_time=1507813200000&channel=ch1","isPlaying":1},{"id":2089,"channelid":105,"actname":"新闻联播","actpart":1,"actstart":1260,"actend":1290,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"21:00","endTimeStr":"21:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507813200000&end_time=1507815000000&channel=ch1","isPlaying":1},{"id":2090,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":1290,"actend":1320,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"21:30","endTimeStr":"22:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507815000000&end_time=1507816800000&channel=ch1","isPlaying":1},{"id":2092,"channelid":105,"actname":"国际时讯","actpart":1,"actstart":1320,"actend":1350,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"22:00","endTimeStr":"22:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507816800000&end_time=1507818600000&channel=ch1","isPlaying":1},{"id":2093,"channelid":105,"actname":"环球视线","actpart":1,"actstart":1350,"actend":1380,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"22:30","endTimeStr":"23:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507818600000&end_time=1507820400000&channel=ch1","isPlaying":1},{"id":2094,"channelid":105,"actname":"24小时","actpart":1,"actstart":1380,"actend":1440,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"23:00","endTimeStr":"00:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507820400000&end_time=1507824000000&channel=ch1","isPlaying":1}]}
     */

    private int ErrorCode;
    private String Message;
    private DataBeanX Data;

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBeanX getData() {
        return Data;
    }

    public void setData(DataBeanX Data) {
        this.Data = Data;
    }

    public static class DataBeanX {
        /**
         * channel : {"id":105,"name":"CCTV-13实时","playkey":"ch1","imageurl":"/nodejspackage/programme20171012151058/upload_3af72310cd589ae039eacc91fb4f8342.jpg","createtime":"2017-01-01T00:00:00.000Z","channelpath":"programme20171012151058","innerid":"59a693a46768762d060e27d1"}
         * data : [{"id":2058,"channelid":105,"actname":"午夜新闻","actpart":1,"actstart":0,"actend":60,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"00:00","endTimeStr":"01:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507737600000&end_time=1507741200000&channel=ch1","isPlaying":-1},{"id":2059,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":60,"actend":80,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:00","endTimeStr":"01:20","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507741200000&end_time=1507742400000&channel=ch1","isPlaying":-1},{"id":2060,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":80,"actend":96,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:20","endTimeStr":"01:36","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507742400000&end_time=1507743360000&channel=ch1","isPlaying":-1},{"id":2091,"channelid":105,"actname":"法治在线","actpart":1,"actstart":96,"actend":120,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"01:36","endTimeStr":"02:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507743360000&end_time=1507744800000&channel=ch1","isPlaying":-1},{"id":2062,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":120,"actend":153,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"02:00","endTimeStr":"02:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507744800000&end_time=1507746780000&channel=ch1","isPlaying":-1},{"id":2063,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":153,"actend":180,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"02:33","endTimeStr":"03:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507746780000&end_time=1507748400000&channel=ch1","isPlaying":-1},{"id":2064,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":180,"actend":224,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"03:00","endTimeStr":"03:44","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507748400000&end_time=1507751040000&channel=ch1","isPlaying":-1},{"id":2065,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":224,"actend":240,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"03:44","endTimeStr":"04:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507751040000&end_time=1507752000000&channel=ch1","isPlaying":-1},{"id":2066,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":240,"actend":273,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"04:00","endTimeStr":"04:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507752000000&end_time=1507753980000&channel=ch1","isPlaying":-1},{"id":2067,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":273,"actend":300,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"04:33","endTimeStr":"05:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507753980000&end_time=1507755600000&channel=ch1","isPlaying":-1},{"id":2068,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":300,"actend":317,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:00","endTimeStr":"05:17","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507755600000&end_time=1507756620000&channel=ch1","isPlaying":-1},{"id":2069,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":317,"actend":333,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:17","endTimeStr":"05:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507756620000&end_time=1507757580000&channel=ch1","isPlaying":-1},{"id":2070,"channelid":105,"actname":"法治在线","actpart":1,"actstart":333,"actend":360,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"05:33","endTimeStr":"06:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507757580000&end_time=1507759200000&channel=ch1","isPlaying":-1},{"id":2071,"channelid":105,"actname":"朝闻天下","actpart":1,"actstart":360,"actend":378,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"06:00","endTimeStr":"06:18","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507759200000&end_time=1507760280000&channel=ch1","isPlaying":-1},{"id":2072,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":540,"actend":600,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"09:00","endTimeStr":"10:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507770000000&end_time=1507773600000&channel=ch1","isPlaying":-1},{"id":2073,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":600,"actend":660,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"10:00","endTimeStr":"11:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507773600000&end_time=1507777200000&channel=ch1","isPlaying":-1},{"id":2074,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":660,"actend":720,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"11:00","endTimeStr":"12:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507777200000&end_time=1507780800000&channel=ch1","isPlaying":-1},{"id":2075,"channelid":105,"actname":"新闻30分","actpart":1,"actstart":720,"actend":753,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"12:00","endTimeStr":"12:33","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507780800000&end_time=1507782780000&channel=ch1","isPlaying":-1},{"id":2076,"channelid":105,"actname":"法治在线","actpart":1,"actstart":753,"actend":780,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"12:33","endTimeStr":"13:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507782780000&end_time=1507784400000&channel=ch1","isPlaying":-1},{"id":2077,"channelid":105,"actname":"不忘初心继续前进(7)","actpart":1,"actstart":780,"actend":829,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:00","endTimeStr":"13:49","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507784400000&end_time=1507787340000&channel=ch1","isPlaying":-1},{"id":2078,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":829,"actend":840,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:49","endTimeStr":"14:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507787340000&end_time=1507788000000&channel=ch1","isPlaying":-1},{"id":2079,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":830,"actend":840,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"13:50","endTimeStr":"14:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507787400000&end_time=1507788000000&channel=ch1","isPlaying":-1},{"id":2080,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":840,"actend":900,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"14:00","endTimeStr":"15:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507788000000&end_time=1507791600000&channel=ch1","isPlaying":-1},{"id":2081,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":900,"actend":960,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"15:00","endTimeStr":"16:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507791600000&end_time=1507795200000&channel=ch1","isPlaying":-1},{"id":2082,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":960,"actend":1020,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"16:00","endTimeStr":"17:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507795200000&end_time=1507798800000&channel=ch1","isPlaying":0},{"id":2083,"channelid":105,"actname":"新闻直播间","actpart":1,"actstart":1020,"actend":1080,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"17:00","endTimeStr":"18:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507798800000&end_time=1507802400000&channel=ch1","isPlaying":1},{"id":2084,"channelid":105,"actname":"共同关注","actpart":1,"actstart":1080,"actend":1140,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"18:00","endTimeStr":"19:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507802400000&end_time=1507806000000&channel=ch1","isPlaying":1},{"id":2085,"channelid":105,"actname":"新闻联播","actpart":1,"actstart":1140,"actend":1170,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:00","endTimeStr":"19:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507806000000&end_time=1507807800000&channel=ch1","isPlaying":1},{"id":2086,"channelid":105,"actname":"天气预报","actpart":1,"actstart":1170,"actend":1179,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:30","endTimeStr":"19:39","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507807800000&end_time=1507808340000&channel=ch1","isPlaying":1},{"id":2087,"channelid":105,"actname":"焦点访谈","actpart":1,"actstart":1179,"actend":1200,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"19:39","endTimeStr":"20:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507808340000&end_time=1507809600000&channel=ch1","isPlaying":1},{"id":2088,"channelid":105,"actname":"东方时空","actpart":1,"actstart":1200,"actend":1260,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"20:00","endTimeStr":"21:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507809600000&end_time=1507813200000&channel=ch1","isPlaying":1},{"id":2089,"channelid":105,"actname":"新闻联播","actpart":1,"actstart":1260,"actend":1290,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"21:00","endTimeStr":"21:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507813200000&end_time=1507815000000&channel=ch1","isPlaying":1},{"id":2090,"channelid":105,"actname":"新闻1 1","actpart":1,"actstart":1290,"actend":1320,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"21:30","endTimeStr":"22:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507815000000&end_time=1507816800000&channel=ch1","isPlaying":1},{"id":2092,"channelid":105,"actname":"国际时讯","actpart":1,"actstart":1320,"actend":1350,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"22:00","endTimeStr":"22:30","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507816800000&end_time=1507818600000&channel=ch1","isPlaying":1},{"id":2093,"channelid":105,"actname":"环球视线","actpart":1,"actstart":1350,"actend":1380,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"22:30","endTimeStr":"23:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507818600000&end_time=1507820400000&channel=ch1","isPlaying":1},{"id":2094,"channelid":105,"actname":"24小时","actpart":1,"actstart":1380,"actend":1440,"actdescription":"","playdate":"2017-10-12","playkey":"ch1","startTimeStr":"23:00","endTimeStr":"00:00","checkUrl":"http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507820400000&end_time=1507824000000&channel=ch1","isPlaying":1}]
         */

        private ChannelBean channel;
        private List<DataBean> data;

        public ChannelBean getChannel() {
            return channel;
        }

        public void setChannel(ChannelBean channel) {
            this.channel = channel;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class ChannelBean {
            /**
             * id : 105
             * name : CCTV-13实时
             * playkey : ch1
             * imageurl : /nodejspackage/programme20171012151058/upload_3af72310cd589ae039eacc91fb4f8342.jpg
             * createtime : 2017-01-01T00:00:00.000Z
             * channelpath : programme20171012151058
             * innerid : 59a693a46768762d060e27d1
             */

            private int id;
            private String name;
            private String playkey;
            private String imageurl;
            private String createtime;
            private String channelpath;
            private String innerid;

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

            public String getPlaykey() {
                return playkey;
            }

            public void setPlaykey(String playkey) {
                this.playkey = playkey;
            }

            public String getImageurl() {
                return imageurl;
            }

            public void setImageurl(String imageurl) {
                this.imageurl = imageurl;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getChannelpath() {
                return channelpath;
            }

            public void setChannelpath(String channelpath) {
                this.channelpath = channelpath;
            }

            public String getInnerid() {
                return innerid;
            }

            public void setInnerid(String innerid) {
                this.innerid = innerid;
            }
        }

        public static class DataBean {
            /**
             * id : 2058
             * channelid : 105
             * actname : 午夜新闻
             * actpart : 1
             * actstart : 0
             * actend : 60
             * actdescription :
             * playdate : 2017-10-12
             * playkey : ch1
             * startTimeStr : 00:00
             * endTimeStr : 01:00
             * checkUrl : http://192.168.0.1:9090/vodplayback/index.m3u8?command=record&start_time=1507737600000&end_time=1507741200000&channel=ch1
             * isPlaying : -1
             */

            private int id;
            private int channelid;
            private String actname;
            private int actpart;
            private int actstart;
            private int actend;
            private String actdescription;
            private String playdate;
            private String playkey;
            private String startTimeStr;
            private String endTimeStr;
            private String checkUrl;
            private int isPlaying;
            private String videoUrl;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getChannelid() {
                return channelid;
            }

            public void setChannelid(int channelid) {
                this.channelid = channelid;
            }

            public String getActname() {
                return actname;
            }

            public void setActname(String actname) {
                this.actname = actname;
            }

            public int getActpart() {
                return actpart;
            }

            public void setActpart(int actpart) {
                this.actpart = actpart;
            }

            public int getActstart() {
                return actstart;
            }

            public void setActstart(int actstart) {
                this.actstart = actstart;
            }

            public int getActend() {
                return actend;
            }

            public void setActend(int actend) {
                this.actend = actend;
            }

            public String getActdescription() {
                return actdescription;
            }

            public void setActdescription(String actdescription) {
                this.actdescription = actdescription;
            }

            public String getPlaydate() {
                return playdate;
            }

            public void setPlaydate(String playdate) {
                this.playdate = playdate;
            }

            public String getPlaykey() {
                return playkey;
            }

            public void setPlaykey(String playkey) {
                this.playkey = playkey;
            }

            public String getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(String startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public String getEndTimeStr() {
                return endTimeStr;
            }

            public void setEndTimeStr(String endTimeStr) {
                this.endTimeStr = endTimeStr;
            }

            public String getCheckUrl() {
                return checkUrl;
            }

            public void setCheckUrl(String checkUrl) {
                this.checkUrl = checkUrl;
            }

            public int getIsPlaying() {
                return isPlaying;
            }

            public void setIsPlaying(int isPlaying) {
                this.isPlaying = isPlaying;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }
}
