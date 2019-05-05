package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.wiget.GlideCircleTransform;

import butterknife.BindView;

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

}
