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
import com.nfc.electronicseal.wiget.view.ItemSealInfoView;

import java.util.List;

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
    @BindView(R.id.box_no_tv)
    TextView boxNoTV;
    @BindView(R.id.status_tv)
    TextView statusTV;
    @BindView(R.id.items_ll)
    LinearLayout itemsLL;

    private int Id;
    private String chipId;
    private String sealId;
    private String taxNumber;
    private String containerNo;

    private SealItemNode electronic;

    @Override
    public int layoutView() {
        return R.layout.activity_seal_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("查询详情");
        Id = getIntent().getIntExtra("Id", 0);
        chipId = getIntent().getStringExtra("ChipId");
        sealId = getIntent().getStringExtra("SealId");
        taxNumber = getIntent().getStringExtra("TaxNumber");
        containerNo = getIntent().getStringExtra("ContainerNo");

        chipIdTV.setText(chipId);
        sealIdTV.setText(sealId);
        taxNumTV.setText(taxNumber);
        boxNoTV.setText(containerNo);
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

    private void setViewsData(List<SealItemNode> nodes){
        if(nodes==null)
            return;
        if(electronic!=null){
            if(electronic.getSealStatus()==1){
                statusTV.setText("已施封");
                statusTV.setTextColor(getResources().getColor(R.color.red));
            }else if(electronic.getSealStatus()==2){
                statusTV.setText("已巡检");
                statusTV.setTextColor(getResources().getColor(R.color.yellow_light));
            }else if(electronic.getSealStatus()==3){
                statusTV.setText("已完成");
                statusTV.setTextColor(getResources().getColor(R.color.green_light));
            }
        }

        for (SealItemNode node:nodes){
            ItemSealInfoView itemInfoV = new ItemSealInfoView(SealInfoActivity.this, node);
            itemsLL.addView(itemInfoV);
        }

    }



    private void getSealInfoData(){
        SealInfoBean bean = new SealInfoBean(chipId, sealId, taxNumber, containerNo);
        APIRetrofitUtil.getInstance().getSealInfoData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<SealInfoResponse>("加载数据...").io_main(this))
                .subscribe(new RxSubscriber<SealInfoResponse>() {
                    @Override
                    public void _onNext(SealInfoResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            electronic = response.getElectronic();
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
