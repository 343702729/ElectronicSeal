package com.nfc.electronicseal.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.SelectPicAdapter;
import com.nfc.electronicseal.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionActivity extends BaseActivity{
    @BindView(R.id.rv_photos)
    RecyclerView recyclerView;

    private SelectPicAdapter selectPicAdapter;
    private ArrayList<String> selectPictures = new ArrayList<>();

    @Override
    public int layoutView() {
        return R.layout.activity_exception;
    }

    @Override
    public void initview() {
        super.initview();
//        initPicSelect();
    }

    /**
     * 图片选择
     */
    private void initPicSelect(){
        IPicker.setCropEnable(false);
        IPicker.setLimit(1);
        selectPicAdapter = new SelectPicAdapter(getApplicationContext(), selectPictures);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(selectPicAdapter);
    }

    @OnClick(R.id.add_pic_btn)
    public void addPicClick(View view){
        TLog.log("Come into merchantS item click");
        IPicker.setOnSelectedListener(new PicItemSelectListener());
        IPicker.open(this, null);
    }

    private class PicItemSelectListener implements IPicker.OnSelectedListener {

        @Override
        public void onSelected(List<String> paths) {
            if(paths==null&&paths.size()<=0)
                return;
            String  selecPic = paths.get(0);
            TLog.log("The pic path is:" + selecPic);
//            showIV.setImageBitmap(PictureUtil.getimage(selecPic));
        }
    }
}
