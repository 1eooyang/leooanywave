package com.xiao.nicevideoplayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/10/11 0011.
 */

public class Util {

    /**
     *
     * @param wifistr
     * @return
     */
    public static boolean isWifi(String wifistr) {
        if (wifistr==null) {
            return false;
        }
        String wifi1 ="Qpop_2_ZT";
        String wifi2 = "Qpop_1_ZT";
        if (wifistr.contains(wifi1) || wifistr.contains(wifi2)) {
            return true;
        }


        return false;
    }


    public static boolean isWifiConnect(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try{
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }catch (Exception e){
            return true;
        }


    }

}
