package com.chii.demo.pojo;

import java.util.Date;

public class Order {
    private String oId;

    private Date oDate;

    private Long oAllprice;

    public Order(String oId, Date oDate, Long oAllprice) {
        this.oId = oId;
        this.oDate = oDate;
        this.oAllprice = oAllprice;
    }

    public Order() {
        super();
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId == null ? null : oId.trim();
    }

    public Date getoDate() {
        return oDate;
    }

    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    public Long getoAllprice() {
        return oAllprice;
    }

    public void setoAllprice(Long oAllprice) {
        this.oAllprice = oAllprice;
    }
}