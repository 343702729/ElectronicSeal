package com.nfc.electronicseal.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.SelectPicAdapter;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ExceptionActivity extends BaseActivity{
    @BindView(R.id.rv_photos)
    RecyclerView recyclerView;
    private BDLocationUtil bdLocationUtil;

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
//        bdLocationUtil = new BDLocationUtil(this, null);
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

    @OnClick({R.id.add_pic_btn, R.id.location_start_btn, R.id.location_end_btn})
    public void addItemClick(View view){
        switch (view.getId()){
            case R.id.add_pic_btn:
                TLog.log("Come into merchantS item click");
                IPicker.setOnSelectedListener(new PicItemSelectListener());
                IPicker.open(this, null);
                break;
            case R.id.location_start_btn:
                bdLocationUtil.startLocation();
                break;
            case R.id.location_end_btn:
                bdLocationUtil.stopLocation();
                break;
        }

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TLog.log("Come into nfc read");
        try {
            if(!NFCUtil.isNFCCard(intent))
                AppToast.showShortText(this, "该封条不可用");
            String nfcId = NFCUtil.readNFCId(intent);
            String str = NFCUtil.readNFCFromTag(intent);
            TLog.log("The NFC content is:" + str + "   nfcId:" + nfcId + "  size:" + str.getBytes().length);

            String writeStr = "SEALID:241520190519JD,TAXNUMBER:91341003MA2TJA5342,CONTAINERNO:1234562789,SEALSTATUS:2";
            NFCUtil.writeNFCToTag(writeStr, intent);
            TLog.log("Write success!!");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
