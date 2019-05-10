package com.nfc.electronicseal.activity.exception;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.search.SealInfoActivity;
import com.nfc.electronicseal.adapter.SealItemAdapter;
import com.nfc.electronicseal.fragment.SearchFragment;
import com.nfc.electronicseal.node.SealItemNode;
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
    private SealItemAdapter sealItemAdapter;
    private List<SealItemNode> sealItemNodes = new ArrayList<>();
    private int itemIndex = 1;

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
        sealItemNodes.add(new SealItemNode());
        sealItemNodes.add(new SealItemNode());
        sealItemNodes.add(new SealItemNode());
        sealItemAdapter = new SealItemAdapter(this, sealItemNodes);
        listView.setAdapter(sealItemAdapter);
        listView.setOnItemClickListener(sealItemClick);
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    @OnClick(R.id.add_iv)
    public void addBtnClick(View view){
        Intent intent = new Intent(this, ExceptionAddActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.item_status1_tv, R.id.item_status2_tv, R.id.item_status3_tv})
    public void itemStatusClick(View view){

        switch (view.getId()){
            case R.id.item_status1_tv:
                if(itemIndex==1)
                    return;
                itemIndex = 1;
                setStatusItemV(1);
                break;
            case R.id.item_status2_tv:
                if(itemIndex==2)
                    return;
                itemIndex = 2;
                setStatusItemV(2);
                break;
            case R.id.item_status3_tv:
                if(itemIndex==3)
                    return;
                itemIndex = 3;
                setStatusItemV(3);
                break;
        }
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
            Intent intent = new Intent(ExceptionsActivity.this, ExceptionInfoActivity.class);
            startActivity(intent);
        }
    };

    private class PullRefreshListener implements PullToRefreshLayout.OnRefreshListener{
        private PullToRefreshLayout refreshLayout, loadLayout;

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            refreshLayout = pullToRefreshLayout;
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            loadLayout = pullToRefreshLayout;
        }
    }
}