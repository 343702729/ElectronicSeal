package com.nfc.electronicseal.version;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import com.nfc.electronicseal.data.Constants;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.FileUtil;
import com.nfc.electronicseal.util.ThreadPoolDo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApplicationUpdateService extends Service {
	private static final String TAG = "AppUpdate";
	private static final int TIMEOUT = 10 * 1000;// 超时

	private Intent updateIntent;

	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;

	private PendingIntent pendingIntent;
	//	private NotificationManager notificationManager;
	private Notification notification;
	private int notification_id = 0;
	private static final String appName = "MerchantManage";
	private File appFile;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//ApplicationData
		Log.i(TAG, "Come into update app");
		appFile = FileUtil.createFile(appName, Constants.PATH_DOWNLOAD);

		if(appFile!=null){
			//			createNotification();
			Log.i(TAG, "Come into begin download");
			startDownload(appName, appFile);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void startDownload(String appName, File appFile){
		DownloadAppThread thread = new DownloadAppThread(appName + ".apk", appFile);
		ThreadPoolDo.getInstance().executeThread(thread);
	}

	/***
	 * 创建通知栏
	 */
	private RemoteViews contentView;

	/**
	public void createNotification() {
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		long number = 100;
		notification = new Notification(R.drawable.ic_launcher,"DnwoLoadManager",number);// 设定Notification出现时的声音，一般不建议自定义 System.currentTimeMillis()

		//在这里我们用自定的view来显示Notification
		contentView = new RemoteViews(getPackageName(), R.layout.item_notification);
		contentView.setTextViewText(R.id.notificationTitle, "正在下载" + appName + ".apk");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);

		notification.contentView = contentView;

		updateIntent = new Intent(this, MainActivity.class);
		updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);

		notification.contentIntent = pendingIntent;

		notificationManager.notify(notification_id, notification);

	}
	 */

	private class DownloadAppThread extends Thread {
		private String appName;
		private final Message message = new Message();
		private File appFile;

		public DownloadAppThread(String appName, File appFile){
			this.appName = appName;
			this.appFile = appFile;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String down_url = Constants.DOWNLOAD_URL;
			Log.i(TAG, "The down apk url:" + down_url);
			try {
				long downloadSize = downloadUpdateFile(down_url, appFile.toString());
				if (downloadSize > 0) {
					// 下载成功
					message.what = DOWN_OK;
					downloadHandler.sendMessage(message);
				}

			} catch (Exception e) {
				e.printStackTrace();
				message.what = DOWN_ERROR;
				downloadHandler.sendMessage(message);
			}


		}

	}

	/***
	 * 下载文件
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String file)
			throws Exception {
		int down_step = 1;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小
		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		// 获取下载文件的size
		totalSize = httpURLConnection.getContentLength();
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		}
		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
		byte buffer[] = new byte[1024];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// 时时获取下载到的大小
			/**
			 * 每次增张5%
			 */
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				System.out.println("The download percent:" + updateCount);
				if(UserInfo.getInstance().getAppDownloadUpdate()!=null)
					UserInfo.getInstance().getAppDownloadUpdate().update(updateCount);
				// 改变通知栏
				// notification.setLatestEventInfo(this, "正在下载...", updateCount
				// + "%" + "", pendingIntent);
				//				contentView.setTextViewText(R.id.notificationPercent, updateCount + "%");
				//				contentView.setProgressBar(R.id.notificationProgress, 100, updateCount, false);
				// show_view
				//				notificationManager.notify(notification_id, notification);

			}

		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();

		return downloadCount;

	}

	private Handler downloadHandler = new Handler(){

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case DOWN_OK:
				// 下载完成，点击安装
				//				notificationManager.cancel(notification_id);
				Uri uri = Uri.fromFile(appFile);
				openFile();
//				openfile(uri);

				//				Intent intent = new Intent(Intent.ACTION_VIEW);
				//				intent.setDataAndType(uri, "application/vnd.android.package-archive");
				//
				//				pendingIntent = PendingIntent.getActivity(ApplicationUpdateService.this, 0, intent, 0);
				//
				//				notification.setLatestEventInfo(ApplicationUpdateService.this, ApplicationData.serveAppName, "下载成功，点击安装", pendingIntent);
				//
				//				notificationManager.notify(notification_id, notification);
				//
				try {
					if(updateIntent!=null)
						stopService(updateIntent);
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			case DOWN_ERROR:
				break;
			default:
				stopService(updateIntent);
				break;
			}
		}

	};

	public void openfile(Uri uri){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(uri,"application/vnd.android.package-archive");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

		}else {

		}
		startActivity(intent);
	}

	public void openFile(){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(Build.VERSION.SDK_INT>=24) { //Android 7.0及以上
			// 参数2 清单文件中provider节点里面的authorities ; 参数3  共享的文件,即apk包的file类
			//com.yayin.merchantmanage.fileprovider
			String fp = getPackageName() + ".fileprovider";
			Log.i("YaYin", "The packagename is:" + fp);
			Uri apkUri = FileProvider.getUriForFile(this, fp, appFile);
			//对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
			AppToast.showShortText(this, "The is 7.0");
		}else{
			intent.setDataAndType(Uri.fromFile(appFile), "application/vnd.android.package-archive");
		}
		startActivity(intent);
	}
}
