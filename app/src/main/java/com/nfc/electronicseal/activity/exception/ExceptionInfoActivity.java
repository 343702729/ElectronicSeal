package com.nfc.electronicseal.activity.exception;

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
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.ExceptionInfoBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.ExceptionInfoNode;
import com.nfc.electronicseal.response.ExceptionInfoResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.box_status_tv)
    TextView boxStatusTV;
    @BindView(R.id.box_seal_time_tv)
    TextView boxSealTimeTV;
    @BindView(R.id.box_seal_person_tv)
    TextView boxSealPersonTV;
    @BindView(R.id.box_seal_addr_tv)
    TextView boxSealAddrTV;
    @BindView(R.id.expt_type_tv)
    TextView exptTypeTV;
    @BindView(R.id.expt_desc_tv)
    TextView exptDescTV;
    @BindView(R.id.expt_result)
    TextView exptResultTV;
    @BindView(R.id.expt_person_tv)
    TextView exptPersonTV;
    @BindView(R.id.expt_pics_ll)
    LinearLayout exptPicsLL;

    @Override
    public int layoutView() {
        return R.layout.activity_exception_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("异常信息");
    }

    @Override
    public void initData() {
        super.initData();
        int id = getIntent().getIntExtra("Id", 0);
        getExceptionInfoData(id);
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private void setViewsData(ExceptionInfoNode infoNode){
        if(infoNode==null)
            return;
        if(infoNode.getDealStatus()==1) {
            boxStatusTV.setText("已处理");
            boxStatusTV.setTextColor(getResources().getColor(R.color.green_light));
        }else {
            boxStatusTV.setText("待处理");
            boxStatusTV.setTextColor(getResources().getColor(R.color.redDark));
        }
        if(infoNode.getUpdateTime()!=null)
            boxSealTimeTV.setText(DateUtil.timeStamp2Date(infoNode.getUpdateTime()));
        boxSealPersonTV.setText(infoNode.getSealOperName());
        boxSealAddrTV.setText(infoNode.getSealLoca());

        if(infoNode.getExceptionType()==1){
            //机械故障
            exptTypeTV.setText("机械故障");
        }else if(infoNode.getExceptionType()==2){
            //芯片故障
            exptTypeTV.setText("芯片故障");
        }else if(infoNode.getExceptionType()==3){
            //其他
            exptTypeTV.setText("其他");
        }

        exptDescTV.setText(infoNode.getSealDestr());
        if(!TextUtils.isEmpty(infoNode.getDealPersonName()))
            exptPersonTV.setText(infoNode.getDealPersonName());
        if(!TextUtils.isEmpty(infoNode.getDealResult()))
            exptResultTV.setText(infoNode.getDealResult());
        String pics = infoNode.getSealPic();
        if(!TextUtils.isEmpty(pics)){
            String[] picItems = pics.split(",");

            for (int i=0; i<picItems.length; i++)
                addPictureItem(exptPicsLL, picItems[i], i, picItems);
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
                Intent intent = new Intent(ExceptionInfoActivity.this, PictureShowActivity.class);
                intent.putExtra("Index", position);
                intent.putExtra("Pictures", picUrlList);
                startActivity(intent);
            }
        });
        Glide.with(this).load(picUrl).into(itemIV);
        linearLayout.addView(view);
    }

    /**
     *
     * @param id
     */
    private void getExceptionInfoData(int id){
        ExceptionInfoBean bean = new ExceptionInfoBean(id);
        APIRetrofitUtil.getInstance().getExceptionInfoData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ExceptionInfoResponse>("加载数据中...").io_main(this))
                .subscribe(new RxSubscriber<ExceptionInfoResponse>() {
                    @Override
                    public void _onNext(ExceptionInfoResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            setViewsData(response.getData());
                        }else {
                            AppToast.showShortText(ExceptionInfoActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

}
