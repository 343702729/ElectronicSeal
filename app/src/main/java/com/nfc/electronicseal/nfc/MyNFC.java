package com.nfc.electronicseal.nfc;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.widget.Toast;

import com.nfc.electronicseal.util.AppToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LU-MAC on 2019-6-1.
 */

public class MyNFC {
    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static MyNFC instance;
    public NfcA nfcA;
//    public Ndef nfcA;
    private Context context;
    private Timer timerNFC;
    public Tag tag;

    //构造器私有化
    private MyNFC(Context context){
        this.context = context;
    }

    //方法同步，调用效率低
    public static synchronized MyNFC getInstance(Context context){
        if(instance==null){
            instance=new MyNFC(context);
        }
        return instance;
    }

//    public void setOnNFCListener(OnNFCListener onNFCListener){
//        this.onNFCListener = onNFCListener;
//    }

    public void setTag(Tag tag){
        this.tag = tag;
    }

    public void readData() throws IOException {
        if (timerNFC != null) {
            timerNFC.cancel();
        }
        timerNFC = new Timer();
        TimerTask timerTaskNFC;
        timerTaskNFC = new TimerTask() {
            List<byte[]> pagesTemp = new ArrayList<>();

            @Override
            public void run() {
                try {

                    for (int i = 0; i < 7; i++) {
                        byte[] DATA_READ = {
                                (byte) 0x3A,
                                (byte) (i * 33),
                                (byte) ((i * 33) + 32)
                        };
                        byte[] data = nfcA.transceive(DATA_READ);
                        for (int j = 0; j < data.length; j += 4) {
                            byte[] temp = new byte[4];
                            temp[0] = data[j];
                            temp[1] = data[j + 1];
                            temp[2] = data[j + 2];
                            temp[3] = data[j + 3];
                            pagesTemp.add(temp);
                        }
                    }
//                    setNFCDataMode(pagesTemp);
                } catch (final Exception e) {
                    ((Activity)  context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppToast.showShortText(context, e.getMessage()+"标签丢失或读取失败，请重新把标签靠近手机");
                        }
                    });
//                    timerNFC.cancel();
                }
            }
        };
        timerNFC.schedule(timerTaskNFC, 500);
    }

    public void writeData(byte[] contents){
        if(contents==null)
            return;
        try {
            int page = 4;
            byte[] item = new byte[contents.length + 4];
            item[0] = (byte)0x03;
            item[1] = (byte)contents.length;
            item[2] = (byte)0xD1;
            for (int i=0; i < contents.length; i++ ){
                item[i+3] = contents[i];
            }
            item[contents.length + 3] = (byte)0xFE;
            for (int i=0; i<item.length;){
                byte[] data = new byte[]{(byte)0xA2,(byte)page,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF};
                if(i++<item.length){
                    data[2] = item[i];
                }
                if(i++<item.length){
                    data[3] = item[i];
                }
                if(i++<item.length){
                    data[4] = item[i];
                }
                if(i++<item.length){
                    data[5] = item[i];
                }
                nfcA.transceive(data);
                page++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
