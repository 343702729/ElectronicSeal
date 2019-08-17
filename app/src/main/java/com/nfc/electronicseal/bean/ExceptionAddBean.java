package com.nfc.electronicseal.bean;

public class ExceptionAddBean {
    private String sealId;
    private int exceptionType;
    private String sealDestr;
    private String sealPic;
    private String lngLat;
    private String sealLoca;

    public ExceptionAddBean(String sealId, int exceptionType, String sealDestr, String sealPic, String lngLat, String sealLoca){
        this.sealId = sealId;
        this.exceptionType = exceptionType;
        this.sealDestr = sealDestr;
        this.sealPic = sealPic;
        this.lngLat = lngLat;
        this.sealLoca = sealLoca;
    }
}
