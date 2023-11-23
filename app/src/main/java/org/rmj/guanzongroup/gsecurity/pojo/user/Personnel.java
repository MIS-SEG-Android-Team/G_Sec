package org.rmj.guanzongroup.gsecurity.pojo.user;

public class Personnel {

    private final String PersonnelID;
    private final String PersonnelName;
    private final String WareHouseDuty;
    private final String LastVisited;

    public Personnel(
            String personnelID,
            String personnelName,
            String wareHouseDuty,
            String lastVisited) {
        PersonnelID = personnelID;
        PersonnelName = personnelName;
        WareHouseDuty = wareHouseDuty;
        LastVisited = lastVisited;
    }

    public String getPersonnelID() {
        return PersonnelID;
    }

    public String getPersonnelName() {
        return PersonnelName;
    }

    public String getWareHouseDuty() {
        return WareHouseDuty;
    }

    public String getLastVisited() {
        return LastVisited;
    }

}
