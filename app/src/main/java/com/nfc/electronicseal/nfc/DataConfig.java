package com.nfc.electronicseal.nfc;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DataConfig {
	/**
	 * hexString 转 hex数组
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToHexBytes(String hexString)
    {
        hexString = hexString.replace(" ", "");

        if (hexString.length() % 2 != 0)
        {
            hexString += "0";
        }

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++)
        {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

	/**
	 * hexString 转 hex数组
	 * @param hexString
	 * @return
	 */
	public static short[] hexStringToShort(String hexString)
	{
		hexString = hexString.replace(" ", "");

		if (hexString.length() % 2 != 0)
		{
			hexString += "0";
		}

		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		short[] d = new short[length];
		for (int i = 0; i < length; i++)
		{
			int pos = i * 2;
			d[i] = (short)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

    private static byte charToByte(char c)
    {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }
    
    
    /**
	 * HEX数组转为HEXString
	 * @param hexBytes
	 * @return
	 */
	public static String hexByteToHexString(byte[] hexBytes)
	{
		String stmp="";
		StringBuilder sb = new StringBuilder("");
		for (int n=0;n<hexBytes.length;n++)
		{
			stmp = Integer.toHexString(hexBytes[n] & 0xFF);
			sb.append((stmp.length()==1)? "0"+stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static String hexByteToHexString(short[] hexBytes)
	{
		String stmp="";
		StringBuilder sb = new StringBuilder("");
		for (int n=0;n<hexBytes.length;n++)
		{
			stmp = Integer.toHexString(hexBytes[n] & 0xFF);
			sb.append((stmp.length()==1)? "0"+stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * HEX数组转为HEXString
	 * @param hexBytes
	 * @return
	 */
	public static String hexByteToHexString(byte[] hexBytes, int length)
	{
		String stmp="";
		StringBuilder sb = new StringBuilder("");
		for (int n=0;n<length;n++)
		{
			stmp = Integer.toHexString(hexBytes[n] & 0xFF);
			sb.append((stmp.length()==1)? "0"+stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static String hexByteToHexString(byte hexBytes)
	{
		String stmp="";
		StringBuilder sb = new StringBuilder("");
		stmp = Integer.toHexString(hexBytes & 0xFF);
		sb.append((stmp.length()==1)? "0"+stmp : stmp);
		sb.append(" ");
		return sb.toString().toUpperCase().trim();
	}

	public static String hexByteToHexString(short hexBytes)
	{
		String stmp="";
		StringBuilder sb = new StringBuilder("");
		stmp = Integer.toHexString(hexBytes & 0xFF);
		sb.append((stmp.length()==1)? "0"+stmp : stmp);
		sb.append(" ");
		return sb.toString().toUpperCase().trim();
	}
    
    /**
     * CRC16校验
     * @param btData
     * @param nLength
     * @param wConstant
     * @return
     */
    public static byte[] CRC_16(byte[] btData, int nLength, int wConstant)
	{
		int CRC=0xFFFF; 
		byte j,Tmp=0; 
		for(int i=0;i<nLength;i++) 
		{     
			if(btData[i]<0)
			{
				CRC^=(btData[i]+256); 
			}
			else
			{
				CRC^=btData[i]; 
			}
			                     
			for (j=0;j<8;j++)                   
			{                                   
				Tmp=(byte)(CRC & 0x0001);                  
				CRC=CRC>>1;                      
				if(Tmp != 0)
				{
					CRC=(CRC^wConstant);
				}
			}                                  
		}

		byte[] result = new byte[2];
		result[0] = (byte)(CRC);
		result[1] = (byte)(CRC>>8);
//		return ((CRC<<8) + (CRC>>8));									//低位在前
		return result;
	}
//
//    public static byte[] Conn_DATA()
//    {
//    	SimpleDateFormat sdf=new SimpleDateFormat("hhmmss");
//    	String sUUID = UUID.randomUUID().toString();
//
//    	sUUID = sUUID.substring(0,8)+sUUID.substring(9,13)+sUUID.substring(14,18)+sUUID.substring(19,23)+sUUID.substring(24);
//    	String date=sdf.format(new java.util.Date());
//    	byte[] bUUID = hexStringToHexBytes(sUUID);
//    	byte[] bDate = hexStringToHexBytes(date);
//
//    	bDate[0] |= bUUID[0];
//    	bDate[1] |= bUUID[1];
//    	bDate[2] |= bUUID[2];
//
//    	byte[] data = new byte[19];
//    	for(int i=0; i<bUUID.length; i++)
//    	{
//    		data[i] = bUUID[i];
//    	}
//    	for(int i=0; i<bDate.length; i++)
//    	{
//    		data[i+16] = bDate[i];
//    	}
//
//    	byte[] newData = new byte[25];
//    	newData[0] = (byte)0xAA;
//    	newData[1] = (byte)(newData.length - 3);
//    	newData[2] = (byte)0xFF;
//
//    	for(int i=0; i<data.length; i++)
//    	{
//    		newData[i+3] = data[i];
//    	}
//
//    	byte[] crcData = new byte[newData.length-4];
//    	for(int i=1; i<newData.length-3; i++)
//    	{
//    		crcData[i-1] = newData[i];
//    	}
//    	int crc = DataConfig.CRC_16(crcData, crcData.length, 0xA001);
//
//    	newData[22] = (byte)(crc>>8);
//    	newData[23] = (byte)crc;
//    	newData[24] = (byte)0x55;
//
//    	return newData;
//    }
    
    private static char[] base64EncodeChars = "QSE7aHRPcCFTGZ1mjkAWLNV08nYIMhf3OXDzxs2qUJKBwd45gvb9poe6lyi+rut/=".toCharArray();
    
	// 1. 解密
	public static byte[] DecryptString(String str) {
		try {
			if ((str.length() % 4) != 0) {
				throw new Exception("不是正确的BASE64编码!");
			}
			String Base64Code = "QSE7aHRPcCFTGZ1mjkAWLNV08nYIMhf3OXDzxs2qUJKBwd45gvb9poe6lyi+rut/=";
			int page = str.length() / 4;
			ArrayList<Byte> outMessage = new ArrayList<Byte>(page * 3);
			char[] message = str.toCharArray();
			for (int i = 0; i < page; i++) {
				byte[] instr = new byte[4];
				instr[0] = (byte) Base64Code.indexOf(message[i * 4]);
				instr[1] = (byte) Base64Code.indexOf(message[i * 4 + 1]);
				instr[2] = (byte) Base64Code.indexOf(message[i * 4 + 2]);
				instr[3] = (byte) Base64Code.indexOf(message[i * 4 + 3]);
				byte[] outstr = new byte[3];
				outstr[0] = (byte) ((instr[0] << 2) ^ ((instr[1] & 0x30) >> 4));
				if (instr[2] != 64) {
					outstr[1] = (byte) ((instr[1] << 4) ^ ((instr[2] & 0x3c) >> 2));
				} else {
					outstr[2] = 0;
				}
				if (instr[3] != 64) {
					outstr[2] = (byte) ((instr[2] << 6) ^ instr[3]);
				} else {
					outstr[2] = 0;
				}
				outMessage.add(outstr[0]);
				if (outstr[1] != 0)
					outMessage.add(outstr[1]);
				if (outstr[2] != 0)
					outMessage.add(outstr[2]);
			}
			Byte[] outbyte = (Byte[]) outMessage.toArray(new Byte[outMessage
					.size()]);
			byte[] b = new byte[outbyte.length];
			for(int i=0; i<outbyte.length; i++)
			{
				b[i] = outbyte[i].byteValue();
			}
			//String s = new String(b, "GB2312");
			return b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new byte[]{};
		}
	}
	
	// 1. 解密
	public static byte[] DecryptString2(byte[] message) {
		try {
//			if ((str.length() % 4) != 0) {
//				throw new Exception("不是正确的BASE64编码!");
//			}
			String Base64Code = "QSE7aHRPcCFTGZ1mjkAWLNV08nYIMhf3OXDzxs2qUJKBwd45gvb9poe6lyi+rut/=";
			int page = message.length / 4;
			ArrayList<Byte> outMessage = new ArrayList<Byte>(page * 3);
//			char[] message = str.toCharArray();
			for (int i = 0; i < page; i++) {
				byte[] instr = new byte[4];
				instr[0] = (byte) Base64Code.indexOf(message[i * 4]);
				instr[1] = (byte) Base64Code.indexOf(message[i * 4 + 1]);
				instr[2] = (byte) Base64Code.indexOf(message[i * 4 + 2]);
				instr[3] = (byte) Base64Code.indexOf(message[i * 4 + 3]);
				byte[] outstr = new byte[3];
				outstr[0] = (byte) ((instr[0] << 2) ^ ((instr[1] & 0x30) >> 4));
				if (instr[2] != 64) {
					outstr[1] = (byte) ((instr[1] << 4) ^ ((instr[2] & 0x3c) >> 2));
				} else {
					outstr[2] = 0;
				}
				if (instr[3] != 64) {
					outstr[2] = (byte) ((instr[2] << 6) ^ instr[3]);
				} else {
					outstr[2] = 0;
				}
				outMessage.add(outstr[0]);
				if (outstr[1] != 0)
					outMessage.add(outstr[1]);
				if (outstr[2] != 0)
					outMessage.add(outstr[2]);
			}
			Byte[] outbyte = (Byte[]) outMessage.toArray(new Byte[outMessage
					.size()]);
			byte[] b = new byte[outbyte.length];
			for(int i=0; i<outbyte.length; i++)
			{
				b[i] = outbyte[i].byteValue();
			}
			//String s = new String(b, "GB2312");
			return b;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new byte[]{};
		}
	}
    
	// 2. 加密
    public static String encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }
    
    /**
    * JAVA获得0-9,a-z,A-Z范围的随机数
    * @param length 随机数长度
    * @return String
    */
    public static String getRandomChar(int length) {
    char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    Random random = new Random();
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < length; i++) {
    buffer.append(chr[random.nextInt(62)]);
    }
    return buffer.toString();
    }
    public static String getRandomChar() {
    return getRandomChar(12);
    }

	//高位在前，低位在后
	public static byte[] int2bytes(int num){
//		byte[] result = new byte[4];
//		result[0] = (byte)((num >>> 24) & 0xff);//说明一
//		result[1] = (byte)((num >>> 16)& 0xff );
//		result[2] = (byte)((num >>> 8) & 0xff );
//		result[3] = (byte)((num >>> 0) & 0xff );

		byte[] result = new byte[2];
		result[0] = (byte)((num >>> 8) & 0xff );
		result[1] = (byte)((num >>> 0) & 0xff );
		return result;
	}

	//高位在前，低位在后
	public static byte[] int2bytes4(int num){
		byte[] result = new byte[4];
		result[0] = (byte)((num >>> 24) & 0xff);//说明一
		result[1] = (byte)((num >>> 16)& 0xff );
		result[2] = (byte)((num >>> 8) & 0xff );
		result[3] = (byte)((num >>> 0) & 0xff );
//
//		byte[] result = new byte[2];
//		result[0] = (byte)((num >>> 8) & 0xff );
//		result[1] = (byte)((num >>> 0) & 0xff );
		return result;
	}

	//高位在前，低位在后
	public static int bytes2int(byte[] bytes) {
		int result = 0;
		if (bytes.length == 4) {
			int a = (bytes[0] & 0xff) << 24;//说明二
			int b = (bytes[1] & 0xff) << 16;
			int c = (bytes[2] & 0xff) << 8;
			int d = (bytes[3] & 0xff);
			result =  a | b | c | d;
		}else if(bytes.length == 2){
			int c = (bytes[0] & 0xff) << 8;
			int d = (bytes[1] & 0xff);
			result = c | d;
		}
		return result;
	}

	/**
	 * 提取数组
	 * @param src
	 * @param begin
	 * @param count
	 * @return
	 */
	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}

	/**
	 * dp转换成px
	 */
	public static int dp2px(Context context,float dpValue){
		float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dpValue*scale+0.5f);
	}

	/**
	 * px转换成dp
	 */
	public static int px2dp(Context context, float pxValue){
		float scale=context.getResources().getDisplayMetrics().density;
		return (int)(pxValue/scale+0.5f);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取现在时间
	 *
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}


	/**
	 * 获取设备IMEI
	 * @param context
	 * @return
	 */
	public static String getImei(Context context){
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}


	private static String[] binaryArray =
			{"0000","0001","0010","0011",
					"0100","0101","0110","0111",
					"1000","1001","1010","1011",
					"1100","1101","1110","1111"};
	/**
	 *
	 * @param bArray
	 * @return 转换为二进制字符串
	 */
	public static String bytes2BinaryStr(byte[] bArray){

		String outStr = "";
		int pos = 0;
		for(byte b:bArray){
			//高四位
			pos = (b&0xF0)>>4;
			outStr+=binaryArray[pos];
			//低四位
			pos=b&0x0F;
			outStr+=binaryArray[pos];
		}
		return outStr;

	}

	/**
	 * 二进制字符串转16进制
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp=new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * ACSII转16进制
	 * @param str
	 * @return
	 */
	public static String convertStringToHex(String str){

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for(int i = 0; i < chars.length; i++){
			hex.append(Integer.toHexString((int)chars[i]));
		}

		return hex.toString();
	}

	/**
	 * 反转数组
	 * @param array
	 * @return
	 */
	public static char[] invertUsingFor(char[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			char temp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = temp;
		}
		return array;
	}

	/**
	 * 反转字符串
	 * @param input
	 * @return
	 */
	public static String reverseWithStringConcat(String input) {
		String output = new String();
		for (int i = (input.length() - 1); i >= 0; i--) {
			output += (input.charAt(i));
		}
		return output;
	}

	private static short[] dscrc_table = {
			0, 94,188,226, 97, 63,221,131,194,156,126, 32,163,253, 31, 65,
			157,195, 33,127,252,162, 64, 30, 95, 1,227,189, 62, 96,130,220,
			35,125,159,193, 66, 28,254,160,225,191, 93, 3,128,222, 60, 98,
			190,224, 2, 92,223,129, 99, 61,124, 34,192,158, 29, 67,161,255,
			70, 24,250,164, 39,121,155,197,132,218, 56,102,229,187, 89, 7,
			219,133,103, 57,186,228, 6, 88, 25, 71,165,251,120, 38,196,154,
			101, 59,217,135, 4, 90,184,230,167,249, 27, 69,198,152,122, 36,
			248,166, 68, 26,153,199, 37,123, 58,100,134,216, 91, 5,231,185,
			140,210, 48,110,237,179, 81, 15, 78, 16,242,172, 47,113,147,205,
			17, 79,173,243,112, 46,204,146,211,141,111, 49,178,236, 14, 80,
			175,241, 19, 77,206,144,114, 44,109, 51,209,143, 12, 82,176,238,
			50,108,142,208, 83, 13,239,177,240,174, 76, 18,145,207, 45,115,
			202,148,118, 40,171,245, 23, 73, 8, 86,180,234,105, 55,213,139,
			87, 9,235,181, 54,104,138,212,149,203, 41,119,244,170, 72, 22,
			233,183, 85, 11,136,214, 52,106, 43,117,151,201, 74, 20,246,168,
			116, 42,200,150, 21, 75,169,247,182,232, 10, 84,215,137,107, 53
	};
	/// <summary>
	/// 获取CRC8
	/// </summary>
	/// <param name="data"></param>
	/// <returns></returns>
	public static short get_CRC8(short[] data, int start, int end)
	{
		short crc = 0;

		for (int i = start; i < end; i++)
		{
			crc = dscrc_table[crc ^ data[i]];
		}
		return crc;
	}

	/// <summary>
	/// 获取CRC8
	/// </summary>
	/// <param name="data"></param>
	/// <returns></returns>
	public static short get_CRC8(short[] data)
	{
		return get_CRC8(data,0,data.length);
	}

	public static byte[] sohrtTobyte(short[] sData){
		byte[] data = new byte[sData.length];
		for (int i=0; i<data.length; i++){
			data[i] = (byte)sData[i];
		}

		return data;
	}
}
