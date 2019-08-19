package com.nfc.electronicseal.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.exception.ExceptionsActivity;
import com.nfc.electronicseal.activity.search.SealInfoActivity;
import com.nfc.electronicseal.adapter.ProblemAdapter;
import com.nfc.electronicseal.api.APIRetrofitUtil;
import com.nfc.electronicseal.api.util.RxHelper;
import com.nfc.electronicseal.api.util.RxSubscriber;
import com.nfc.electronicseal.bean.ProblemItemsBean;
import com.nfc.electronicseal.data.UserInfo;
import com.nfc.electronicseal.fragment.SearchFragment;
import com.nfc.electronicseal.node.ProblemItemNode;
import com.nfc.electronicseal.response.ProblemItemsResponse;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.wiget.pullableview.PullToRefreshLayout;
import com.nfc.electronicseal.wiget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProblemsActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.listview)
    PullableListView listView;
    @BindView(R.id.refreshview)
    PullToRefreshLayout pullRL;

    private PullRefreshListener pullRefreshListener;
    private ProblemAdapter problemAdapter;
    private List<ProblemItemNode> itemNodes = new ArrayList<>();

    private int pageIndex = 0;
    private int pageSize = 10;

    @Override
    public int layoutView() {
        return R.layout.activity_problems;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("常见问题");

        pullRL.isPullDown(false);
        pullRL.isPullUp(false);
        pullRefreshListener = new PullRefreshListener();
        pullRL.setOnRefreshListener(pullRefreshListener);
        problemAdapter = new ProblemAdapter(this, itemNodes);
        listView.setAdapter(problemAdapter);
        listView.setOnItemClickListener(problemItemClick);
    }

    @Override
    public void initData() {
        super.initData();
        getProblemsData();
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private AdapterView.OnItemClickListener problemItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ProblemItemNode node = itemNodes.get(position);
            Intent intent = new Intent(ProblemsActivity.this, ProblemInfoActivity.class);
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
            getProblemsData();
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            loadLayout = pullToRefreshLayout;
            pageIndex++;
            getProblemsData();
        }

        public void closeRefreshLoad(){
            if(refreshLayout!=null)
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            if(loadLayout!=null)
                loadLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    private void getProblemsData(){
        ProblemItemsBean bean = new ProblemItemsBean(pageIndex, pageSize);
        APIRetrofitUtil.getInstance().getProblemsItemsData(UserInfo.getInstance().getToken(), bean)
                .compose(new RxHelper<ProblemItemsResponse>("加载数据中...").io_main(this))
                .subscribe(new RxSubscriber<ProblemItemsResponse>() {
                    @Override
                    public void _onNext(ProblemItemsResponse response) {
                        if(response!=null&&response.isSuccess()){
                            if(pullRefreshListener!=null)
                                pullRefreshListener.closeRefreshLoad();
                            if(pageIndex==0)
                                itemNodes = response.getData();
                            else
                                itemNodes.addAll(response.getData());
                            if(problemAdapter!=null)
                                problemAdapter.updateViews(itemNodes);

                            if(response.getTotal()>0)
                                pullRL.isPullDown(true);
                            else
                                pullRL.isPullDown(false);

                            if(response.getTotal()<pageSize*(pageIndex+1))
                                pullRL.isPullUp(false);
                            else
                                pullRL.isPullUp(true);
                        }else
                            AppToast.showShortText(ProblemsActivity.this, response.getMessage());
                    }

                    @Override
                    public void _onError(String msg) {

                    }
                });
    }
}
