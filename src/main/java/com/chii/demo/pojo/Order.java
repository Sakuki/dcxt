package com.chii.demo.pojo;

import java.util.Date;

public class Order {
    private String oId;

    private String oUser;

    private String oDesk;

    private Date oDate;

    private String oData;

    private Integer oNumber;

    public Order(String oId, String oUser, String oDesk, Date oDate, String oData, Integer oNumber) {
        this.oId = oId;
        this.oUser = oUser;
        this.oDesk = oDesk;
        this.oDate = oDate;
        this.oData = oData;
        this.oNumber = oNumber;
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

    public String getoUser() {
        return oUser;
    }

    public void setoUser(String oUser) {
        this.oUser = oUser == null ? null : oUser.trim();
    }

    public String getoDesk() {
        return oDesk;
    }

    public void setoDesk(String oDesk) {
        this.oDesk = oDesk == null ? null : oDesk.trim();
    }

    public Date getoDate() {
        return oDate;
    }

    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    public String getoData() {
        return oData;
    }

    public void setoData(String oData) {
        this.oData = oData == null ? null : oData.trim();
    }

    public Integer getoNumber() {
        return oNumber;
    }

    public void setoNumber(Integer oNumber) {
        this.oNumber = oNumber;
    }
}