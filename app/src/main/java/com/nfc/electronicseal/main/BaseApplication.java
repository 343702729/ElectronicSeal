package com.nfc.electronicseal.main;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.baidu.mapapi.SDKInitializer;
import com.nfc.electronicseal.util.AppInfo;

public class BaseApplication extends Application {
	public int localVersion;
	public String versionName;
	public static BaseApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		SDKInitializer.initialize(this);
//		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//		JPushInterface.init(this);
		AppInfo.init(this);
		try {
			PackageInfo mPKinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			localVersion = mPKinfo.versionCode;
			versionName = mPKinfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static BaseApplication getInstance() {

		return instance;
	}

}
