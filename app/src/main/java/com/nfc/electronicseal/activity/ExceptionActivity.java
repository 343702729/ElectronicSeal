package com.nfc.electronicseal.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.liuguangqiang.ipicker.IPicker;
import com.nfc.electronicseal.R;
import com.nfc.electronicseal.activity.base.BaseActivity;
import com.nfc.electronicseal.adapter.SelectPicAdapter;
import com.nfc.electronicseal.nfc.MyNFC;
import com.nfc.electronicseal.nfc.NfcDataOrder;
import com.nfc.electronicseal.util.AppToast;
import com.nfc.electronicseal.util.BDLocationUtil;
import com.nfc.electronicseal.util.NFCUtil;
import com.nfc.electronicseal.util.TLog;

import java.io.IOException;
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

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private MyNFC myNFC;


    @Override
    public int layoutView() {
        return R.layout.activity_exception;
    }

    @Override
    public void initview() {
        super.initview();
//        initPicSelect();
//        bdLocationUtil = new BDLocationUtil(this, null);
//        mNfcAdapter = mNfcAdapter.getDefaultAdapter(this);
//        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
//                getClass()), 0);
        myNFC = MyNFC.getInstance(this);
		ToastUtils.showLong("This is test toast");
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


//            String writeStr = "SEALID:241520190519JD,TAXNUMBER:91341003MA2TJA5342,CONTAINERNO:1234562789,SEALSTATUS:2";
            String writeStr = "456";
            MyNFC.getInstance(this).verificationData(writeStr, intent);
//            verificationData(writeStr, intent);
//            NFCUtil.writeNFCToTag(writeStr, intent);
            TLog.log("Write success!!");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void verificationData(String content, Intent intent) throws IOException {
        //0xFF
        myNFC.nfcA.connect();
        byte[] data = new byte[]{(byte)0x1B,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
        byte[] response = myNFC.nfcA.transceive(data);
        System.out.println("The respons:" + response);
        if(response!=null){
            try {
                myNFC.writeData(NFCUtil.getNdefMsgs(content));
                TLog.log("CSss写入成功，等待设备回复");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                myNFC.nfcA.close();
            }
        }
    }

    public void write(int page, List<byte[]> pagesTemp){
        try{
            try {
                byte[] data = new byte[]{(byte)0xA2,(byte)page,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
                for(int i=0; i< pagesTemp.size(); i++){
                    data[2] = pagesTemp.get(i)[0];
                    data[3] = pagesTemp.get(i)[1];
                    data[4] = pagesTemp.get(i)[2];
                    data[5] = pagesTemp.get(i)[3];
                    myNFC.nfcA.transceive(data);
                    data[1] ++;
                }
                TLog.log("Come into write success");
//                Toasty.success(getContext(), "写入成功").show();

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }


    public void writeTag(Tag tag) {

        MifareClassic mfc = MifareClassic.get(tag);

        try {
            mfc.connect();
            boolean auth = false;
            short sectorAddress = 1;
            auth = mfc.authenticateSectorWithKeyA(sectorAddress,
                    MifareClassic.KEY_NFC_FORUM);
            if (auth) {
                // the last block of the sector is used for KeyA and KeyB cannot be overwritted
                mfc.writeBlock(4, "1313838438000000".getBytes());
                mfc.writeBlock(5, "1322676888000000".getBytes());
                mfc.close();
                Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        for (String tech : tag.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false;
        //读取TAG

        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize()
                    + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_NFC_FORUM);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            return metaInfo;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
        return null;

    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }


}
