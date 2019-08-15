package com.nfc.electronicseal.bean;

public class ExceptionAddBean {
    private String sealId;
    private int exceptionType;
    private String sealDestr;
    private String sealPic;

    public ExceptionAddBean(String sealId, int exceptionType, String sealDestr, String sealPic){
        this.sealId = sealId;
        this.exceptionType = exceptionType;
        this.sealDestr = sealDestr;
        this.sealPic = sealPic;
    }
}
