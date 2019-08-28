package com.nfc.electronicseal.activity.seal;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.PicUploadUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.bean.ChipCheckBean;
import com.nfc.electronicseal.bean.SealBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.data.bean.JsonBean;
import com.nfc.electronicseal.dialog.DialogHelper;
import com.nfc.electronicseal.response.ChipCheckResponse;
import com.nfc.electronicseal.response.Response;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.GetJsonDataUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SealOperateActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.box_id_ll)
    LinearLayout boxIdLL;
    @BindView(R.id.box_maker_ll)
    LinearLayout boxMakerLL;
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
    @BindView(R.id.location_tv)
    TextView locationTV;
    @BindView(R.id.receiver_name_et)
    EditText receiverNameET;
    @BindView(R.id.receiver_tel_et)
    EditText reveiverTelET;
    @BindView(R.id.receiver_addr_et)
    TextView receiverAddrET;
    @BindView(R.id.receiver_addrinfo_et)
    EditText receiverAddrinfoET;
    @BindView(R.id.seal_elc_id_et)
    EditText sealElcIdET;
    @BindView(R.id.seal_box_no_et)
    EditText sealBoxNoET;
    @BindView(R.id.seal_carrier_et)
    EditText sealCarrierET;
    @BindView(R.id.seal_desc_et)
    EditText sealDescET;
    @BindView(R.id.chip_id_tv)
    TextView chipIdTV;
    @BindView(R.id.item_status_tv)
    TextView itemStatusTV;

    private BDLocationUtil bdLocationUtil;

    private String nfcId;
    private String sealLoca;
    private double latitude = 0, longitude = 0;
    private String pic1Url, pic2Url, pic3Url;
    private boolean isWrite = false;
    private String writeContent;
    private boolean reRead = false;

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    public int layoutView() {
        return R.layout.activity_seal_operate;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("施封管理");
        new NFCUtil(this);
        initDatas();
        boxIdLL.setVisibility(View.GONE);
        boxMakerLL.setVisibility(View.GONE);
        bdLocationUtil = new BDLocationUtil(this, new LocationInfoCall());
        chipIdTV.setText(nfcId);
        itemStatusTV.setText("未使用");
        itemStatusTV.setTextColor(getResources().getColor(R.color.yellow_light));
    }

    private void initDatas(){
        nfcId = getIntent().getStringExtra("NFCID");
        initJsonData();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bdLocationUtil.stopLocation();
    }

    @OnClick(R.id.re_read_tv)
    public void reReadTVClick(View view){
        reRead = true;
        DialogHelper.showProgressDlg(SealOperateActivity.this, "请靠近封条读取...");
    }

    @OnClick(R.id.receiver_addr_et)
    public void addressSel(View view){
        TLog.log("Come into addr sel click");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                receiverAddrET.setText(tx);
//                AppToast.showShortText(SealOperateActivity.this, tx);
            }
        }).setTitleText("城市选择")
          .setDividerColor(Color.BLACK)
          .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
          .setContentTextSize(20)
          .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
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
                Glide.with(SealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow1IV);
                picDelete1IV.setVisibility(View.GONE);
                break;
            case R.id.pic2_delete_iv:
                Glide.with(SealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow2IV);
                picDelete2IV.setVisibility(View.GONE);
                break;
            case R.id.pic3_delete_iv:
                Glide.with(SealOperateActivity.this).load(R.mipmap.icon_transparent).into(picShow3IV);
                picDelete3IV.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.submit_btn)
    public void submitBtnClick(View view){
        sealSubmitStart();
    }

    private class LocationInfoCall implements BaseInfoUpdate{
        @Override
        public void update(Object object) {
            if(object==null)
                return;
            BDLocation location = (BDLocation)object;
            //纬度
            latitude = location.getLatitude();
            //经度
            longitude = location.getLongitude();
            sealLoca = location.getAddrStr();
            TLog.log("The seal loca:" + sealLoca);
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
            picUploadUtil.uploadSealDo(UserInfo.getInstance().getToken(), SealOperateActivity.this, selecPic, new BaseInfoUpdate() {
                @Override
                public void update(Object object) {
                    if(object==null)
                        return;

                    Glide.with(SealOperateActivity.this).load(selecPic).into(imageView);
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

    private void sealSubmitStart(){
//        String sealPic = pic1Url + "," + pic2Url + "," + pic3Url;

        //姓名
        String name = receiverNameET.getText().toString();
        if(TextUtils.isEmpty(name)){
            AppToast.showShortText(this, "收货人姓名不能为空");
            return;
        }

        //手机号
        String tel = reveiverTelET.getText().toString();
        if(TextUtils.isEmpty(tel)){
            AppToast.showShortText(this, "收货人手机号不能为空");
            return;
        }

        //收货地址
        String addr = receiverAddrET.getText().toString();
        if(TextUtils.isEmpty(addr)){
            AppToast.showShortText(this, "收货人收货地址不能为空");
            return;
        }

        //详细地址
        String addrinfo = receiverAddrinfoET.getText().toString();
        if(TextUtils.isEmpty(addrinfo)){
            AppToast.showShortText(this, "收货人详细地址不能为空");
            return;
        }

        //电子封箱号
        final String elcId = sealElcIdET.getText().toString();
        if(TextUtils.isEmpty(elcId)){
            AppToast.showShortText(this, "电子封箱号不能为空");
            return;
        }

        //集装箱号
        final String boxNo = sealBoxNoET.getText().toString();
        if(TextUtils.isEmpty(boxNo)){
            AppToast.showShortText(this, "集装箱号不能为空");
            return;
        }

        if(!isLetterDigitOrChinese(boxNo)){
            AppToast.showShortText(this, "集装箱号不符合规定");
            return;
        }

        //承运人
//        String carrier = sealCarrierET.getText().toString();
//        if(TextUtils.isEmpty(carrier)){
//            AppToast.showShortText(this, "承运人不能为空");
//            return;
//        }

        //地理位置
        if(latitude==0|| longitude == 0||TextUtils.isEmpty(sealLoca)){
            AppToast.showShortText(this, "地理位置不能为空");
            return;
        }

        //施封描述
        String desc = sealDescET.getText().toString();
        if(TextUtils.isEmpty(desc)){
            AppToast.showShortText(this, "施封描述不能为空");
            return;
        }

        //pic1Url, pic2Url, pic3Url
        if(TextUtils.isEmpty(pic1Url)&&TextUtils.isEmpty(pic2Url)&&TextUtils.isEmpty(pic3Url)){
            AppToast.showShortText(this, "请上传施封照片");
            return;
        }

        DialogHelper.showProgressDlg(SealOperateActivity.this, "加载数据..");
        ChipCheckBean bean = new ChipCheckBean(elcId);
        APIRetrofitUtil.getInstance().chipCheckDo(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ChipCheckResponse>("加载数据..").io_no_main(this))
                .subscribe(new RxSubscriber<ChipCheckResponse>() {
                    @Override
                    public void _onNext(ChipCheckResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            String taxNumber = response.getData().getTaxNumber();
                            isWrite = true;
                            writeContent = "SEALID:" + elcId + "," + "TAXNUMBER:" + taxNumber + "," + "CONTAINERNO:" + boxNo + "," + "SEALSTATUS:" + 1;

                        }else {
                            AppToast.showShortText(SealOperateActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        if(isWrite)
                            DialogHelper.showProgressDlg(SealOperateActivity.this, "请靠近封条写入...");
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
            if(!TextUtils.isEmpty(str))
                str = str.trim();
            TLog.log("Come into seal nfc:" + str);
            if(reRead){
                DialogHelper.stopProgressDlg();
                reRead = false;
                if(!TextUtils.isEmpty(str)&&!"zh".equals(str)) {
                    AppToast.showShortText(this, "该封条已使用");
                    return;
                }
                AppToast.showShortText(this, "读取成功");
                nfcId = nfcSId;
                chipIdTV.setText(nfcId);
                return;
            }else if(isWrite){
                if(!nfcId.equals(nfcSId)){
                    DialogHelper.stopProgressDlg();
                    AppToast.showShortText(this, "封条不对");
                    return;
                }
                TLog.log("The NFC seal come into write:" + writeContent);
                NFCUtil.writeNFCToTag(writeContent, intent);
                DialogHelper.stopProgressDlg();
                isWrite = false;
                sealSubmitDo();
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void sealSubmitDo(){
        String name = receiverNameET.getText().toString();
        String tel = reveiverTelET.getText().toString();
        String addr = receiverAddrET.getText().toString()+receiverAddrinfoET.getText().toString();
//        String addrinfo = receiverAddrinfoET.getText().toString();
        String elcId = sealElcIdET.getText().toString();
        String boxNo = sealBoxNoET.getText().toString();
        String carrier = sealCarrierET.getText().toString();
        String desc = sealDescET.getText().toString();

        String lnglat = longitude + "," + latitude;
        String sealPic = pic1Url + "," + pic2Url + "," + pic3Url;
        sealPic = sealPic.replaceAll(",null", "");

        SealBean bean = new SealBean(elcId, nfcId, name, tel, addr, boxNo, carrier, sealLoca, lnglat, desc, sealPic);
        APIRetrofitUtil.getInstance().sealSubmitDo(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<Response>("提交信息...").io_main(SealOperateActivity.this))
                .subscribe(new RxSubscriber<Response>() {
                    @Override
                    public void _onNext(Response response) {
                        if(response!=null&&response.isSuccess()){
                            AppToast.showShortText(SealOperateActivity.this,"信息提交成功");
                            finish();
                        }else {
                            AppToast.showShortText(SealOperateActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    private static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
