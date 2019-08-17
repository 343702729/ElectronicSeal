package com.nfc.electronicseal.bean;

public class InspectBean {
    private String sealId;
    private String sealLoca;
    private String lngLat;
    private String descs;
    private String sealPic;

    public InspectBean(String sealId, String sealLoca, String lngLat, String descs, String sealPic) {
        this.sealId = sealId;
        this.sealLoca = sealLoca;
        this.lngLat = lngLat;
        this.descs = descs;
        this.sealPic = sealPic;
    }
}
