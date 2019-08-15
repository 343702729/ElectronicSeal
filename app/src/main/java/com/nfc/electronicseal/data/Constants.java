package com.nfc.electronicseal.data;

import android.os.Environment;

public class Constants {
    public static String EVENT_RXBUS_GOTO_LOGIN = "login activity";

    public static final int REQUEST_CODE = 1010;
    public static final int RESULT_CODE = 1100;

    private static final String Path = Environment.getExternalStorageDirectory().toString() + "/Seal/";
    public static final String PATH_PIC = Path + "system/Picture/";
}
