package org.rmj.guanzongroup.gsecurity.pojo.activity;

public class RecentActivity {

    private final String specificNFC;
    private final String assignedWarehouse;
    private final String lastTimeVisit;

    public RecentActivity(String specificNFC, String assignedWarehouse, String lastTimeVisit) {
        this.specificNFC = specificNFC;
        this.assignedWarehouse = assignedWarehouse;
        this.lastTimeVisit = lastTimeVisit;
    }

    public String getSpecificNFC() {
        return specificNFC;
    }

    public String getAssignedWarehouse() {
        return assignedWarehouse;
    }

    public String getLastTimeVisit() {
        return lastTimeVisit;
    }
}
