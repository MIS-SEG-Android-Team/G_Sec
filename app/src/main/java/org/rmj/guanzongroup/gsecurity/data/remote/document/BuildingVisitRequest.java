package org.rmj.guanzongroup.gsecurity.data.remote.document;

public class BuildingVisitRequest {

    private String sRquestID = "";

    private String sNFCIDxxx = "";
    private String sUserIDxx = "";
    private String sModified = "";
    private String dModified = "";
    private String dSchedule = "";

    public BuildingVisitRequest() {

    }

    public String getsRquestID() {
        return sRquestID;
    }

    public void setsRquestID(String sRquestID) {
        this.sRquestID = sRquestID;
    }

    public String getsNFCIDxxx() {
        return sNFCIDxxx;
    }

    public void setsNFCIDxxx(String sNFCIDxxx) {
        this.sNFCIDxxx = sNFCIDxxx;
    }

    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public void setsUserIDxx(String sUserIDxx) {
        this.sUserIDxx = sUserIDxx;
    }

    public String getsModified() {
        return sModified;
    }

    public void setsModified(String sModified) {
        this.sModified = sModified;
    }

    public String getdModified() {
        return dModified;
    }

    public void setdModified(String dModified) {
        this.dModified = dModified;
    }

    public String getdSchedule() {
        return dSchedule;
    }

    public void setdSchedule(String dSchedule) {
        this.dSchedule = dSchedule;
    }
}
