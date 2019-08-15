package com.nfc.electronicseal.activity.my;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.PasswordUpdateBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.response.Response;
import com.nfc.electronicseal.util.AppToast;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordUpdateActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.old_psw_et)
    EditText oldPswET;
    @BindView(R.id.new_psw_et)
    EditText newPswET;
    @BindView(R.id.sure_psw_et)
    EditText surePswET;

    @Override
    public int layoutView() {
        return R.layout.activity_password_update;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("密码设置");
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.submit_btn)
    public void submitBtnClick(View view){
        String oldPwd = oldPswET.getText().toString();
        String newPwd = newPswET.getText().toString();
        String surePwd = surePswET.getText().toString();

        if(TextUtils.isEmpty(oldPwd)){
            AppToast.showShortText(this, "请输入旧密码");
            return;
        }

        if(TextUtils.isEmpty(newPwd)){
            AppToast.showShortText(this, "请输入新密码");
            return;
        }

        if(TextUtils.isEmpty(surePwd)){
            AppToast.showShortText(this, "请输入确认新密码");
            return;
        }

        if(!newPwd.equals(surePwd)){
            AppToast.showShortText(this, "确认新密码与新密码不符");
            return;
        }
        passwordUpdateDo(oldPwd, newPwd);
    }

    private void passwordUpdateDo(String oldPwd, String newPwd){
        PasswordUpdateBean bean = new PasswordUpdateBean(oldPwd, newPwd);
        APIRetrofitUtil.getInstance().passwordUpdateDo(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<Response>("数据提交...").io_main(this))
                .subscribe(new RxSubscriber<Response>() {
                    @Override
                    public void _onNext(Response response) {
                        if(response!=null&&response.isSuccess()){
                            AppToast.showShortText(PasswordUpdateActivity.this, "密码设置成功");
                            finish();
                        }else {
                            AppToast.showShortText(PasswordUpdateActivity.this, response.getMessage());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
