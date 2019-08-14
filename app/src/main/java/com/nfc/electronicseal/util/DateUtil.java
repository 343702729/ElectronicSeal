package com.nfc.electronicseal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * yyyyMMdd
     * @return
     */
    public static String getCurrentDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        return df.format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDay1(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * yyyy年MM月dd日
     * @return
     */
    public static String getCurrentDay2(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
        return df.format(new Date());
    }

    /**
     * yyyyMMddHHmmss
     * @param count
     * @return
     */
    public static String getLastDate(int count){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - count);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    /**
     * yyyyMMdd
     * @param count
     * @return
     */
    public static String getLastDay(int count){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - count);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }

    /**
     * 时间戳转换成日期格式字符串yyyy-MM-dd HH:mm:ss
     * @param seconds
     * @return
     */
    public static String timeStamp2Date(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(seconds));

    }

    /**
     * yyyyMMddHHmmss转换成日期格式字符串yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String timeStamp2Date(String date) {
        String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
        return date.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
    }

    /**
     * 时间戳转换成日期格式字符串yyyyMMddHHmmss
     * @param seconds
     * @return
     */
    public static String timeStamp2Date1(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date(seconds));

    }

    /**
     * 时间戳转换成日期格式字符串yyyy-MM-dd HH:mm:ss
     * @param seconds
     * @return
     */
    public static String timeStamp2Date2(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(seconds));

    }

    /**
     * yyyy年MM月dd日转换成日期格式字符串yyyy-MM-dd
     * @param str
     * @return
     */
    public static String timeStamp2Date2(String str) {
        try {
            DateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            return format2.format(format1.parse(str));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = null;
        Date date_end = null;
        try {
            date_start = sdf.parse(startDate);
            date_end = sdf.parse(endDate);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24)) +1;
    }

    /**
     * 获取OCR时间戳（秒）
     * @return
     */
    public static String getOCRTimeStamp(){
        return 90*24*60*60 + "";
//        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static String getStandardDateStr(int year, int month, int day){
        String date = "";
        date = year + "";
        if(month<10)
            date = date + "0" + month;
        else
            date = date + month;
        if(day<10)
            date = date + "0" + day;
        else
            date = date + day;
        return date;
    }

    public static String getDateStr(int year, int month, int day){
        String date = "";
        date = year + "-";
        if(month<10)
            date = date + "0" + month + "-";
        else
            date = date + month + "-";
        if(day<10)
            date = date + "0" + day;
        else
            date = date + day;
        return date;
    }
}
