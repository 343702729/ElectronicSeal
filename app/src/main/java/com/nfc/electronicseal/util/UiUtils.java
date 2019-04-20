package com.nfc.electronicseal.util;

import android.content.Context;
import android.view.View;

import com.nfc.electronicseal.activity.base.BaseApplication;

public class UiUtils {

	public static Context getContext(){
		return BaseApplication.getInstance();
	}

	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}
}
