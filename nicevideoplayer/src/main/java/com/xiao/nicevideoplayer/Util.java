package com.xiao.nicevideoplayer;

import android.content.Context;
import android.media.AudioManager;


import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
}
