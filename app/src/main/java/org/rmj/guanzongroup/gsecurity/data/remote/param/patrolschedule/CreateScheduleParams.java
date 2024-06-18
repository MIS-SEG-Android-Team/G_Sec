package org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule;

import java.util.List;

public class CreateScheduleParams {
    private String sWHouseID;
    private String sWHouseNm;
    private List<PersonnelPatrolSchedule> sSchedule;
    private String sUserIDxx;
    private String sUserName;
    private List<PersonnelPatrolRoute> sRoutexxx;
    private String sNotesxxx;

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getSWHouseNm() { return sWHouseNm; }
    public void setSWHouseNm(String value) { this.sWHouseNm = value; }

    public List<PersonnelPatrolSchedule> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<PersonnelPatrolSchedule> value) { this.sSchedule = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public String getSUserName() { return sUserName; }
    public void setSUserName(String value) { this.sUserName = value; }

    public List<PersonnelPatrolRoute> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<PersonnelPatrolRoute> value) { this.sRoutexxx = value; }

    public String getSNotesxxx() { return sNotesxxx; }
    public void setSNotesxxx(String value) { this.sNotesxxx = value; }
}

