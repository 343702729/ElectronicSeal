package com.nfc.electronicseal.activity.seal;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.unseal.UnSealSearchActivity;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 施封管理
 */
public class SealSearchActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.instructions_tv)
    TextView instructionsTV;
    @BindView(R.id.animation_iv)
    ImageView animationIV;

    @Override
    public int layoutView() {
        return R.layout.activity_seal_search;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("施封管理");
        initInstructionsTV();
        Glide.with(this).load(R.mipmap.animation).asGif().into(animationIV);
    }

    @Override
    public void initData() {
        super.initData();
        new NFCUtil(this);
        if(!NFCUtil.isSupportNFC)
            AppToast.showShortText(this, "该手机不支持NFC");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
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
        if(NFCUtil.isSupportNFC)
            NFCUtil.mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            if(!NFCUtil.isNFCCard(intent)) {
                AppToast.showShortText(this, "该封条不可用");
                return;
            }
            String nfcId = NFCUtil.readNFCId(intent);
            String str = NFCUtil.readNFCFromTag(intent);
            if(!TextUtils.isEmpty(str))
                str = str.trim();
            TLog.log("The NFC content is:" + str + "   nfcId:" + nfcId + "  size:" + str.getBytes().length);
            if(!TextUtils.isEmpty(str)&&!"zh".equals(str)){
                if(str.contains("SEALID")&&str.contains("TAXNUMBER")&&str.contains("CONTAINERNO")&&str.contains("SEALSTATUS")){
                    AppToast.showShortText(SealSearchActivity.this, "该封条不符合当前操作");
                    return;
                }

            }

//            String writeStr = "sealId:241520190519JD;taxNumber:91341003MA2TJA5342;containerNo:1234562789";
//            NFCUtil.writeNFCToTag(writeStr, intent);

            Intent intent1 = new Intent(this, SealOperateActivity.class);
            intent1.putExtra("NFCID", nfcId);
            startActivity(intent1);
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

     @OnClick({R.id.open_nfc_btn})
     public void itemBtnClick(View view){
         switch (view.getId()){
            case R.id.open_nfc_btn:
//                Intent intent1 = new Intent(this, SealOperateActivity.class);
//                startActivity(intent1);

                 //开启前台调度系统
                if(NFCUtil.isSupportNFC){
                    NFCUtil.mNfcAdapter.enableForegroundDispatch(this, NFCUtil.mPendingIntent,
                            NFCUtil.mIntentFilter, NFCUtil.mTechList);
                }

//                 TLog.log("Come into open NFC");


                 break;
         }
     }

     private void initInstructionsTV(){
         String content = "1、手机授权打开NFC设置； <br />2、靠近电子封条的NFC芯片附件，点击“<font color='#F3992F'>NFC读卡</font>”按钮，等待信息读取校验自动填写相关ID信息； <br />3、操作员输入需要提交的信息； <br />4、确认信息无误后，点击“<font color='#F3992F'>提交信息</font>”按钮，将数据提交云端，该步操作完成。";
         instructionsTV.setText(Html.fromHtml(content));

     }

}
