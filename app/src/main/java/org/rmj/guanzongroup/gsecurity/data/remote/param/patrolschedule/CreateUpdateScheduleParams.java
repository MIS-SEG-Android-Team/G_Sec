package org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule;
import java.util.List;

public class CreateUpdateScheduleParams {
    private String sWHouseID;
    private String sWHouseNm;
    private List<SSchedule> sSchedule;
    private String sUserIDxx;
    private String sUserName;
    private List<SRoutexxx> sRoutexxx;
    private String sNotesxxx;

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getSWHouseNm() { return sWHouseNm; }
    public void setSWHouseNm(String value) { this.sWHouseNm = value; }

    public List<SSchedule> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<SSchedule> value) { this.sSchedule = value; }

    public String getSUserIDxx() { return sUserIDxx; }
    public void setSUserIDxx(String value) { this.sUserIDxx = value; }

    public String getSUserName() { return sUserName; }
    public void setSUserName(String value) { this.sUserName = value; }

    public List<SRoutexxx> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<SRoutexxx> value) { this.sRoutexxx = value; }

    public String getSNotesxxx() { return sNotesxxx; }
    public void setSNotesxxx(String value) { this.sNotesxxx = value; }
}

