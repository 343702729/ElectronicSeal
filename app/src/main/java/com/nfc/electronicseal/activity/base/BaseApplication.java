package com.nfc.electronicseal.activity.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class BaseApplication extends Application {

	public static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SDKInitializer.initialize(this);
//		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//		JPushInterface.init(this);
//		AppInfo.init(this);
	}

	public static BaseApplication getInstance() {

		return instance;
	}

}
