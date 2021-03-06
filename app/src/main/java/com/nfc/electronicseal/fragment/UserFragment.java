package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.activity.my.InstructionActivity;
import com.nfc.electronicseal.activity.my.ProblemsActivity;
import com.nfc.electronicseal.activity.my.SettingActivity;
import com.nfc.electronicseal.activity.my.SupportInfoActivity;
import com.nfc.electronicseal.activity.my.UserInfoActivity;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.bean.VersionBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.VersionNode;
import com.nfc.electronicseal.response.VersionResponse;
import com.nfc.electronicseal.util.AppInfo;
import com.nfc.electronicseal.version.VersionUtil;
import com.nfc.electronicseal.wiget.GlideCircleTransform;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class UserFragment extends BaseFragment {
    @BindView(R.id.user_head_iv)
    ImageView headIV;
    @BindView(R.id.version_tv)
    TextView versionTV;
    @BindView(R.id.name_tv)
    TextView nameTV;
    @BindView(R.id.tel_tv)
    TextView telTV;

    private Context context;

    @Override
    public int layoutView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initview(View view) {
        super.initview(view);
        context = getActivity();
        Glide.with(this).load(UserInfo.getInstance().getUserNode().getEmployeeImage())
                //圆形
                .transform(new GlideCircleTransform(getContext()))
                .into(headIV);
        versionTV.setText("V" + AppInfo.appVersion);

        nameTV.setText(UserInfo.getInstance().getUserNode().getName());
        telTV.setText(UserInfo.getInstance().getUserNode().getTelephone());

        UserInfo.getInstance().setHeadImgUpdate(new UserHeadImgUpdate());
    }

    @Override
    public void initData() {
        super.initData();
    }

    @OnClick({R.id.setting_ll, R.id.user_info_ll, R.id.user_instruction_ll, R.id.user_problems_ll, R.id.user_version_ll, R.id.user_support_ll})
    public void lineItemClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.setting_ll:
                intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.user_info_ll:
                intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_instruction_ll:
                intent = new Intent(getContext(), InstructionActivity.class);
                startActivity(intent);
                break;
            case R.id.user_problems_ll:
                intent = new Intent(getContext(), ProblemsActivity.class);
                startActivity(intent);
                break;
            case R.id.user_version_ll:
                checkVersionData();
                break;
            case R.id.user_support_ll:
                intent = new Intent(getContext(), SupportInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class UserHeadImgUpdate implements BaseInfoUpdate{
        @Override
        public void update(Object object) {
            Glide.with(context).load(UserInfo.getInstance().getUserNode().getEmployeeImage())
                    //圆形
                    .transform(new GlideCircleTransform(getContext()))
                    .into(headIV);
        }
    }

    private void checkVersionData(){
        VersionBean bean = new VersionBean(0);
        APIRetrofitUtil.getInstance().getVersionData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<VersionResponse>("获取数据中...").io_main_fragment(this))
                .subscribe(new RxSubscriber<VersionResponse>() {
                    @Override
                    public void _onNext(VersionResponse response) {
                        if(response!=null&&response.isSuccess()){
                            checkVersion(response.getData());
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    /**
     * 版本更新检测
     * @param node
     */
    private void checkVersion(VersionNode node){
        if(node==null)
            return;
        VersionUtil versionUtil = new VersionUtil();
        boolean flage = versionUtil.checkVersion(getActivity(), node);
    }
}
