package com.nfc.electronicseal.activity.search;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.PictureShowActivity;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.exception.ExceptionInfoActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.SealInfoBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.SealItemNode;
import com.nfc.electronicseal.response.SealInfoResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SealInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.chip_id_tv)
    TextView chipIdTV;
    @BindView(R.id.seal_id_tv)
    TextView sealIdTV;
    @BindView(R.id.tax_num_tv)
    TextView taxNumTV;
    @BindView(R.id.seal_time_tv)
    TextView sealTimeTV;
    @BindView(R.id.seal_person_tv)
    TextView sealPerTV;
    @BindView(R.id.seal_addr_tv)
    TextView sealAddrTV;
    @BindView(R.id.seal_desc_tv)
    TextView sealDescTV;
    @BindView(R.id.seal_pics_ll)
    LinearLayout sealPicsLL;
    @BindView(R.id.status_tv)
    TextView statusTV;
    @BindView(R.id.title_seal_time_tv)
    TextView titleSealTimeTV;
    @BindView(R.id.title_seal_person_tv)
    TextView titleSealPerTV;
    @BindView(R.id.title_seal_addr_tv)
    TextView titleSealAddrTV;
    @BindView(R.id.title_seal_desc_tv)
    TextView titleSealDescTV;

    private int Id;

    @Override
    public int layoutView() {
        return R.layout.activity_seal_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("查询详情");
        Id = getIntent().getIntExtra("Id", 0);
    }

    @Override
    public void initData() {
        super.initData();
        getSealInfoData();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private void setViewsData(SealItemNode node){
        if(node==null)
            return;
        if(node.getSealStatus()==1){
            //已施封
            statusTV.setText("已施封");
            statusTV.setTextColor(getResources().getColor(R.color.red));
            titleSealTimeTV.setText("施封时间");
            titleSealPerTV.setText("施封员");
            titleSealAddrTV.setText("施封地点");
            titleSealDescTV.setText("施封描述");
        }else if(node.getSealStatus()==2){
            //已巡检
            statusTV.setText("已巡检");
            statusTV.setTextColor(getResources().getColor(R.color.yellow_light));
            titleSealTimeTV.setText("巡检时间");
            titleSealPerTV.setText("巡检员");
            titleSealAddrTV.setText("巡检地点");
            titleSealDescTV.setText("巡检描述");
        }else if(node.getSealStatus()==3){
            //已完成
            statusTV.setText("已完成");
            statusTV.setTextColor(getResources().getColor(R.color.green_light));
            titleSealTimeTV.setText("拆封时间");
            titleSealPerTV.setText("拆封员");
            titleSealAddrTV.setText("拆封地点");
            titleSealDescTV.setText("拆封描述");
        }
        chipIdTV.setText(node.getChipId());
        sealIdTV.setText(node.getSealId());
        taxNumTV.setText(node.getTaxNumber());

        sealTimeTV.setText(DateUtil.timeStamp2Date(node.getSealDate()));
        sealPerTV.setText(node.getSealOperName());
        sealAddrTV.setText(node.getSealLoca());
        sealDescTV.setText(node.getDescs());

        String pics = node.getSealPic();
        if(!TextUtils.isEmpty(pics)){
            String[] picItems = pics.split(",");

            for (int i=0; i<picItems.length; i++)
                addPictureItem(sealPicsLL, picItems[i], i, picItems);
        }
    }

    private void addPictureItem(LinearLayout linearLayout, String picUrl, final int position, final String[] picUrlList){
        if(TextUtils.isEmpty(picUrl))
            return;
        View view = View.inflate(this, R.layout.item_picture_s, null);
        ImageView itemIV = view.findViewById(R.id.picture_iv);
        itemIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SealInfoActivity.this, PictureShowActivity.class);
                intent.putExtra("Index", position);
                intent.putExtra("Pictures", picUrlList);
                startActivity(intent);
            }
        });
        Glide.with(this).load(picUrl).into(itemIV);
        linearLayout.addView(view);
    }

    private void getSealInfoData(){
        SealInfoBean bean = new SealInfoBean(Id);
        APIRetrofitUtil.getInstance().getSealInfoData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<SealInfoResponse>("加载数据...").io_main(this))
                .subscribe(new RxSubscriber<SealInfoResponse>() {
                    @Override
                    public void _onNext(SealInfoResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            setViewsData(response.getData());
                        }else {
                            AppToast.showShortText(SealInfoActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

}
