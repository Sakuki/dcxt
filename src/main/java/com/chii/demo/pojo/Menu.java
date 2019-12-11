package com.chii.demo.pojo;

public class Menu {
    private String mId;

    private String mName;

    private String mWay;

    private String mFlavor;

    private String mIng;

    private Long mPrice;

    private String mPic;

    private String kId;

    public Menu(String mId, String mName, String mWay, String mFlavor, String mIng, Long mPrice, String mPic, String kId) {
        this.mId = mId;
        this.mName = mName;
        this.mWay = mWay;
        this.mFlavor = mFlavor;
        this.mIng = mIng;
        this.mPrice = mPrice;
        this.mPic = mPic;
        this.kId = kId;
    }

    public Menu() {
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

    public String getmWay() {
        return mWay;
    }

    public void setmWay(String mWay) {
        this.mWay = mWay == null ? null : mWay.trim();
    }

    public String getmFlavor() {
        return mFlavor;
    }

    public void setmFlavor(String mFlavor) {
        this.mFlavor = mFlavor == null ? null : mFlavor.trim();
    }

    public String getmIng() {
        return mIng;
    }

    public void setmIng(String mIng) {
        this.mIng = mIng == null ? null : mIng.trim();
    }

    public Long getmPrice() {
        return mPrice;
    }

    public void setmPrice(Long mPrice) {
        this.mPrice = mPrice;
    }

    public String getmPic() {
        return mPic;
    }

    public void setmPic(String mPic) {
        this.mPic = mPic == null ? null : mPic.trim();
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId == null ? null : kId.trim();
    }
}