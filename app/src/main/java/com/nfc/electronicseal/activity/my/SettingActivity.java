package com.nfc.electronicseal.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

    @Override
    public int layoutView() {
        return R.layout.activity_setting;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("设置");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.password_set_ll)
    public void passwordSetLLClick(View view){
        Intent intent = new Intent(this, PasswordUpdateActivity.class);
        startActivity(intent);
    }
}
