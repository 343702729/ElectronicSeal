package com.nfc.electronicseal.activity.exception;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.ExceptionActivity;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.util.TLog;

import java.util.List;

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
    @BindView(R.id.pic1_show_iv)
    ImageView picShow1IV;
    @BindView(R.id.pic2_show_iv)
    ImageView picShow2IV;
    @BindView(R.id.pic3_show_iv)
    ImageView picShow3IV;
    @BindView(R.id.pic1_delete_iv)
    ImageView picDelete1IV;
    @BindView(R.id.pic2_delete_iv)
    ImageView picDelete2IV;
    @BindView(R.id.pic3_delete_iv)
    ImageView picDelete3IV;

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

    @Override
    public void initListener() {
        super.initListener();

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

    @OnClick({R.id.pic1_add_iv, R.id.pic2_add_iv, R.id.pic3_add_iv, R.id.pic1_delete_iv, R.id.pic2_delete_iv, R.id.pic3_delete_iv})
    public void picAddOrDeleteClick(View view){
        switch (view.getId()){
            case R.id.pic1_add_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(1, picShow1IV));
                IPicker.open(this, null);
                break;
            case R.id.pic2_add_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(2, picShow2IV));
                IPicker.open(this, null);
                break;
            case R.id.pic3_add_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(3, picShow3IV));
                IPicker.open(this, null);
                break;
            case R.id.pic1_delete_iv:
                Glide.with(ExceptionAddActivity.this).load(R.mipmap.icon_transparent).into(picShow1IV);
                picDelete1IV.setVisibility(View.GONE);
                break;
            case R.id.pic2_delete_iv:
                Glide.with(ExceptionAddActivity.this).load(R.mipmap.icon_transparent).into(picShow2IV);
                picDelete2IV.setVisibility(View.GONE);
                break;
            case R.id.pic3_delete_iv:
                Glide.with(ExceptionAddActivity.this).load(R.mipmap.icon_transparent).into(picShow3IV);
                picDelete3IV.setVisibility(View.GONE);
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

    private class PicItemSelectListener implements IPicker.OnSelectedListener {
        private int index;
        private ImageView imageView;

        public PicItemSelectListener(int index, ImageView imageView){
            this.index = index;
            this.imageView = imageView;
        }

        @Override
        public void onSelected(List<String> paths) {
            if(paths==null&&paths.size()<=0)
                return;
            String  selecPic = paths.get(0);
            TLog.log("The pic path is:" + selecPic);
            Glide.with(ExceptionAddActivity.this).load(selecPic).into(imageView);
            switch (index){
                case 1:
                    picDelete1IV.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    picDelete2IV.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    picDelete3IV.setVisibility(View.VISIBLE);
                    break;
            }
//            showIV.setImageBitmap(PictureUtil.getimage(selecPic));
        }
    }
}
