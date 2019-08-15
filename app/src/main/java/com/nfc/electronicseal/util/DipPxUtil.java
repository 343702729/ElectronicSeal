package com.nfc.electronicseal.util;

import android.content.Context;

/**
 * @author Administrator
 * @version 2014-10-9
 */
public class DipPxUtil {

	/**
	 * dipתpx
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue){
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(dipValue * scale + 0.5f); 
	}

	/**
	 * pxתdip
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int)(pxValue / scale + 0.5f); 
	} 

}
