package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.activity.my.InstructionActivity;
import com.nfc.electronicseal.activity.my.SettingActivity;
import com.nfc.electronicseal.activity.my.UserInfoActivity;
import com.nfc.electronicseal.wiget.GlideCircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class UserFragment extends BaseFragment {
    @BindView(R.id.user_head_iv)
    ImageView headIV;

    @Override
    public int layoutView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initview(View view) {
        super.initview(view);
        Glide.with(this).load(R.mipmap.test_icon_user)
                //圆形
                .transform(new GlideCircleTransform(getContext()))
                .into(headIV);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.setting_ll, R.id.user_info_ll, R.id.user_instruction_ll})
    public void lineItemClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.setting_ll:
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.user_info_ll:
                intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_instruction_ll:
                intent = new Intent(getContext(), InstructionActivity.class);
                startActivity(intent);
                break;
        }
    }

}
