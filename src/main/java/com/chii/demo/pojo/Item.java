package com.chii.demo.pojo;

public class Item {
    private String iId;

    private String oId;

    private String mId;

    private Integer iNum;

    private Long iPrice;

    public Item(String iId, String oId, String mId, Integer iNum, Long iPrice) {
        this.iId = iId;
        this.oId = oId;
        this.mId = mId;
        this.iNum = iNum;
        this.iPrice = iPrice;
    }

    public Item() {
        super();
    }

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId == null ? null : iId.trim();
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId == null ? null : oId.trim();
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public Integer getiNum() {
        return iNum;
    }

    public void setiNum(Integer iNum) {
        this.iNum = iNum;
    }

    public Long getiPrice() {
        return iPrice;
    }

    public void setiPrice(Long iPrice) {
        this.iPrice = iPrice;
    }
}