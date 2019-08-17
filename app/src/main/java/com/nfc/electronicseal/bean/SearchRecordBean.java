package com.nfc.electronicseal.bean;

public class SearchRecordBean {
    private int pageIndex;
    private int pageSize;
    private String sealStatus;
    private String sealId;

    public SearchRecordBean(int pageIndex, int pageSize, String sealStatus, String sealId) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sealStatus = sealStatus;
        this.sealId = sealId;
    }
}
