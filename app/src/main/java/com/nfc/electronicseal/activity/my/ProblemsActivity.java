package com.nfc.electronicseal.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.activity.search.SealInfoActivity;
import com.nfc.electronicseal.adapter.ProblemAdapter;
import com.nfc.electronicseal.fragment.SearchFragment;
import com.nfc.electronicseal.node.ProblemItemNode;
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

    private PullRefreshListener pullRefreshListener;
    private ProblemAdapter problemAdapter;
    private List<ProblemItemNode> itemNodes = new ArrayList<>();

    @Override
    public int layoutView() {
        return R.layout.activity_problems;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("常见问题");

        itemNodes.add(new ProblemItemNode());
        itemNodes.add(new ProblemItemNode());
        itemNodes.add(new ProblemItemNode());
        itemNodes.add(new ProblemItemNode());
        problemAdapter = new ProblemAdapter(this, itemNodes);
        listView.setAdapter(problemAdapter);
        listView.setOnItemClickListener(problemItemClick);
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private AdapterView.OnItemClickListener problemItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ProblemsActivity.this, ProblemInfoActivity.class);
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
