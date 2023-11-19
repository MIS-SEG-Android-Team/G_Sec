package org.rmj.guanzongroup.gsecurity.data.remote.document;

public class Warehouse {

    private String sWHouseID = "";

    private String sWHouseNm = "";
    private String sBranchCd = "";
    private String cRecdStat = "";
    private String dLastChck = "";
    private String dModified = "";
    private String dTimeStmp = "";

    public Warehouse() {

    }

    public String getsWHouseID() {
        return sWHouseID;
    }

    public void setsWHouseID(String sWHouseID) {
        this.sWHouseID = sWHouseID;
    }

    public String getsWHouseNm() {
        return sWHouseNm;
    }

    public void setsWHouseNm(String sWHouseNm) {
        this.sWHouseNm = sWHouseNm;
    }

    public String getsBranchCd() {
        return sBranchCd;
    }

    public void setsBranchCd(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getcRecdStat() {
        return cRecdStat;
    }

    public void setcRecdStat(String cRecdStat) {
        this.cRecdStat = cRecdStat;
    }

    public String getdLastChck() {
        return dLastChck;
    }

    public void setdLastChck(String dLastChck) {
        this.dLastChck = dLastChck;
    }

    public String getdModified() {
        return dModified;
    }

    public void setdModified(String dModified) {
        this.dModified = dModified;
    }

    public String getdTimeStmp() {
        return dTimeStmp;
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }
}
