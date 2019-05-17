package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.view.View;

import com.nfc.electronicseal.MainActivity;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.util.TLog;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Override
    public int layoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        String mac = android.os.Build.SERIAL;
        TLog.log("The mac is:" + mac);
    }

    @OnClick(R.id.login_btn)
    public void loginBtnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
