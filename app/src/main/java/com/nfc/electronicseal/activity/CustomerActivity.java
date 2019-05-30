package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liuguangqiang.ipicker.adapters.BaseAdapter;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.CustomerListAdapter;
import com.nfc.electronicseal.base.BaseInfoUpdate;
import com.nfc.electronicseal.dialog.JudgeDialog;
import com.nfc.electronicseal.node.CustomerItemNode;
import com.nfc.electronicseal.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomerActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTV;
    @BindView(R.id.listview)
    ListView listView;

    private CustomerListAdapter customerListAdapter;

    @Override
    public int layoutView() {
        return R.layout.activity_customer;
    }

    @Override
    public void initview() {
        super.initview();
        titleTV.setText("我的客服");
        customerListAdapter = new CustomerListAdapter();
        listView.setAdapter(customerListAdapter);
        listView.setOnItemClickListener(customerItemClick);

        List<CustomerItemNode> nodes = new ArrayList<>();
        nodes.add(new CustomerItemNode());
        nodes.add(new CustomerItemNode());
        nodes.add(new CustomerItemNode());
        customerListAdapter.setDataList(nodes);
    }

    @OnClick(R.id.back_ib)
    public void backBtnClick(View view){
        finish();
    }

    private AdapterView.OnItemClickListener customerItemClick = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TLog.log("The position:" + position);
            CustomerItemNode itemNode = (CustomerItemNode)parent.getItemAtPosition(position);
            JudgeDialog dialog = new JudgeDialog(CustomerActivity.this, customerCallInfo);
            dialog.showView(view, "提示", "是否拨打 0551-68888888");
        }

        private BaseInfoUpdate customerCallInfo = new BaseInfoUpdate() {
            @Override
            public void update(Object object) {
                if(object==null)
                    return;
                boolean flag = (boolean)object;
                if(flag){
                    String phone = "0551-68888888";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.trim()));
                    startActivity(intent);
                }

            }
        };
    };


}
