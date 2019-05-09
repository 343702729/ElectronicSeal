package com.nfc.electronicseal.activity.unseal;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UnSealOperateActivity extends BaseActivity{
    @BindView(R.id.title_tv)
    TextView titleTV;

    @Override
    public int layoutView() {
        return R.layout.activity_unseal_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("拆封管理");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }
}
