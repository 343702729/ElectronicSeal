package com.nfc.electronicseal.dialog;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.nfc.electronicseal.R;
import com.nfc.electronicseal.base.BaseInfoUpdate;


/**
 * Created by CL on 2018/2/6.
 */

public class JudgeDialog {
    private Context context;
    private BaseInfoUpdate infoUpdate;
    private PopupWindow popupWindow;
    private View contentView;
    private TextView titleTV, contentTV;
    private String str1, str2;

    public JudgeDialog(Context context, BaseInfoUpdate infoUpdate){
        this.context = context;
        this.infoUpdate = infoUpdate;
        initViews();
    }

    public JudgeDialog(Context context, BaseInfoUpdate infoUpdate, String str1, String str2){
        this.context = context;
        this.infoUpdate = infoUpdate;
        this.str1 = str1;
        this.str2 = str2;
        initViews();
    }

    private void initViews(){
        contentView = View.inflate(context, R.layout.dialog_judge, null);
        titleTV = contentView.findViewById(R.id.judge_title_tv);
        contentTV = contentView.findViewById(R.id.judge_content_tv);
        Button cancelBtn = contentView.findViewById(R.id.cancel_btn);
        if(!TextUtils.isEmpty(str1)){
            cancelBtn.setText(str1);
        }
        cancelBtn.setOnClickListener(itemBtnClick);
        Button sureBtn = contentView.findViewById(R.id.sure_btn);
        if(!TextUtils.isEmpty(str2)){
            sureBtn.setText(str2);
        }
        sureBtn.setOnClickListener(itemBtnClick);

        if(popupWindow==null){
            popupWindow = new PopupWindow(context);
            popupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
        }
    }

    public void showView(View parentV, String title, String content){
        titleTV.setText(title);
        contentTV.setText(content);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(parentV, Gravity.BOTTOM, 0, 0);
        popupWindow.update();
    }

    private View.OnClickListener itemBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popupWindow.dismiss();
            if(view.getId()==R.id.cancel_btn){
                if(infoUpdate!=null)
                    infoUpdate.update(false);
            }else if(view.getId()==R.id.sure_btn){
                if(infoUpdate!=null)
                    infoUpdate.update(true);
            }

        }
    };

}
