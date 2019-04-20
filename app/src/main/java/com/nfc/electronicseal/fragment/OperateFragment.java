package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.SealActivity;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.util.UiUtils;

import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class OperateFragment extends BaseFragment{

    @Override
    public int layoutView() {
        return R.layout.fragment_operate;
    }

    @OnClick(R.id.nfc_read_btn)
    public void nfcReadBtnClick(View view){
        Intent intent = new Intent(UiUtils.getContext(), SealActivity.class);
        startActivity(intent);
    }

}
