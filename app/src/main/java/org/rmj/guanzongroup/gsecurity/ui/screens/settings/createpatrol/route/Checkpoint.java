package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

public class Checkpoint {

    private final String sNFCIDxxx;
    private final String sDescript;
    private final String sCatIDxxx;
    private final String sWHouseID;
    private Boolean cSelected = false;


    public Checkpoint(String sNFCIDxxx, String sDescript, String sCatIDxxx, String sWHouseID) {
        this.sNFCIDxxx = sNFCIDxxx;
        this.sDescript = sDescript;
        this.sCatIDxxx = sCatIDxxx;
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

    public String getsWHouseID() {
        return sWHouseID;
    }

    public Boolean isSelected() {
        return cSelected;
    }

    public void hasSelected(Boolean cAddedxxx) {
        this.cSelected = cAddedxxx;
    }
}
