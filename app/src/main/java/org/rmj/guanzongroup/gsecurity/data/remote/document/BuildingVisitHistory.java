package org.rmj.guanzongroup.gsecurity.data.remote.document;

public class BuildingVisitHistory {

    private String dTimeStmp = "";
    private String sNFCIDxxx = "";
    private String sUserIDxx = "";
    private String sRemarksx = "";

    public BuildingVisitHistory(String dTimeStmp, String sNFCIDxxx, String sUserIDxx, String sRemarksx) {
        this.dTimeStmp = dTimeStmp;
        this.sNFCIDxxx = sNFCIDxxx;
        this.sUserIDxx = sUserIDxx;
        this.sRemarksx = sRemarksx;
    }

    public String getTimeStmp() {
        return dTimeStmp;
    }

    public String getNFCIDxxx() {
        return sNFCIDxxx;
    }

    public String getUserIDxx() {
        return sUserIDxx;
    }

    public String getRemarksx() {
        return sRemarksx;
    }
}
