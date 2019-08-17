package com.nfc.electronicseal.activity.inspect;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.seal.SealOperateActivity;
import com.nfc.electronicseal.activity.unseal.UnSealSearchActivity;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import butterknife.BindView;
import butterknife.OnClick;

public class InspectSearchActivity extends BaseActivity{
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.instructions_tv)
    TextView instructionsTV;
    @BindView(R.id.animation_iv)
    ImageView animationIV;

    private String sealId;
    private String taxNum;
    private String containerNo;
    private String sealStatus;

    @Override
    public int layoutView() {
        return R.layout.activity_inspect_search;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("巡检管理");
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
//        NFCUtil.mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String nfcId = NFCUtil.readNFCId(intent);
            String str = NFCUtil.readNFCFromTag(intent);
            TLog.log("The NFC content is:" + str + "   nfcId:" + nfcId);
            parseNFCContent(str);
            if(TextUtils.isEmpty(str)||TextUtils.isEmpty(sealId)||TextUtils.isEmpty(taxNum)||TextUtils.isEmpty(containerNo)||TextUtils.isEmpty(sealStatus)){
                if("1".equals(sealStatus)||"1".equals(sealStatus)){

                }else {
                    AppToast.showShortText(InspectSearchActivity.this, "该芯片不符合当前操作");
                    return;
                }

            }
//            String str = NFCUtil.rendFromTag(intent);

            Intent intent1 = new Intent(this, InspectOperateActivity.class);
            intent1.putExtra("NFCID", nfcId);
            intent1.putExtra("NFCCONTENT", str);
            startActivity(intent1);

            //关闭前台调度系统
//            NFCUtil.mNfcAdapter.disableForegroundDispatch(this);
            TLog.log("Come into close NFC");
            finish();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick({R.id.open_nfc_btn})
    public void itemBtnClick(View view){
        switch (view.getId()){
            case R.id.open_nfc_btn:
//                Intent intent1 = new Intent(this, InspectOperateActivity.class);
//                intent1.putExtra("NFCID", nfcId);
//                startActivity(intent1);
                //开启前台调度系统
                if(NFCUtil.isSupportNFC){
                    NFCUtil.mNfcAdapter.enableForegroundDispatch(this, NFCUtil.mPendingIntent,
                            NFCUtil.mIntentFilter, NFCUtil.mTechList);
                }
                TLog.log("Come into open NFC");


                break;
        }
    }

    private void initInstructionsTV(){
        String content = "1、手机授权打开NFC设置； <br />2、靠近电子封条的NFC芯片附件，点击“<font color='#F3992F'>NFC读卡</font>”按钮，等待信息读取校验自动填写相关ID信息； <br />3、操作员输入需要提交的信息； <br />4、确认信息无误后，点击“<font color='#F3992F'>提交信息</font>”按钮，将数据提交云端，该步操作完成。";
        instructionsTV.setText(Html.fromHtml(content));

    }

    private void parseNFCContent(String content){
        if(TextUtils.isEmpty(content))
            return;
        String[] items = content.split(",");
        for(String item:items){
            if(TextUtils.isEmpty(item))
                continue;
            String[] strs = item.split(":");
            if(TextUtils.isEmpty(strs[0]))
                continue;
            if(strs[0].contains("SEALID")) {
                sealId = strs[1];
            }else if(strs[0].contains("TAXNUMBER")) {
                taxNum = strs[1];
            }else if(strs[0].contains("CONTAINERNO")){
                containerNo = strs[1];
            }else if(strs[0].contains("SEALSTATUS")){
                sealStatus = strs[1];
            }
        }

    }
}
