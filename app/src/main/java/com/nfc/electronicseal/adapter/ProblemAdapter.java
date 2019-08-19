package com.nfc.electronicseal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.node.ProblemItemNode;
import com.nfc.electronicseal.node.SealItemNode;

import java.util.ArrayList;
import java.util.List;

public class ProblemAdapter extends BaseAdapter {
    private Context context;
    private List<ProblemItemNode> nodes = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ProblemAdapter(Context context, List<ProblemItemNode> nodes){
        this.context = context;
        if(nodes!=null)
            this.nodes = nodes;
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateViews(List<ProblemItemNode> nodes){
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
        ProblemItemNode node = nodes.get(position);
        if(view==null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_problem, null);
            viewHolder.titleTV = view.findViewById(R.id.title_tv);
            viewHolder.descTV = view.findViewById(R.id.desc_tv);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.titleTV.setText(node.getTitle());
        viewHolder.descTV.setText(node.getDescription());
        return view;
    }

    class ViewHolder {
        TextView titleTV;
        TextView descTV;
    }
}
