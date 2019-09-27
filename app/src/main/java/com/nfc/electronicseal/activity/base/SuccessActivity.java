package com.nfc.electronicseal.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

    @Override
    public int layoutView() {
        return R.layout.activity_success;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("提示");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.sure_btn)
    public void sureBtnClick(View view){
        finish();
    }
}
