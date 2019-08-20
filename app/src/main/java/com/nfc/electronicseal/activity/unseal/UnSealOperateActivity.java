package com.nfc.electronicseal.activity.unseal;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.inspect.InspectOperateActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.PicUploadUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.bean.UnSealBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.dialog.DialogHelper;
import com.nfc.electronicseal.response.Response;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UnSealOperateActivity extends BaseActivity{
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.chip_id_tv)
    TextView chipIdTV;
    @BindView(R.id.seal_id_tv)
    TextView sealIdTV;
    @BindView(R.id.tax_num_tv)
    TextView taxNumTV;
    @BindView(R.id.item_status_tv)
    TextView itemStatusTV;
    @BindView(R.id.location_tv)
    EditText locationTV;
    @BindView(R.id.pic1_show_iv)
    ImageView picShow1IV;
    @BindView(R.id.pic2_show_iv)
    ImageView picShow2IV;
    @BindView(R.id.pic3_show_iv)
    ImageView picShow3IV;
    @BindView(R.id.pic1_delete_iv)
    ImageView picDelete1IV;
    @BindView(R.id.pic2_delete_iv)
    ImageView picDelete2IV;
    @BindView(R.id.pic3_delete_iv)
    ImageView picDelete3IV;
    @BindView(R.id.unseal_desc_et)
    EditText unsealDescET;
    @BindView(R.id.box_no_ll)
    LinearLayout boxNoLL;
    @BindView(R.id.box_no_tv)
    TextView boxNoTV;

    private String chipId;
    private String content;
    private String sealId;
    private String taxNum;
    private String containerNo;
    private String sealStatus;

    private BDLocationUtil bdLocationUtil;
    private String unsealLoca;
    private double latitude = 0, longitude = 0;

    private String pic1Url, pic2Url, pic3Url;

    private boolean isWrite = false;
    private String writeContent;
    private boolean reRead = false;

    @Override
    public int layoutView() {
        return R.layout.activity_unseal_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("拆封管理");
        chipId = getIntent().getStringExtra("NFCID");
        content = getIntent().getStringExtra("NFCCONTENT");

        chipIdTV.setText(chipId);
        boxNoLL.setVisibility(View.VISIBLE);
        bdLocationUtil = new BDLocationUtil(this, new LocationInfoCall());
    }

    @Override
    public void initData() {
        super.initData();
        parseNFCContent();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.re_read_tv)
    public void reReadTVClick(View view){
        reRead = true;
        DialogHelper.showProgressDlg(UnSealOperateActivity.this, "请靠近封条读取...");
    }

    @OnClick(R.id.location_ib)
    public void locationBtnClick(View view){
        bdLocationUtil.startLocation();
    }

    @OnClick({R.id.pic1_show_iv, R.id.pic2_show_iv, R.id.pic3_show_iv, R.id.pic1_delete_iv, R.id.pic2_delete_iv, R.id.pic3_delete_iv})
    public void picAddOrDeleteClick(View view){
        switch (view.getId()){
            case R.id.pic1_show_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(1, picShow1IV));
                IPicker.open(this, null);
                break;
            case R.id.pic2_show_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(2, picShow2IV));
                IPicker.open(this, null);
                break;
            case R.id.pic3_show_iv:
                IPicker.setOnSelectedListener(new PicItemSelectListener(3, picShow3IV));
                IPicker.open(this, null);
                break;
            case R.id.pic1_delete_iv:
                Glide.with(UnSealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow1IV);
                picDelete1IV.setVisibility(View.GONE);
                break;
            case R.id.pic2_delete_iv:
                Glide.with(UnSealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow2IV);
                picDelete2IV.setVisibility(View.GONE);
                break;
            case R.id.pic3_delete_iv:
                Glide.with(UnSealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow3IV);
                picDelete3IV.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.submit_btn)
    public void submitBtnClick(View view){
        unsealSubmitStart();
    }

    private void parseNFCContent(){
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
                sealIdTV.setText(sealId);
            }else if(strs[0].contains("TAXNUMBER")) {
                taxNum = strs[1];
                taxNumTV.setText(taxNum);
            }else if(strs[0].contains("CONTAINERNO")){
                containerNo = strs[1];
                boxNoTV.setText(containerNo);
            }else if(strs[0].contains("SEALSTATUS")){
                sealStatus = strs[1];
            }
        }

        if("1".equals(sealStatus)){
            //已施封
            itemStatusTV.setText("已施封");
        }else if("2".equals(sealStatus)){
            //已巡检
            itemStatusTV.setText("已巡检");
        }else {
            //已完成
            itemStatusTV.setText("已完成");
        }
    }

    private class LocationInfoCall implements BaseInfoUpdate {
        @Override
        public void update(Object object) {
            if(object==null)
                return;
            BDLocation location = (BDLocation)object;
            //纬度
            latitude = location.getLatitude();
            //经度
            longitude = location.getLongitude();
            unsealLoca = location.getAddrStr();
            TLog.log("The seal loca:" + unsealLoca);
            DecimalFormat df = new DecimalFormat("#.000000");
            locationTV.setText(df.format(latitude) + "," + df.format(longitude));
            bdLocationUtil.stopLocation();
        }
    }

    private class PicItemSelectListener implements IPicker.OnSelectedListener {
        private int index;
        private ImageView imageView;

        public PicItemSelectListener(int index, ImageView imageView){
            this.index = index;
            this.imageView = imageView;
        }

        @Override
        public void onSelected(List<String> paths) {
            if(paths==null&&paths.size()<=0)
                return;
            final String  selecPic = paths.get(0);
            TLog.log("The pic path is:" + selecPic);

            PicUploadUtil picUploadUtil = new PicUploadUtil();
            picUploadUtil.uploadUnsealDo(UserInfo.getInstance().getToken(), UnSealOperateActivity.this, selecPic, new BaseInfoUpdate() {
                @Override
                public void update(Object object) {
                    if(object==null)
                        return;

                    Glide.with(UnSealOperateActivity.this).load(selecPic).into(imageView);
                    switch (index){
                        case 1:
                            pic1Url = (String)object;
                            picDelete1IV.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            pic2Url = (String)object;
                            picDelete2IV.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            pic3Url = (String)object;
                            picDelete3IV.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });

        }
    }

    public void unsealSubmitStart(){
        //地理位置
        if(latitude==0|| longitude == 0||TextUtils.isEmpty(unsealLoca)){
            AppToast.showShortText(this, "地理位置不能为空");
            return;
        }

        if(TextUtils.isEmpty(pic1Url)&&TextUtils.isEmpty(pic2Url)&&TextUtils.isEmpty(pic3Url)){
            AppToast.showShortText(this, "请上传巡检照片");
            return;
        }

        isWrite = true;
        writeContent = "SEALID:" + sealId + "," + "TAXNUMBER:" + taxNum + "," + "CONTAINERNO:" + containerNo + "," + "SEALSTATUS:" + 3;
        DialogHelper.showProgressDlg(UnSealOperateActivity.this, "请靠近封条写入...");

    }

    private void unsealSubmitDo(){
        String desc = unsealDescET.getText().toString();
        String lnglat = longitude + "," + latitude;
        String sealPic = pic1Url + "," + pic2Url + "," + pic3Url;
        UnSealBean bean = new UnSealBean(sealId, unsealLoca, lnglat, desc, sealPic);
        APIRetrofitUtil.getInstance().unsealSubmitDo(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<Response>("提交信息...").io_main(UnSealOperateActivity.this))
                .subscribe(new RxSubscriber<Response>() {
                    @Override
                    public void _onNext(Response response) {
                        if(response!=null&&response.isSuccess()){
                            AppToast.showShortText(UnSealOperateActivity.this, "信息提交成功");
                            finish();
                        }else
                            AppToast.showShortText(UnSealOperateActivity.this, response.getMessage());
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try{
            if(!NFCUtil.isNFCCard(intent))
                AppToast.showShortText(this, "该封条不可用");

            String nfcSId = NFCUtil.readNFCId(intent);

            String str = NFCUtil.readNFCFromTag(intent);
            TLog.log("Come into seal nfc:" + str);
            if(reRead){
                reRead = false;
                DialogHelper.stopProgressDlg();
                if(TextUtils.isEmpty(str))
                    return;
                String[] items = str.split(",");
                String sealId = null, taxNum = null, containerNo = null, sealStatus = null;
                for(String item:items) {
                    if (TextUtils.isEmpty(item))
                        continue;
                    String[] strs = item.split(":");
                    if (TextUtils.isEmpty(strs[0]))
                        continue;
                    if (strs[0].contains("SEALID")) {
                        sealId = strs[1];
                    } else if (strs[0].contains("TAXNUMBER")) {
                        taxNum = strs[1];
                    } else if (strs[0].contains("CONTAINERNO")) {
                        containerNo = strs[1];
                    } else if (strs[0].contains("SEALSTATUS")) {
                        sealStatus = strs[1];
                    }
                }

                if(TextUtils.isEmpty(str)||TextUtils.isEmpty(sealId)||TextUtils.isEmpty(taxNum)||TextUtils.isEmpty(containerNo)||TextUtils.isEmpty(sealStatus)||!"2".equals(sealStatus)){
                    AppToast.showShortText(UnSealOperateActivity.this, "该封条不符合当前操作");
                    return;
                }
                chipId = nfcSId;
                this.sealId = sealId;
                this.taxNum = taxNum;
                this.containerNo = containerNo;
                this.sealStatus = sealStatus;
                sealIdTV.setText(sealId);
                taxNumTV.setText(taxNum);
                boxNoTV.setText(containerNo);
                if("1".equals(sealStatus)){
                    //已施封
                    itemStatusTV.setText("已施封");
                }else if("2".equals(sealStatus)){
                    //已巡检
                    itemStatusTV.setText("已巡检");
                }else {
                    //已完成
                    itemStatusTV.setText("已完成");
                }
                AppToast.showShortText(this, "读取成功");
            }

            if(isWrite){
                if(!chipId.equals(nfcSId)){
                    DialogHelper.stopProgressDlg();
                    AppToast.showShortText(this, "封条不对");
                    return;
                }
                TLog.log("The NFC seal come into write:" + writeContent);
                NFCUtil.writeNFCToTag("", intent);
                DialogHelper.stopProgressDlg();
                isWrite = false;
                unsealSubmitDo();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
