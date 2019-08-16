package com.nfc.electronicseal.bean;

public class SearchRecordBean {
    private int pageIndex;
    private int pageSize;
    private int sealStatus;

    public SearchRecordBean(int pageIndex, int pageSize, int sealStatus) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sealStatus = sealStatus;
    }
}
