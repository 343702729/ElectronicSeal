package com.nfc.electronicseal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CL on 2018/1/7.
 */

public class PageViewAdapter extends PagerAdapter {
    private List<View> picVList;

    public PageViewAdapter(List<View> picVList){
        this.picVList = picVList;
    }
    @Override
    public int getCount() {
        return picVList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            ((ViewPager)container).removeView(picVList.get(position));
        }catch (Exception e){

        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(picVList.get(position));
        return picVList.get(position);
    }
}
