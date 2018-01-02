package com.anywave.qpop.utils;

import android.content.Context;
import android.widget.Toast;

import com.anywave.qpop.App;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class MyToast {

    public static void getToast(String s){
        Toast.makeText(App.context, s, Toast.LENGTH_SHORT).show();
    }

    public static void getToast(Context c,String s){
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }
}
