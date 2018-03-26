package com.sachet.bloodsearch.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class UserDataSharedPreference {
    private static SharedPreferences sharedPreferences;
    private final static String key = "username";

    public static void setNullValue(Context context) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void setUserName(Context context, String userName) {
        sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userNameKey",userName);
        editor.apply();
    }

    public String getUserName(Context context){
        sharedPreferences  = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        return sharedPreferences.getString("userNameKey","NODATA");
    }
}
