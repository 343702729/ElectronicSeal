package com.nfc.electronicseal.activity.search;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.SealInfoBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.SealItemNode;
import com.nfc.electronicseal.response.SealInfoResponse;
import com.nfc.electronicseal.util.AppToast;

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
        chipIdTV.setText(node.getChipId());
        sealIdTV.setText(node.getSealId());
        taxNumTV.setText(node.getTaxNumber());
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
