package org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport;

public class PersonnelPatrolReport {
    private String sNFCIDxxx;
    private String sDescript;
    private String sOfficerx;
    private String sRemarksx;
    private String cRequestd;
    private String sUserIDxx;
    private String dSchedule;
    private String dVisitedx;

    public String getSNFCIDxxx() { return sNFCIDxxx; }
    public void setSNFCIDxxx(String value) { this.sNFCIDxxx = value; }

    public String getSRemarksx() { return sRemarksx; }
    public void setSRemarksx(String value) { this.sRemarksx = value; }

    public String getCRequestd() { return cRequestd; }
    public void setCRequestd(String value) { this.cRequestd = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public String getDVisitedx() { return dVisitedx; }
    public void setDVisitedx(String value) { this.dVisitedx = value; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String sDescript) { this.sDescript = sDescript; }

    public String getDSchedule() { return dSchedule; }
    public void setDSchedule(String dSchedule) { this.dSchedule = dSchedule; }

    public String getSOfficerx() { return sOfficerx; }
    public void setSOfficerx(String sOfficerx) { this.sOfficerx = sOfficerx; }
}
