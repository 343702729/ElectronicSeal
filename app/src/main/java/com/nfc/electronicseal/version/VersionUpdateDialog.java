package com.nfc.electronicseal.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.node.VersionNode;

public class VersionUpdateDialog {
	private Activity context;
	private BaseInfoUpdate baseInfo;
	private AlertDialog mDialog;
	private View contentView;
	private TextView contentTV, downloadInfoTV;
	private VersionNode versionnode;
	private LinearLayout contentLL, progressLL;
	private ProgressBar progressBar;
    private boolean isExit = false;

	public VersionUpdateDialog(Activity context, VersionNode versionnode){
		this.context = context;
		this.versionnode = versionnode;
	}

	public void showView(){
		mDialog = new AlertDialog.Builder(context).create();
		mDialog.setCancelable(false);

		contentView = View.inflate(context, R.layout.dialog_app_update, null);
        contentLL = contentView.findViewById(R.id.content_ll);
        progressLL = contentView.findViewById(R.id.progress_ll);
        progressBar = contentView.findViewById(R.id.progress_bar);

//        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setOnKeyListener(dialogOnKeyListener);

        ImageButton closeIB = contentView.findViewById(R.id.close_ib);
        downloadInfoTV = contentView.findViewById(R.id.download_info_tv);
//        versionnode.setUpgradeType(1);
        if(versionnode.getUpgradeType()==1){
            closeIB.setVisibility(View.GONE);
            updateDo();
        }

		mDialog.show();

		Window mWindow = mDialog.getWindow();
		mWindow.setContentView(contentView);
		mWindow.setBackgroundDrawableResource(android.R.color.transparent);

		TextView versionName = contentView.findViewById(R.id.version_name_tv);
		contentTV = (TextView)contentView.findViewById(R.id.content_tv);
		if(versionnode!=null){
            versionName.setText("V" + versionnode.getVersion());
			contentTV.setText(versionnode.getUpgradeDetail());
		}

		Button cancelBtn = (Button)contentView.findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(closeDialogClick);

		closeIB.setOnClickListener(closeDialogClick);
		Button sureBtn = (Button)contentView.findViewById(R.id.sure_btn);
		sureBtn.setOnClickListener(sureBtnClick);
	}

	public void setContent(Integer progress){
		//		textV.setText(str);
        if(contentLL==null||progressLL==null)
            return;
        if(contentLL.getVisibility()== View.VISIBLE)
            contentLL.setVisibility(View.GONE);
        if(progressLL.getVisibility()!= View.VISIBLE)
            progressLL.setVisibility(View.VISIBLE);
        progressBar.setProgress(progress);
        if(progress==100)
            downloadInfoTV.setText("已下载完成,请安装更新");
        if(progress==100&&versionnode.getUpgradeType()!=1) {
            closeView();
        }
	}

	private OnClickListener closeDialogClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			closeView();
		}
	};

	private OnClickListener sureBtnClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.i("YaYin", "Come into sure btn click");
			updateDo();
		}
	};

	private void updateDo(){
		contentLL.setVisibility(View.GONE);
		progressLL.setVisibility(View.VISIBLE);
		Intent updateIntent = new Intent(context, ApplicationUpdateService.class);
		context.startService(updateIntent);
	}

	public boolean isShow(){
		if(mDialog==null)
			return false;
		return mDialog.isShowing();
	}

	public void closeView(){
		if(mDialog != null){
			mDialog.dismiss();
		}
	}

	private DialogInterface.OnKeyListener dialogOnKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_UP&&event.getRepeatCount()==0) {
                Log.i("Menus", "Come into dialog back:" + event.getRepeatCount());
                exit();
            }
            return false;

        }
    };

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(1, 2000);
        } else {
//            UserInfo.getInstance().setLogin(false);
            context.finish();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isExit = false;
                    break;
            }
        }
    };
}
