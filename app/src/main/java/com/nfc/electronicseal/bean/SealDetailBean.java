package com.nfc.electronicseal.bean;

public class SealDetailBean {
    private String sealId;
    private String chipId;
    private String taxNumber;
    private String containerNo;

    public SealDetailBean(String sealId, String chipId, String taxNumber, String containerNo) {
        this.sealId = sealId;
        this.chipId = chipId;
        this.taxNumber = taxNumber;
        this.containerNo = containerNo;
    }
}
