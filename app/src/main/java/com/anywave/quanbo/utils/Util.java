package com.anywave.quanbo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.anywave.quanbo.App;
import com.anywave.quanbo.R;

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

    private int currVolume = 0;

    /**
     * 打开扬声器
     */
    private void openSpeaker() {
        try {
            AudioManager audioManager = (AudioManager) App.context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.ROUTE_SPEAKER);
            currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (!audioManager.isSpeakerphoneOn()) {
                //setSpeakerphoneOn() only work when audio mode set to MODE_IN_CALL.
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                        AudioManager.STREAM_VOICE_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭扬声器
     */
    public void closeSpeaker() {
        try {
            AudioManager audioManager = (AudioManager) App.context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static int getDayofweek() {

        //1-7
        Calendar cal = Calendar.getInstance();
//   cal.setTime(new Date(System.currentTimeMillis()));
        cal.setTime(new Date(System.currentTimeMillis()));

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayofweek(String date) {
        Calendar cal = Calendar.getInstance();
//   cal.setTime(new Date(System.currentTimeMillis()));
        if (date.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(getDateByStr2(date).getTime()));
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date getDateByStr2(String dd) {

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sd.parse(dd);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param wifistr
     * @return
     */
    public static boolean isWifi(String wifistr) {
        if (wifistr == null || wifistr.trim().length() == 0) {
            return false;
        }

        String s = wifistr.replaceAll("\"", "");

        return s.contains(wifi); //|| s.equals(wifi2);
    }

    private static final String wifi1 = App.context.getString(R.string.wifi1);
    private static final String wifi= "Qpop";
    private static final String wifi2 = App.context.getString(R.string.wifi2);


    public static boolean ScanWifi(String wifistr, String lastFailedSsid) {
        if (wifistr == null || wifistr.trim().length() == 0) {
            return false;
        }
        return wifistr.equals(wifi1) || wifistr.equals(wifi2);
    }


    public static boolean isWifi2(String wifistr) {
        if (wifistr == null || wifistr.trim().length() == 0) {
            return false;
        }


        String wifi1 = App.context.getString(R.string.wifi3);

        //    System.out.println("leo3 wifistr = " + wifistr);
        return wifistr.toLowerCase().contains(wifi1.toLowerCase());
    }
    public static boolean isWifiConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
          //  versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "2.0";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
            return "2.0";
        }
        return versionName;
    }


}
