package com.nfc.electronicseal;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.nfc.electronicseal.activity.SealActivity;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.fragment.OperateFragment;
import com.nfc.electronicseal.fragment.SearchFragment;
import com.nfc.electronicseal.fragment.UserFragment;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    List<Fragment> fragments;
    private Fragment preFragment;

    @Override
    public int layoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initview() {
        super.initview();
        fragments = new ArrayList<Fragment>();
        fragments.add(new OperateFragment());
        fragments.add(new SearchFragment());
        fragments.add(new UserFragment());
        changefragment(0);
    }

    @Override
    public void initListener() {
        super.initListener();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.operate_tab:
                        changefragment(0);
                        break;
                    case R.id.search_tab:
                        changefragment(1);
                        break;
                    case R.id.user_tab:
                        changefragment(2);
                        break;
                }
            }
        });
    }

    protected void changefragment(int index) {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment = fragments.get(index);
        if (preFragment != null) {
            transaction.hide(preFragment);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.framelayout, fragment);
        }
        transaction.commit();
        preFragment = fragment;
    }

}
