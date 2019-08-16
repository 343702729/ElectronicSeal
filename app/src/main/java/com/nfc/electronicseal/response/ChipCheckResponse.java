package com.nfc.electronicseal.response;

public class ChipCheckResponse extends Response<ChipCheckResponse> {
    private int id;
    private String chipId;
    private String sealId;
    private String taxNumber;
    private String containerNo;
    private int sealStatus;
    private Long created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChipId() {
        return chipId;
    }

    public void setChipId(String chipId) {
        this.chipId = chipId;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public int getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(int sealStatus) {
        this.sealStatus = sealStatus;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

}
