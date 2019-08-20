package com.nfc.electronicseal.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {
    private static final  String NAME = "ElectronicSeal";

    public static void saveUserAccount(Context context, String account){
        SharedPreferences share = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Account", account);
        editor.commit();
    }

    public static String getUserAccount(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString("Account", "");
    }

    public static void saveUserPassword(Context context, String password){
        SharedPreferences share = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Password", password);
        editor.commit();
    }

    public static String getUserPassword(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString("Password", "");
    }
}
