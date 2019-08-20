package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.nfc.electronicseal.MainActivity;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RetrofitServiceUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.LoginBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.response.LoginResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.SharePreferenceUtils;
import com.nfc.electronicseal.util.TLog;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.account_et)
    EditText accountET;
    @BindView(R.id.password_et)
    EditText passwordET;

    @Override
    public int layoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        String mac = android.os.Build.SERIAL;
        TLog.log("The mac is:" + mac);
        String account = SharePreferenceUtils.getUserAccount(this);
        String password = SharePreferenceUtils.getUserPassword(this);
        if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(password)){
            accountET.setText(account);
            passwordET.setText(password);
        }
    }

    @OnClick(R.id.login_btn)
    public void loginBtnClick(View view){
        String account = accountET.getText().toString();
        String password = passwordET.getText().toString();
//        account = "cs001";
//        password = "123456";

        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(password)){
            AppToast.showShortText(this, "请输入正确账号密码！");
            return;
        }

        loginDo(account, password);

    }

    @OnClick(R.id.test_btn)
    public void testBtnClick(View view){
        Intent intent = new Intent(this, ExceptionActivity.class);
        startActivity(intent);
    }

    private void loginDo(final String account, final String password){
        LoginBean bean = new LoginBean(account, password);
        APIRetrofitUtil.getInstance().getLoginData(bean)
                .compose(new RxHelper<LoginResponse>("正在登录,请稍等...").io_main(this))
                .subscribe(new RxSubscriber<LoginResponse>() {
                    @Override
                    public void _onNext(LoginResponse response) {
                        if(response!=null&&response.isSuccess()){
                            SharePreferenceUtils.saveUserAccount(LoginActivity.this, account);
                            SharePreferenceUtils.saveUserPassword(LoginActivity.this, password);
                            UserInfo.getInstance().setToken(response.getToken());
                            UserInfo.getInstance().setUserNode(response.getData());
                            UserInfo.getInstance().setCustomerPhoneList(response.getCustomerPhoneList());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            AppToast.showShortText(LoginActivity.this, response.getMessage());
                        }

                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
