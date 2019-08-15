package com.nfc.electronicseal.activity;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.PageViewAdapter;
import com.nfc.electronicseal.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PictureShowActivity extends BaseActivity {
    @BindView(R.id.page_index_tv)
    TextView indexTV;
    @BindView(R.id.pic_vp)
    ViewPager viewPager;

    private String[] picList;
    private int index = 0;
    private List<View> pageViews;

    @Override
    public int layoutView() {
        return R.layout.activity_picture_show;
    }

    @Override
    public void initview() {
        super.initview();
        picList = getIntent().getStringArrayExtra("Pictures");
        index = getIntent().getIntExtra("Index", 0);

        pageViews = new ArrayList<>();
        addPicViews(pageViews);
        TLog.log("page size:" + pageViews.size());
        indexTV.setText(String.valueOf(index + 1) + " / " + picList.length);
        PageViewAdapter picPViewAdapter = new PageViewAdapter(pageViews);
        viewPager.setAdapter(picPViewAdapter);
        viewPager.setOnPageChangeListener(new PicChangeListener());
        viewPager.setCurrentItem(index);
    }

    private void addPicViews(List<View> pageviews){
        ImageView iv = null;
        View view = null;
        LayoutInflater inflater = getLayoutInflater();
        for(String item:picList){
            view = inflater.inflate(R.layout.adapter_picture_show, null);
            iv = view.findViewById(R.id.pic_iv);
            Glide.with(this).load(item).into(iv);
            pageviews.add(view);
        }
    }

    @OnClick(R.id.sure_btn)
    public void sureBtnClick(View view){
        finish();
    }

    private class PicChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setPointSel(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        private  void setPointSel(int sel){
            index = sel;
            indexTV.setText(String.valueOf(index + 1) + " / " + picList.length);
        }
    }
}
