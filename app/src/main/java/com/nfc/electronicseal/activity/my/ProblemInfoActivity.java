package com.nfc.electronicseal.activity.my;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ProblemInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

    @Override
    public int layoutView() {
        return R.layout.activity_problem_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("软件使用NFC读取设置权限需要...");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }
}
