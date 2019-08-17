package com.nfc.electronicseal.bean;

public class ExceptionItemsBean {
    private int pageIndex;
    private int pageSize;
    private String dealStatus;

    public ExceptionItemsBean(int pageIndex, int pageSize, String dealStatus){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dealStatus = dealStatus;
    }
}
