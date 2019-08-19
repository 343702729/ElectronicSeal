package com.nfc.electronicseal.bean;

public class ProblemItemsBean {
    private int pageIndex = 0;
    private int pageSize = 10;

    public ProblemItemsBean(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
