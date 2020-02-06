package com.chii.demo.pojo;

import java.util.Date;

public class Order {
    private String oId;

    private String oUser;

    private String oDesk;

    private Date oDate;

    private String oData;

    private Integer oNumber;

    private String oTotalprice;

    private String oFlag;

    public Order(String oId, String oUser, String oDesk, Date oDate, String oData, Integer oNumber, String oTotalprice, String oFlag) {
        this.oId = oId;
        this.oUser = oUser;
        this.oDesk = oDesk;
        this.oDate = oDate;
        this.oData = oData;
        this.oNumber = oNumber;
		this.oTotalprice = oTotalprice;
		this.oFlag = oFlag;
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
	
	public String getoTotalprice(){
		return oTotalprice;
	}
	
	public void setoTotalprice(String oTotalprice){
		this.oTotalprice = oTotalprice;
	}
	
	public String getoFlag(){
		return oFlag;
	}
	
	public void setoFlag(String oFlag){
		this.oFlag = oFlag;
	}
}