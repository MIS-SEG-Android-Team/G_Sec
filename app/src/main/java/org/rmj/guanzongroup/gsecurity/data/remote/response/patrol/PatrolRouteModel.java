package org.rmj.guanzongroup.gsecurity.data.remote.response.patrol;

import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;

import java.util.ArrayList;
import java.util.List;

public class PatrolRouteModel {
    private String sSchedIDx;
    private String sWHouseID;
    private String sWHouseNm;
    private String sNotexxxx;
    private String cRequestx;
    private String cRecdStat;
    private List<PatrolScheduleEntity> sSchedule;
    private List<PatrolRouteEntity> sRoutexxx;

    public String getSSchedIDx() { return sSchedIDx; }
    public void setSSchedIDx(String value) { this.sSchedIDx = value; }

    public String getSWHouseID() { return sWHouseID; }
    public void setSWHouseID(String value) { this.sWHouseID = value; }

    public String getsWHouseNm(){
        return sWHouseNm;
    }
    public void setsWHouseNm(String value){this.sWHouseNm = value;}

    public String getSNotexxxx() { return sNotexxxx; }
    public void setSNotexxxx(String value) { this.sNotexxxx = value; }

    public String getcRequestx(){
        return cRequestx;
    }
    public void setcRequestx(String value){
        this.cRequestx = value;
    }

    public String getCRecdStat() { return cRecdStat; }
    public void setCRecdStat(String value) { this.cRecdStat = value; }

    public List<PatrolScheduleEntity> getSSchedule() { return sSchedule; }
    public void setSSchedule(List<PatrolScheduleEntity> value) {
        this.sSchedule = value;
    }

    public List<PatrolRouteEntity> getSRoutexxx() { return sRoutexxx; }
    public void setSRoutexxx(List<PatrolRouteEntity> value) { this.sRoutexxx = value; }
}
