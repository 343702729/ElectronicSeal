package com.nfc.electronicseal.nfc;

import java.util.ArrayList;
import java.util.List;

public class NfcDataOrder {
    public static String key = DataConfig.hexByteToHexString(new short[]{0,0,0,0,0,0});

    public static List<byte[]> handle(String kind){
//        String order = "8102";
        String data = fixData(kind);
//        String crc =  DataConfig.hexByteToHexString(DataConfig.get_CRC8(DataConfig.hexStringToShort(data)));

//        return splitData(DataConfig.hexStringToHexBytes(data+crc));
        return splitData(DataConfig.hexStringToHexBytes(data));
    }

    private static String fixData(String data){
        data = data.replace(" ", "");
        int length = data.length();
        for(int i=0; i<34-length; i++){
            data += "0";
        }
        return data;
    }

    public static List<byte[]> splitData(byte[] data){
        byte[] newData = new byte[20];
        for (int i=0; i<data.length; i++){
            newData[i] = data[i];
        }
        List<byte[]> pagesTemp = new ArrayList<>();
        for (int i= 0; i < 5; i++) {
            byte[] temp = new byte[4];
            temp[0] = newData[i*4 +0];
            temp[1] = newData[i*4 +1];
            temp[2] = newData[i*4 +2];
            temp[3] = newData[i*4 +3];
            pagesTemp.add(temp);
        }

        return pagesTemp;
    }
}
