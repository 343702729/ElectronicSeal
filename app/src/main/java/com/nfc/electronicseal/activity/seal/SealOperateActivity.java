package com.nfc.electronicseal.activity.seal;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SealOperateActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.box_id_ll)
    LinearLayout boxIdLL;
    @BindView(R.id.box_maker_ll)
    LinearLayout boxMakerLL;

    private String nfcId;

    @Override
    public int layoutView() {
        return R.layout.activity_seal_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("施封管理");
        boxIdLL.setVisibility(View.GONE);
        boxMakerLL.setVisibility(View.GONE);
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
