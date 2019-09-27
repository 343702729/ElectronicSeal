package com.nfc.electronicseal.wiget.banner;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.util.DipPxUtil;

import java.util.ArrayList;
import java.util.List;


public class PointView {
	private Context context;
	private static final int ItemMargin = 10;
	private List<ImageView> ivs = new ArrayList<ImageView>();

	public PointView(Context context){
		this.context = context;
	}

	public void addViews(ViewGroup group, int size){
		ImageView iv = null;
		for(int i=0; i<size; i++){
			iv = new ImageView(context);
			if(i==0)
				iv.setBackgroundResource(R.mipmap.point_fc);
			else
				iv.setBackgroundResource(R.mipmap.point_bg);
			if(i!=0){
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				lp.setMargins(DipPxUtil.dip2px(context, ItemMargin), 0, 0, 0);
				iv.setLayoutParams(lp);
			}

			ivs.add(iv);
			group.addView(iv);

		}
	}

	public void setPointSelect(int count){
		if(count>ivs.size())
			return;
		for(int i=0; i<ivs.size(); i++){
			if (count==i){
				ivs.get(i).setBackgroundResource(R.mipmap.point_fc);
			}else{
				ivs.get(i).setBackgroundResource(R.mipmap.point_bg);
			}
		}
	}
}
