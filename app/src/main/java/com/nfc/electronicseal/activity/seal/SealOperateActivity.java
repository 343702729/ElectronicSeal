package com.nfc.electronicseal.activity.seal;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SealOperateActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

    private String nfcId;

    @Override
    public int layoutView() {
        return R.layout.activity_seal_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("施封管理");
//        initDatas();
    }

    private void initDatas(){
        nfcId = getIntent().getStringExtra("NFCID");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }
}
