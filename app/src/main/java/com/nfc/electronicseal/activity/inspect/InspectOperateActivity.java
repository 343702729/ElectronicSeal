package com.nfc.electronicseal.activity.inspect;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class InspectOperateActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

    @Override
    public int layoutView() {
        return R.layout.activity_inspect_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("巡检管理");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

}
