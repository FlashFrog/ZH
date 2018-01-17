package com.example.frog.zhbj.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.CookiePolicy;

/**
 * Created by Frog on 2018/1/15.
 */

//通过sharedpreferences存储数据
public class CacheUtils {

    private static final String SP_NAME = "zhbj";

    private static SharedPreferences sp;

    private static SharedPreferences getPreferences(Context context){
        if(sp == null){
            sp = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        }
        return  sp;
    }


    public static boolean getBoolean(Context context, String key){
        SharedPreferences sp =  getPreferences(context);
        return sp.getBoolean(key,false);
    }

    public static boolean getBoolean(Context context, String key,boolean defValue){
        SharedPreferences sp =  getPreferences(context);
        return sp.getBoolean(key,defValue);
    }

    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp = getPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
}
