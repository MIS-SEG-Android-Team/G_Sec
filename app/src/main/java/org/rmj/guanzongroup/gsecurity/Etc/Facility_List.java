package org.rmj.guanzongroup.gsecurity.Etc;

public class Facility_List {
    private String facilityname;
    private String recentvisitor;
    private String timesvisited;

    public Facility_List(String facilityname, String recentvisitor, String timesvisited){
        this.facilityname = facilityname;
        this.recentvisitor = recentvisitor;
        this.timesvisited = timesvisited;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public String getRecentvisitor() {
        return recentvisitor;
    }

    public String getTimesvisited() {
        return timesvisited;
    }
}
