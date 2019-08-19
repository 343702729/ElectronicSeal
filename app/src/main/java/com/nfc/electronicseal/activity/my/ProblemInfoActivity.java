package com.nfc.electronicseal.activity.my;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.ProblemInfoBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.ProblemItemNode;
import com.nfc.electronicseal.response.ProblemInfoResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.DateUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ProblemInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.pro_title_tv)
    TextView pTitleTV;
    @BindView(R.id.time_tv)
    TextView timeTV;
    @BindView(R.id.desc_tv)
    TextView descTV;

    @Override
    public int layoutView() {
        return R.layout.activity_problem_info;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("软件使用NFC读取设置权限需要...");
    }

    @Override
    public void initData() {
        super.initData();
        int id = getIntent().getIntExtra("Id", 0);
        getProblemInfoData(id);
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private void setViewsData(ProblemItemNode node){
        if(node==null)
            return;
        pTitleTV.setText(node.getTitle());
        if(node.getCreated()!=null)
            timeTV.setText(DateUtil.timeStamp2Date(node.getCreated()));
        descTV.setText(node.getDescription());
    }

    private void getProblemInfoData(int id){
        ProblemInfoBean bean = new ProblemInfoBean(id);
        APIRetrofitUtil.getInstance().getProblemInfoData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ProblemInfoResponse>("").io_main(this))
                .subscribe(new RxSubscriber<ProblemInfoResponse>() {
                    @Override
                    public void _onNext(ProblemInfoResponse response) {
                        if(response!=null&&response.isSuccess()){
                            setViewsData(response.getData());
                        }else
                            AppToast.showShortText(ProblemInfoActivity.this, response.getMessage());
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
