package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.login.LoginActivity;

public class SplashActivity extends BaseActivity{
    @Override
    public int layoutView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        super.initData();
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
}
