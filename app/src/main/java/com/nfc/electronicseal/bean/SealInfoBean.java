package com.nfc.electronicseal.bean;

public class SealInfoBean {
    private int id;
    private String chipId;
    private String sealId;
    private String taxNumber;
    private String containerNo;

    public SealInfoBean(int id){
        this.id = id;
    }

    public SealInfoBean(String chipId, String sealId, String taxNumber, String containerNo) {
        this.chipId = chipId;
        this.sealId = sealId;
        this.taxNumber = taxNumber;
        this.containerNo = containerNo;
    }
}
