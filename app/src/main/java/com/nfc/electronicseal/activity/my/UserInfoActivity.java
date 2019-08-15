package com.nfc.electronicseal.activity.my;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.exception.ExceptionAddActivity;
import com.nfc.electronicseal.api.util.PicUploadUtil;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.wiget.GlideCircleTransform;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.head_iv)
    ImageView headIV;
    @BindView(R.id.name_tv)
    TextView nameTV;
    @BindView(R.id.tel_tv)
    TextView telTV;
    @BindView(R.id.account_tv)
    TextView accountTV;

    @Override
    public int layoutView() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("个人信息");

        Glide.with(this).load(UserInfo.getInstance().getUserNode().getEmployeeImage())
                //圆形
                .transform(new GlideCircleTransform(this))
                .into(headIV);
        nameTV.setText(UserInfo.getInstance().getUserNode().getName());
        accountTV.setText(UserInfo.getInstance().getUserNode().getAccount());
        telTV.setText(UserInfo.getInstance().getUserNode().getTelephone());
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.head_iv)
    public void headIVClick(View view){
        IPicker.setOnSelectedListener(new PicItemSelectListener());
        IPicker.setCropEnable(true);
        IPicker.open(this);
    }

    private class PicItemSelectListener implements IPicker.OnSelectedListener {
        @Override
        public void onSelected(List<String> paths) {
            if(paths==null&&paths.size()<=0)
                return;
            final String selecPic = paths.get(0);
            Glide.with(UserInfoActivity.this).load(selecPic)
                    //圆形
                    .transform(new GlideCircleTransform(UserInfoActivity.this))
                    .into(headIV);
            PicUploadUtil picUploadUtil = new PicUploadUtil();
            picUploadUtil.uploadUserHeadDo(UserInfo.getInstance().getToken(), UserInfoActivity.this, selecPic, new BaseInfoUpdate() {
                @Override
                public void update(Object object) {
                    Glide.with(UserInfoActivity.this).load(selecPic).into(headIV);
                }
            });
        }
    }
}
