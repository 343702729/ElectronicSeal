package com.nfc.electronicseal.wiget.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewFlow extends ViewFlow{
	private ViewPager mPager;
	
	public MyViewFlow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setViewPager(ViewPager viewPager) {
        mPager = viewPager;
    }

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (mPager != null)
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPager.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    mPager.requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mPager.requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPager.requestDisallowInterceptTouchEvent(true);
                    break;
            }
		return super.onInterceptTouchEvent(ev);
	}
}
