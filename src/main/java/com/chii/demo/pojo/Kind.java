package com.chii.demo.pojo;

public class Kind {
    private String kId;

    private String kName;

    private String kMain;

    public Kind(String kId, String kName, String kMain) {
        this.kId = kId;
        this.kName = kName;
        this.kMain = kMain;
    }

    public Kind() {
        super();
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId == null ? null : kId.trim();
    }

    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName == null ? null : kName.trim();
    }

    public String getkMain() {
        return kMain;
    }

    public void setkMain(String kMain) {
        this.kMain = kMain == null ? null : kMain.trim();
    }
}