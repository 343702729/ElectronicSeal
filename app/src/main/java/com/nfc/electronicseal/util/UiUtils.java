package com.nfc.electronicseal.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nfc.electronicseal.activity.base.BaseApplication;

public class UiUtils {

	public static Context getContext(){
		return BaseApplication.getInstance();
	}

	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	public static void hideSoftKeyboard(Activity activity) {
		View view = activity.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
