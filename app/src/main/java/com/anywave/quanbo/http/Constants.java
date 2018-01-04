package com.anywave.quanbo.http;

/**
 * Created by Administrator on 2017/10/9 0009.
 *
 * http://114.55.250.37:8088/verifications/code
 */

public interface Constants {

    String wificonnection = "http://192.168.0.1/extranetAccessCheck";

    String IP="http://114.55.250.37:8088";
//String LIVE="http://220.248.15.134:8887";
    String LIVE="http://192.168.0.1";
    String LIVE_WIFI="http://192.168.0.6:2060";

    String SMS=IP+"/verifications";

    String CODE=SMS+"/code";
//    String CODE="http://114.55.250.37:8088/verifications/code"+"/code";
//    String MSM_SIGNIN="http://114.55.250.37:8088/members/signin/code";


    String MSM_SIGNIN =IP + "/members/signin/code";

    String PHONE_SIGNIN =IP + "/members/signin/phone";

    String MSM_SIGNOUT =IP + "/members/signout";

    String MEMBERS = IP +"/members";

    String TEST=SMS+"/test";
    String updatepassword =MEMBERS+"/updatepassword";


//    String signout=MEMBERS+"/signout";

    String playlist=LIVE+"/playlistV2";

    String hklist=LIVE+"/hk/hklist?id=65&d=2017-10-11&pwd=testpwd";

    String hk=LIVE+"/hk/hklist";

    String favorites=MEMBERS+"/favorites";

    String wifi = LIVE_WIFI +"/wifiabc/appauth";

    //String checkurl = LIVE +"/hk/hkplay";//id pwd

    String save = IP +"/members/favorite/save";


    String wifimodel = LIVE_WIFI +"/wifiabc/queryinternetmode";

    String check = IP +"/members/favorite/check";


    String gps = LIVE +"/gpsInfo";

    String stations = IP + "/routers/stations";//738
//    String stations = IP + "/routers/stations?busRouterId=1&busRouterName=";//738

//    http://114.55.250.37:8088/members/favorite/save
    //http://114.55.250.37:8088/members/favorite/check
    String news = LIVE+ "/userlog/news";

}
