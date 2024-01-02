package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

public class SelectedCheckpoints {

    private String sNFCIDxxx;
    private String sDescript;
    private String sCatIDxxx;
    private String sPayloadx;
    private String sWHouseID;
    private Boolean cAddedxxx = false;

    public SelectedCheckpoints(String sNFCIDxxx, String sDescript, String sCatIDxxx, String sPayloadx, String sWHouseID) {
        this.sNFCIDxxx = sNFCIDxxx;
        this.sDescript = sDescript;
        this.sCatIDxxx = sCatIDxxx;
        this.sPayloadx = sPayloadx;
        this.sWHouseID = sWHouseID;
    }

    public String getsNFCIDxxx() {
        return sNFCIDxxx;
    }

    public String getsDescript() {
        return sDescript;
    }

    public String getsCatIDxxx() {
        return sCatIDxxx;
    }

    public String getsPayloadx() {
        return sPayloadx;
    }

    public String getsWHouseID() {
        return sWHouseID;
    }

    public Boolean isAdded() {
        return cAddedxxx;
    }

    public void hasAdded(Boolean cAddedxxx) {
        this.cAddedxxx = cAddedxxx;
    }
}
