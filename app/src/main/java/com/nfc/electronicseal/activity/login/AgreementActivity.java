package com.nfc.electronicseal.activity.login;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.response.ProtocolInfoResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class AgreementActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.content_tv)
    TextView contentTV;

    @Override
    public int layoutView() {
        return R.layout.activity_agreement;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("平台服务协议");
    }

    @Override
    public void initData() {
        super.initData();
        getProtocolInfoData();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private void getProtocolInfoData(){
        APIRetrofitUtil.getInstance().getProtocolInfoData()
                .compose(new RxHelper<ProtocolInfoResponse>("").io_no_main(this))
                .subscribe(new RxSubscriber<ProtocolInfoResponse>() {
                    @Override
                    public void _onNext(ProtocolInfoResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            contentTV.setText(Html.fromHtml(response.getData().getContent()));
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
