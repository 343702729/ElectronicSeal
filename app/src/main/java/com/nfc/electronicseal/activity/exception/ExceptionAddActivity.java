package com.nfc.electronicseal.activity.exception;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionAddActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.type1_btn)
    Button type1Btn;
    @BindView(R.id.type2_btn)
    Button type2Btn;
    @BindView(R.id.type3_btn)
    Button type3Btn;

    private int typeIndex = 0;

    @Override
    public int layoutView() {
        return R.layout.activity_exception_add;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("新增异常申报");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick({R.id.type1_btn, R.id.type2_btn, R.id.type3_btn})
    public void typeItemClick(View view){
        switch (view.getId()){
            case R.id.type1_btn:
                typeIndex = 1;
                setTypeSel(1);
                break;
            case R.id.type2_btn:
                typeIndex = 2;
                setTypeSel(2);
                break;
            case R.id.type3_btn:
                typeIndex = 3;
                setTypeSel(3);
                break;
        }
    }

    private void setTypeSel(int index){
        type1Btn.setBackgroundResource(R.mipmap.pic_type_bg);
        type2Btn.setBackgroundResource(R.mipmap.pic_type_bg);
        type3Btn.setBackgroundResource(R.mipmap.pic_type_bg);
        type1Btn.setTextColor(getResources().getColor(R.color.grayLight));
        type2Btn.setTextColor(getResources().getColor(R.color.grayLight));
        type3Btn.setTextColor(getResources().getColor(R.color.grayLight));
        switch (index){
            case 1:
                type1Btn.setBackgroundResource(R.mipmap.pic_type_fc);
                type1Btn.setTextColor(getResources().getColor(R.color.redDark));
                break;
            case 2:
                type2Btn.setBackgroundResource(R.mipmap.pic_type_fc);
                type2Btn.setTextColor(getResources().getColor(R.color.redDark));
                break;
            case 3:
                type3Btn.setBackgroundResource(R.mipmap.pic_type_fc);
                type3Btn.setTextColor(getResources().getColor(R.color.redDark));
                break;
        }
    }
}
