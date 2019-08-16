package com.nfc.electronicseal.bean;

public class SealBean {
    private String sealId;
    private String chipId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String containerNo;
    private String carrier;
    private String sealLoca;
    private String lngLat;
    private String descs;
    private String sealPic;

    public SealBean(String sealId, String chipId, String userName, String userPhone, String userAddress, String containerNo, String carrier, String sealLoca, String lngLat, String descs, String sealPic) {
        this.sealId = sealId;
        this.chipId = chipId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.containerNo = containerNo;
        this.carrier = carrier;
        this.sealLoca = sealLoca;
        this.lngLat = lngLat;
        this.descs = descs;
        this.sealPic = sealPic;
    }
}
