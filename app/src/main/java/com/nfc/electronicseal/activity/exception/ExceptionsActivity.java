package com.nfc.electronicseal.activity.exception;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.ExceptionItemAdapter;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.ExceptionItemsBean;
import com.nfc.electronicseal.data.Constants;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.node.ExceptionItemNode;
import com.nfc.electronicseal.response.ExceptionItemsResponse;
import com.nfc.electronicseal.wiget.pullableview.PullToRefreshLayout;
import com.nfc.electronicseal.wiget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionsActivity extends BaseActivity {
    @BindView(R.id.item_status1_tv)
    TextView status1TV;
    @BindView(R.id.title_tv)
    TextView titleTV;
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
    @BindView(R.id.refreshview)
    PullToRefreshLayout pullRL;
    @BindView(R.id.listview)
    PullableListView listView;

    private PullRefreshListener pullRefreshListener;
    private ExceptionItemAdapter sealItemAdapter;
    private List<ExceptionItemNode> sealItemNodes = new ArrayList<>();
    private int itemIndex = 1;

    private int pageIndex = 0;
    private int pageSize = 10;
    private String dealStatus;

    @Override
    public int layoutView() {
        return R.layout.activity_exceptions;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("异常信息");

        pullRL.isPullDown(false);
        pullRL.isPullUp(false);
        pullRefreshListener = new PullRefreshListener();
        pullRL.setOnRefreshListener(pullRefreshListener);
        sealItemAdapter = new ExceptionItemAdapter(this, sealItemNodes);
        listView.setAdapter(sealItemAdapter);
        listView.setOnItemClickListener(sealItemClick);
    }

    @Override
    public void initData() {
        super.initData();
        getProblemItemsData();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.add_iv)
    public void addBtnClick(View view){
        Intent intent = new Intent(this, ExceptionAddActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @OnClick({R.id.item_status1_tv, R.id.item_status2_tv, R.id.item_status3_tv})
    public void itemStatusClick(View view){

        switch (view.getId()){
            case R.id.item_status1_tv:
                if(itemIndex==1)
                    return;
                itemIndex = 1;
                dealStatus = null;
                setStatusItemV(1);
                break;
            case R.id.item_status2_tv:
                if(itemIndex==2)
                    return;
                itemIndex = 2;
                dealStatus = "0";
                setStatusItemV(2);
                break;
            case R.id.item_status3_tv:
                if(itemIndex==3)
                    return;
                itemIndex = 3;
                dealStatus = "1";
                setStatusItemV(3);
                break;
        }
        pageIndex = 0;
        getProblemItemsData();
    }

    private void setStatusItemV(int index){
        status1TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status2TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status3TV.setTextColor(getResources().getColor(R.color.grayDarkX));
        status1V.setVisibility(View.INVISIBLE);
        status2V.setVisibility(View.INVISIBLE);
        status3V.setVisibility(View.INVISIBLE);
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
        }
    }

    private AdapterView.OnItemClickListener sealItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ExceptionItemNode node = sealItemNodes.get(position);
            Intent intent = new Intent(ExceptionsActivity.this, ExceptionInfoActivity.class);
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
            getProblemItemsData();
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            loadLayout = pullToRefreshLayout;
            pageIndex++;
            getProblemItemsData();
        }

        public void closeRefreshLoad(){
            if(refreshLayout!=null)
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            if(loadLayout!=null)
                loadLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    private void getProblemItemsData(){
        ExceptionItemsBean bean = new ExceptionItemsBean(pageIndex, pageSize, dealStatus);
        APIRetrofitUtil.getInstance().getExceptionItemsData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ExceptionItemsResponse>("加载数据中...").io_main(this))
                .subscribe(new RxSubscriber<ExceptionItemsResponse>() {
                    @Override
                    public void _onNext(ExceptionItemsResponse response) {
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
                        }
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Constants.REQUEST_CODE&&resultCode== Constants.RESULT_CODE){
            pageIndex = 0;
            getProblemItemsData();
        }
    }
}
