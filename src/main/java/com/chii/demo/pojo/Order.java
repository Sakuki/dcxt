package com.chii.demo.pojo;

import java.util.Date;

public class Order {
    private Date oDate;

    private String oId;

    private Long oAllprice;

    private String oData;

    public Order(Date oDate, String oId, Long oAllprice, String oData) {
        this.oDate = oDate;
        this.oId = oId;
        this.oAllprice = oAllprice;
        this.oData = oData;
    }

    public Order() {
        super();
    }

    public Date getoDate() {
        return oDate;
    }

    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId == null ? null : oId.trim();
    }

    public Long getoAllprice() {
        return oAllprice;
    }

    public void setoAllprice(Long oAllprice) {
        this.oAllprice = oAllprice;
    }

    public String getoData() {
        return oData;
    }

    public void setoData(String oData) {
        this.oData = oData == null ? null : oData.trim();
    }
}