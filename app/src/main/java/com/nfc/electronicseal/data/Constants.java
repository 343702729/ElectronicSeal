package com.nfc.electronicseal.data;

import android.os.Environment;

public class Constants {
    public static String EVENT_RXBUS_GOTO_LOGIN = "login activity";

    public static final int REQUEST_CODE = 1010;
    public static final int RESULT_CODE = 1100;

    public static String DOWNLOAD_URL = "";
    public static String PATH_DOWNLOAD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    private static final String Path = Environment.getExternalStorageDirectory().toString() + "/Seal/";
    public static final String PATH_PIC = Path + "system/Picture/";
}
