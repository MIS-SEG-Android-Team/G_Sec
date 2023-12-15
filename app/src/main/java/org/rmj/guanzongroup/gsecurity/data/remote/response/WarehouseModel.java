package org.rmj.guanzongroup.gsecurity.data.remote.response;

public class WarehouseModel {
    private String sBranchNm;
    private String sWHouseID;
    private String sBranchCd;
    private String dTimeStmp;
    private String sWHouseNm;
    private String cRecdStat;

    public String getSBranchNm() { return sBranchNm; }
    public void setSBranchNm(String value) { this.sBranchNm = value; }

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getSBranchCd() { return sBranchCd; }
    public void setSBranchCd(String value) { this.sBranchCd = value; }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSWHouseNm() { return sWHouseNm; }
    public void setSWHouseNm(String value) { this.sWHouseNm = value; }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }
}
