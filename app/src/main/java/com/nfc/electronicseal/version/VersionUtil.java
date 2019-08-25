package com.nfc.electronicseal.version;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.nfc.electronicseal.activity.base.BaseApplication;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.data.Constants;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.dialog.DialogHelper;
import com.nfc.electronicseal.node.VersionNode;

public class VersionUtil {
	private VersionUpdateDialog versionUpdateDialog;
	private VersionNode versionnode;
	private int type = 0;
	private Activity activity;
	
	/***
	 * 检查是否更新版本
	 */
	public boolean checkVersion(Activity activity, VersionNode versionnode){
		this.versionnode = versionnode;
		this.activity = activity;
		if(!((BaseApplication)activity.getApplication()).versionName.equals(versionnode.getVersion())){
//		if(!((BaseApplication)activity.getApplication()).versionName.equals("1.0.2")){
            Constants.DOWNLOAD_URL = versionnode.getDownloadPath();
            versionUpdateDialog = new VersionUpdateDialog(activity, versionnode);
            versionUpdateDialog.showView();
            UserInfo.getInstance().setAppDownloadUpdate(infoUpdate);
			return true;
		}
		return false;
	}

	public void updateVersion(Activity activity, VersionNode versionnode){
		this.versionnode = versionnode;
		this.activity = activity;
		type = 1;
		Constants.DOWNLOAD_URL = versionnode.getDownloadPath();
		UserInfo.getInstance().setAppDownloadUpdate(infoUpdate);
		DialogHelper.showProgressDlg(activity, "版本更新中，已下载0%");
		Intent updateIntent = new Intent(activity, ApplicationUpdateService.class);
		activity.startService(updateIntent);

	}

	private BaseInfoUpdate infoUpdate = new BaseInfoUpdate() {
		
		@Override
		public void update(Object object) {
			// TODO Auto-generated method stub
			if(object==null)
				return;
			Integer content = (Integer)object;
			String msg = "版本更新中，已下载" + content + "%";
			Log.i("Menus", msg);
			if(type==1) {
				handler.sendMessage(handler.obtainMessage(101, content));
			}else {
				handler.sendMessage(handler.obtainMessage(102, content));
			}

		}
	};

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==101){
				Integer mesg = (Integer)msg.obj;
				DialogHelper.updateDlgMsg("版本更新中，已下载" + mesg + "%");
				if(mesg==100)
					DialogHelper.stopProgressDlg();
			}else if(msg.what==102){
				Integer mesg = (Integer)msg.obj;
				versionUpdateDialog.setContent(mesg);

			}
			
		}
		
	};
}
