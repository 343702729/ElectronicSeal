package com.nfc.electronicseal.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.node.SealItemNode;
import com.nfc.electronicseal.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class SealItemAdapter extends BaseAdapter {
    private Context context;
    private List<SealItemNode> nodes = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public SealItemAdapter(Context context, List<SealItemNode> nodes){
        this.context = context;
        if(nodes!=null)
            this.nodes = nodes;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateViews(List<SealItemNode> nodes){
        if(nodes==null)
            nodes = new ArrayList<>();
        this.nodes = nodes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public Object getItem(int position) {
        return nodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        SealItemNode node = nodes.get(position);
        if(view==null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_seal, null);
            viewHolder.sealIdTV = view.findViewById(R.id.box_seal_id_tv);
            viewHolder.sealStatusTV = view.findViewById(R.id.box_seal_status_tv);
            viewHolder.sealTimeTV = view.findViewById(R.id.box_seal_time_tv);
            viewHolder.sealPersonTV = view.findViewById(R.id.box_seal_person_tv);
            viewHolder.sealAddrTV = view.findViewById(R.id.box_seal_addr_tv);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.sealIdTV.setText(node.getSealId());

//        if(node.getDealStatus()==1){
//            viewHolder.sealStatusTV.setText("已处理");
//            viewHolder.sealStatusTV.setTextColor(context.getResources().getColor(R.color.green_light));
//        }else {
//            viewHolder.sealStatusTV.setText("待处理");
//            viewHolder.sealStatusTV.setTextColor(context.getResources().getColor(R.color.redDark));
//        }
//
        if(node.getSealDate()!=null)
            viewHolder.sealTimeTV.setText(DateUtil.timeStamp2Date(node.getSealDate()));
        else
            viewHolder.sealTimeTV.setText("");

        viewHolder.sealPersonTV.setText(node.getSealOperName());
        viewHolder.sealAddrTV.setText(node.getSealLoca());
        return view;
    }

    class ViewHolder {
        TextView sealIdTV;
        TextView sealStatusTV;
        TextView sealTimeTV;
        TextView sealPersonTV;
        TextView sealAddrTV;
    }
}
