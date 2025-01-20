package org.rmj.guanzongroup.gsecurity.data.room.request;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RequestVisitDao {

    @Insert
    void save(RequestVisitEntity value);

    @Update
    void update(RequestVisitEntity value);

    @Query("SELECT a.*, b.sDescript FROM Request_Visit a, Patrol_Route b " +
            "WHERE a.sNFCIDxxx = b.sNFCIDxxx " +
            "AND a.sNFCIDxxx not in (SELECT sNFCIDxxx FROM Patrol_Log WHERE sNFCIDxxx = a.sNFCIDxxx and dSchedule = a.dSchedule and cRequestd = '1') " +
            "AND a.dSchedule < :currentDate " +
            "GROUP BY a.sNFCIDxxx " +
            "ORDER BY dSchedule DESC " +
            "LIMIT 1")
    LiveData<RequestSchedule> getRequestedVisit(String currentDate);

    class RequestSchedule{
        String sRquestID;
        String sUserIDxx;
        String sWhouseID;
        String sNFCIDxxx;
        String dSchedule;
        String sRemarksx;
        String sDescript;

        public String getsRquestID() {
            return sRquestID;
        }

        public void setsRquestID(String sRquestID) {
            this.sRquestID = sRquestID;
        }

        public String getsUserIDxx() {
            return sUserIDxx;
        }

        public void setsUserIDxx(String sUserIDxx) {
            this.sUserIDxx = sUserIDxx;
        }

        public String getsWhouseID() {
            return sWhouseID;
        }

        public void setsWhouseID(String sWhouseID) {
            this.sWhouseID = sWhouseID;
        }

        public String getsNFCIDxxx() {
            return sNFCIDxxx;
        }

        public void setsNFCIDxxx(String sNFCIDxxx) {
            this.sNFCIDxxx = sNFCIDxxx;
        }

        public String getdSchedule() {
            return dSchedule;
        }

        public void setdSchedule(String dSchedule) {
            this.dSchedule = dSchedule;
        }

        public String getsRemarksx() {
            return sRemarksx;
        }

        public void setsRemarksx(String sRemarksx) {
            this.sRemarksx = sRemarksx;
        }

        public String getsDescript() {
            return sDescript;
        }

        public void setsDescript(String sDescript) {
            this.sDescript = sDescript;
        }
    }
}
