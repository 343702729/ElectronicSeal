package com.nfc.electronicseal;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.VersionBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.fragment.OperateFragment;
import com.nfc.electronicseal.fragment.SearchFragment;
import com.nfc.electronicseal.fragment.UserFragment;
import com.nfc.electronicseal.node.VersionNode;
import com.nfc.electronicseal.response.VersionResponse;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.version.VersionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    List<Fragment> fragments;
    private Fragment preFragment;

    private boolean isExit = false;

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
        verifyStoragePermissions(this);
        checkVersionData();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        NFCUtil.enableForeground(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        NFCUtil.disableForeground(this);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(1, 2000);
        } else {
//            UserInfo.getInstance().setLogin(false);
            finish();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isExit = false;
                    break;
            }
        }
    };

    private void checkVersionData(){
        VersionBean bean = new VersionBean(0);
        APIRetrofitUtil.getInstance().getVersionData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<VersionResponse>("").io_no_main(this))
                .subscribe(new RxSubscriber<VersionResponse>() {
                    @Override
                    public void _onNext(VersionResponse response) {
                        if(response!=null&&response.isSuccess()){
                            checkVersion(response.getData());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    /**
     * 版本更新检测
     * @param node
     */
    private void checkVersion(VersionNode node){
        if(node==null)
            return;
        VersionUtil versionUtil = new VersionUtil();
        boolean flage = versionUtil.checkVersion(this, node);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    123);
        }
    }

}
