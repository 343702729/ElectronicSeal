package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.CustomerActivity;
import com.nfc.electronicseal.activity.exception.ExceptionsActivity;
import com.nfc.electronicseal.activity.inspect.InspectSearchActivity;
import com.nfc.electronicseal.activity.seal.SealSearchActivity;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.activity.unseal.UnSealSearchActivity;
import com.nfc.electronicseal.adapter.ImagePagerAdapter;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.BannerNode;
import com.nfc.electronicseal.node.MenuNode;
import com.nfc.electronicseal.response.MenusResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.UiUtils;
import com.nfc.electronicseal.wiget.banner.MyViewFlow;
import com.nfc.electronicseal.wiget.banner.PointView;
import com.nfc.electronicseal.wiget.banner.ViewFlow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class OperateFragment extends BaseFragment{
    @BindView(R.id.viewflow)
    MyViewFlow viewFlow;
    @BindView(R.id.points_ll)
    LinearLayout pointsLL;

    private boolean isSF = false, isXJ = false, isCF = false, isYC = false;
    private int imgSize = 0;
    private PointView pointView;

    @Override
    public int layoutView() {
        return R.layout.fragment_operate;
    }

    @Override
    public void initData() {
        super.initData();
        getMenusData();
        setNewsBanner();
    }

    @OnClick({R.id.item_sf_ll, R.id.item_xj_ll, R.id.item_cf_ll, R.id.item_yc_ll})
    public void itemLLClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.item_sf_ll:       //施封
                if(!isSF){
                    AppToast.showShortText(getContext(), "该账号无施封权限");
                    return;
                }
                intent = new Intent(UiUtils.getContext(), SealSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_xj_ll:       //巡检
                if(!isXJ){
                    AppToast.showShortText(getContext(), "该账号无巡检权限");
                    return;
                }
                intent = new Intent(UiUtils.getContext(), InspectSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_cf_ll:       //拆封
                if(!isCF){
                    AppToast.showShortText(getContext(), "该账号无拆封权限");
                    return;
                }
                intent = new Intent(UiUtils.getContext(), UnSealSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.item_yc_ll:       //异常
                if(!isYC){
                    AppToast.showShortText(getContext(), "该账号无异常权限");
                    return;
                }
                intent = new Intent(UiUtils.getContext(), ExceptionsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.customer_iv)
    public void customerBtnClick(View view){
        Intent intent = new Intent(UiUtils.getContext(), CustomerActivity.class);
        startActivity(intent);
    }

    private void getMenusData(){
        APIRetrofitUtil.getInstance().getMenusData(UserInfo.getInstance().getToken())
                .compose(new RxHelper<MenusResponse>("").io_no_main_fragment(this))
                .subscribe(new RxSubscriber<MenusResponse>() {
                    @Override
                    public void _onNext(MenusResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            List<MenuNode> nodes = response.getData();
                            for (MenuNode node:nodes){
                                if(!TextUtils.isEmpty(node.getAuthUrl())&&node.getAuthUrl().contains("shifeng")){
                                    isSF = true;
                                }else if(!TextUtils.isEmpty(node.getAuthUrl())&&node.getAuthUrl().contains("xunjian")){
                                    isXJ = true;
                                }else if(!TextUtils.isEmpty(node.getAuthUrl())&&node.getAuthUrl().contains("chaifeng")){
                                    isCF = true;
                                }else if(!TextUtils.isEmpty(node.getAuthUrl())&&node.getAuthUrl().contains("yichang")){
                                    isYC = true;
                                }
                            }
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    private void setNewsBanner(){
        viewFlow.setOnViewSwitchListener(imageSwitchListener);
        List<BannerNode> bannerNodes = new ArrayList<>();
        bannerNodes.add(new BannerNode());
        bannerNodes.add(new BannerNode());
        if(bannerNodes==null||bannerNodes.size()==0)
            return;
        imgSize = bannerNodes.size();
        initBanner(bannerNodes);
    }

    private void initBanner(List<BannerNode> bannerNodes){
        if(bannerNodes==null)
            return;
        List<String> imgsList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

//        for(BannerNode node:bannerNodes){
//        }
        imgsList.add("1");
        imgsList.add("2");
        viewFlow.setAdapter(new ImagePagerAdapter(UiUtils.getContext(), imgsList, urlList, titleList).setInfiniteLoop(true));
        viewFlow.setmSideBuffer(bannerNodes.size());
        viewFlow.setTimeSpan(4000);
        viewFlow.setSelection(bannerNodes.size() * 1000);
        viewFlow.startAutoFlowTimer();
        pointView = new PointView(UiUtils.getContext());
        pointView.addViews(pointsLL, bannerNodes.size());
    }

    private ViewFlow.ViewSwitchListener imageSwitchListener = new ViewFlow.ViewSwitchListener() {
        @Override
        public void onSwitched(View view, int position) {
            if(imgSize == 0)
                return;
            int count = position%imgSize;
            if(pointView!=null)
                pointView.setPointSelect(count);
        }
    };
}
