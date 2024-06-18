package org.rmj.guanzongroup.gsecurity.data.remote.param;

public class GetBranchParams {
    private Boolean bsearch;
    private String descript;
    private String timestamp;

    public Boolean getBsearch() { return bsearch; }
    public void setBsearch(Boolean value) { this.bsearch = value; }

    public String getDescript() { return descript; }
    public void setDescript(String value) { this.descript = value; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String value) { this.timestamp = value; }
}
