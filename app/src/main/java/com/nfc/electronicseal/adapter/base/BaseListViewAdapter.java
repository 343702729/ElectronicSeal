package com.nfc.electronicseal.adapter.base;

import android.widget.BaseAdapter;

import java.util.List;


/**
 * BaseListViewAdapter
 * Created by _Ms on 2017/8/5.
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {

    public List<T> mDataList;

    /**
     * ListViewAdapter
     * @param dataList    数据列表
     */
    public BaseListViewAdapter(List<T> dataList) {
        mDataList = dataList;
    }

    /**
     * ListView设置数据源
     * @param dataList    数据源
     */
    public void setDataList(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO
        int xxx = mDataList == null ? 0 : mDataList.size();
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
