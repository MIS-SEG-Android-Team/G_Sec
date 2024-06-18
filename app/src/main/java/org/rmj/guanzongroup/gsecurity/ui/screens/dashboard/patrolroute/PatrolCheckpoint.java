package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

public class PatrolCheckpoint {

    private final String sNFCIDxxx;
    private final String nPatrolNo;
    private final String sDescript;
    private Boolean isVisited = false;

    public PatrolCheckpoint(String sNFCIDxxx, String nPatrolNo, String sDescript) {
        this.sNFCIDxxx = sNFCIDxxx;
        this.nPatrolNo = nPatrolNo;
        this.sDescript = sDescript;
    }

    public String getsNFCIDxxx() {
        return sNFCIDxxx;
    }

    public String getnPatrolNo() {
        return nPatrolNo;
    }

    public String getsDescript() {
        return sDescript;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

    public Boolean isVisited() {
        return isVisited;
    }
}
