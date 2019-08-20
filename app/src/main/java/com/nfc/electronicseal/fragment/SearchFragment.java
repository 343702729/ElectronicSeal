package com.nfc.electronicseal.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseFragment;
import com.nfc.electronicseal.activity.search.SealInfoActivity;
import com.nfc.electronicseal.adapter.SealItemAdapter;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.SearchRecordBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.SealItemNode;
import com.nfc.electronicseal.response.SealItemResponse;
import com.nfc.electronicseal.util.TLog;
import com.nfc.electronicseal.wiget.pullableview.PullToRefreshLayout;
import com.nfc.electronicseal.wiget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class SearchFragment extends BaseFragment {
    @BindView(R.id.search_et)
    EditText searchET;
    @BindView(R.id.item_status1_tv)
    TextView status1TV;
    @BindView(R.id.item_status1_v)
    View status1V;
    @BindView(R.id.item_status2_tv)
    TextView status2TV;
    @BindView(R.id.item_status2_v)
    View status2V;
    @BindView(R.id.item_status3_tv)
    TextView status3TV;
    @BindView(R.id.item_status3_v)
    View status3V;
    @BindView(R.id.item_status4_tv)
    TextView status4TV;
    @BindView(R.id.item_status4_v)
    View status4V;
    @BindView(R.id.refreshview)
    PullToRefreshLayout pullRL;
    @BindView(R.id.listview)
    PullableListView listView;
    @BindView(R.id.no_data_iv)
    ImageView noDataIV;

    private PullRefreshListener pullRefreshListener;
    private SealItemAdapter sealItemAdapter;
    private List<SealItemNode> sealItemNodes = new ArrayList<>();
    private int pageIndex = 0;
    private int pageSize = 10;
    private String sealStatus;
    private String sealId;

    private int itemIndex = 1;

    @Override
    public int layoutView() {
        return R.layout.fragment_search;
    }

    @Override
    public void initview(View view) {
        super.initview(view);
        pullRL.isPullDown(false);
        pullRL.isPullUp(false);
        pullRefreshListener = new PullRefreshListener();
        pullRL.setOnRefreshListener(pullRefreshListener);
        sealItemAdapter = new SealItemAdapter(getContext(), sealItemNodes);
        listView.setAdapter(sealItemAdapter);
        listView.setOnItemClickListener(sealItemClick);
    }

    @Override
    public void initData() {
        super.initData();
        getRecordsData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        TLog.log("Come into onHiddenChanged:" + hidden);
        if(hidden){
            searchET.clearFocus();
            searchET.setCursorVisible(false);
        }
    }

    @OnClick(R.id.search_tv)
    public void searchBtnClick(View view){
        pageIndex = 0;
        getRecordsData();
    }

    @OnClick({R.id.item_status1_tv, R.id.item_status2_tv, R.id.item_status3_tv, R.id.item_status4_tv})
    public void itemStatusClick(View view){

        switch (view.getId()){
            case R.id.item_status1_tv:
                if(itemIndex==1)
                    return;
                itemIndex = 1;
                sealStatus = null;
                setStatusItemV(1);
                break;
            case R.id.item_status2_tv:
                if(itemIndex==2)
                    return;
                itemIndex = 2;
                sealStatus = "3";
                setStatusItemV(2);
                break;
            case R.id.item_status3_tv:
                if(itemIndex==3)
                    return;
                itemIndex = 3;
                sealStatus = "1";
                setStatusItemV(3);
                break;
            case R.id.item_status4_tv:
                if(itemIndex==4)
                    return;
                itemIndex = 4;
                sealStatus = "2";
                setStatusItemV(4);
                break;
        }

        pageIndex = 0;
        getRecordsData();
    }

    private void setStatusItemV(int index){
        status1TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status2TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status3TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status4TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status1V.setVisibility(View.INVISIBLE);
        status2V.setVisibility(View.INVISIBLE);
        status3V.setVisibility(View.INVISIBLE);
        status4V.setVisibility(View.INVISIBLE);
        switch (index){
            case 1:
                status1TV.setTextColor(getResources().getColor(R.color.redDark));
                status1V.setVisibility(View.VISIBLE);
                break;
            case 2:
                status2TV.setTextColor(getResources().getColor(R.color.redDark));
                status2V.setVisibility(View.VISIBLE);
                break;
            case 3:
                status3TV.setTextColor(getResources().getColor(R.color.redDark));
                status3V.setVisibility(View.VISIBLE);
                break;
            case 4:
                status4TV.setTextColor(getResources().getColor(R.color.redDark));
                status4V.setVisibility(View.VISIBLE);
                break;
        }
    }

    private AdapterView.OnItemClickListener sealItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SealItemNode node = sealItemNodes.get(position);
            Intent intent = new Intent(getContext(), SealInfoActivity.class);
            intent.putExtra("Id", node.getId());
            startActivity(intent);
        }
    };

    private class PullRefreshListener implements PullToRefreshLayout.OnRefreshListener{
        private PullToRefreshLayout refreshLayout, loadLayout;

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            refreshLayout = pullToRefreshLayout;
            pageIndex = 0;
            getRecordsData();
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            loadLayout = pullToRefreshLayout;
            pageIndex++;
            getRecordsData();
        }

        public void closeRefreshLoad(){
            if(refreshLayout!=null)
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            if(loadLayout!=null)
                loadLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    private void getRecordsData(){
        sealId = searchET.getText().toString();
        SearchRecordBean bean = new SearchRecordBean(pageIndex, pageSize, sealStatus, sealId);
        APIRetrofitUtil.getInstance().getSearchRecordsData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<SealItemResponse>("加载数据中...").io_main_fragment(this))
                .subscribe(new RxSubscriber<SealItemResponse>() {
                    @Override
                    public void _onNext(SealItemResponse response) {
                        if(response!=null&&response.isSuccess()&&response.getData()!=null){
                            if(pullRefreshListener!=null)
                                pullRefreshListener.closeRefreshLoad();
                            if(pageIndex==0)
                                sealItemNodes = response.getData();
                            else
                                sealItemNodes.addAll(response.getData());
                            if(sealItemAdapter!=null)
                                sealItemAdapter.updateViews(sealItemNodes);

                            if(response.getTotal()>0)
                                pullRL.isPullDown(true);
                            else
                                pullRL.isPullDown(false);

                            if(response.getTotal()<pageSize*(pageIndex+1))
                                pullRL.isPullUp(false);
                            else
                                pullRL.isPullUp(true);

                            if(sealItemNodes!=null&&sealItemNodes.size()!=0){
                                noDataIV.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
