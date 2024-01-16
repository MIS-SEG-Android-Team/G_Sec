package org.rmj.guanzongroup.gsecurity.data.remote.response.patrol;

import java.util.List;

public class PatrolRouteModel {
    private String sNotexxxx;
    private List<SSchedule> sSchedule;
    private List<SRoutexxx> sRoutexxx;
    private String cRecdStat;

    public String getSNotexxxx() {
        return sNotexxxx;
    }

    public void setSNotexxxx(String value) {
        this.sNotexxxx = value;
    }

    public List<SSchedule> getSSchedule() {
        return sSchedule;
    }

    public void setSSchedule(List<SSchedule> value) {
        this.sSchedule = value;
    }

    public List<SRoutexxx> getSRoutexxx() {
        return sRoutexxx;
    }

    public void setSRoutexxx(List<SRoutexxx> value) {
        this.sRoutexxx = value;
    }

    public String getCRecdStat() {
        return cRecdStat;
    }

    public void setCRecdStat(String value) {
        this.cRecdStat = value;
    }
}
