package com.nfc.electronicseal.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.adapter.base.BaseListViewAdapter;
import com.nfc.electronicseal.node.CustomerItemNode;
import com.nfc.electronicseal.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerListAdapter extends BaseListViewAdapter<CustomerItemNode>{

    public CustomerListAdapter(){
        super(null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CustomerItemNode node = (CustomerItemNode)getItem(position);
        if (convertView == null) {
            convertView = UiUtils.inflate(R.layout.item_customer);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.name)
        TextView nameTV;
        @BindView(R.id.telephone)
        TextView telephoneTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
