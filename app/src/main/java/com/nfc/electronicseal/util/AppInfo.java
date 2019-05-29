package com.nfc.electronicseal.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

public class AppInfo {

    public static int appCode;
    public static String appVersion;

    public static void init(Context mContext) {
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != pi) {
            appVersion = pi.versionName;
            appCode = pi.versionCode;
        } else {
            appVersion = "";
            appCode = 0;
        }
    }
}
