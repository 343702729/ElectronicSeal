package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.view.View;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import butterknife.OnClick;

/**
 * 施封
 */
public class SealActivity extends BaseActivity {
    private BDLocationUtil bdLocationUtil;

    @Override
    public int layoutView() {
        return R.layout.activity_seal;
    }

    @Override
    public void initData() {
        super.initData();
        new NFCUtil(this);
        bdLocationUtil = new BDLocationUtil(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启前台调度系统
//        NFCUtil.mNfcAdapter.enableForegroundDispatch(this, NFCUtil.mPendingIntent,
//                NFCUtil.mIntentFilter, NFCUtil.mTechList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭前台调度系统
//        NFCUtil.mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String str = NFCUtil.readNFCFromTag(intent);
            TLog.log("The NFC content is:" + str);
//            String str = NFCUtil.rendFromTag(intent);
        }catch (Exception e){

        }

    }

     @OnClick({R.id.open_btn, R.id.close_btn, R.id.pic_sel_btn, R.id.location_start_btn, R.id.location_end_btn})
     public void itemBtnClick(View view){
         switch (view.getId()){
            case R.id.open_btn:
                 //开启前台调度系统
                 NFCUtil.mNfcAdapter.enableForegroundDispatch(this, NFCUtil.mPendingIntent,
                 NFCUtil.mIntentFilter, NFCUtil.mTechList);
                 TLog.log("Come into open NFC");
                 break;
             case R.id.close_btn:
                 //关闭前台调度系统
                 NFCUtil.mNfcAdapter.disableForegroundDispatch(this);
                 TLog.log("Come into close NFC");
                break;
             case R.id.pic_sel_btn:

                 break;
             case R.id.location_start_btn:
                 bdLocationUtil.startLocation();
                 break;
             case R.id.location_end_btn:
                 bdLocationUtil.stopLocation();
                 break;
         }
     }

}
