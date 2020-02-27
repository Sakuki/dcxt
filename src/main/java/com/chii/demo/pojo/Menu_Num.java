package com.chii.demo.pojo;

public class Menu_Num {
    private String mId;

    private String mName;

    private Long mPrice;

    private String kId;

    private int Num;

    public Menu_Num(String mId, String mName, Long mPrice, String kId, int Num) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
        this.kId = kId;
        this.Num= Num;
    }

    public Menu_Num() {
        super();
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId == null ? null : mId.trim();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName == null ? null : mName.trim();
    }

    public Long getmPrice() {
        return mPrice;
    }

    public void setmPrice(Long mPrice) {
        this.mPrice = mPrice;
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId == null ? null : kId.trim();
    }

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer Num) {
        this.Num = Num;
    }
}
