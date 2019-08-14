package com.nfc.electronicseal.activity.exception;

import android.view.View;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.ExceptionInfoBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.response.ExceptionInfoResponse;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionInfoActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;

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

    private void getExceptionInfoData(int id){
        ExceptionInfoBean bean = new ExceptionInfoBean(id);
        APIRetrofitUtil.getInstance().getExceptionInfoData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ExceptionInfoResponse>("加载数据中...").io_main(this))
                .subscribe(new RxSubscriber<ExceptionInfoResponse>() {
                    @Override
                    public void _onNext(ExceptionInfoResponse exceptionInfoResponse) {

                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
