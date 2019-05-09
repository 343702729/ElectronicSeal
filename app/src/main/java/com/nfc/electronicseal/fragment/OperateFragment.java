package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.ExceptionActivity;
import com.nfc.electronicseal.activity.inspect.InspectSearchActivity;
import com.nfc.electronicseal.activity.seal.SealSearchActivity;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.activity.unseal.UnSealSearchActivity;
import com.nfc.electronicseal.util.UiUtils;

import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class OperateFragment extends BaseFragment{

    @Override
    public int layoutView() {
        return R.layout.fragment_operate;
    }

    @OnClick({R.id.item_sf_ll, R.id.item_xj_ll, R.id.item_cf_ll, R.id.item_yc_ll})
    public void itemLLClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.item_sf_ll:       //施封
                intent = new Intent(UiUtils.getContext(), SealSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_xj_ll:       //巡检
                intent = new Intent(UiUtils.getContext(), InspectSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_cf_ll:       //拆封
                intent = new Intent(UiUtils.getContext(), UnSealSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_yc_ll:       //异常
                intent = new Intent(UiUtils.getContext(), ExceptionActivity.class);
                startActivity(intent);
                break;
        }
    }

}
