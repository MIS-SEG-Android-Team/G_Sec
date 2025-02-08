package org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Upsert;
import androidx.sqlite.db.SimpleSQLiteQuery;
import java.util.List;

@Dao
public interface PatrolScheduleDao {

    @Upsert
    void save(List<PatrolScheduleEntity> value);

    @Query("SELECT * FROM Patrol_Schedule WHERE cRequestxx IN ('0', '1') ORDER BY dTimexxxx ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

    @Query("SELECT dTimexxxx FROM Patrol_Schedule WHERE dTimexxxx < :schedule ORDER BY dTimexxxx DESC LIMIT 1")
    String getRecentSchedule(String schedule);

    @Query("SELECT a.sNFCIDxxx, a.schedIDxx, b.dTimexxxx FROM Patrol_Route a, Patrol_Schedule b " +
            "WHERE a.schedIDxx = b.schedIDxx " +
            "AND b.dTimexxxx >= :dTimex " +
            "AND b.cRequestxx IN ('0', '1') " +
            "ORDER BY b.dTimexxxx ASC LIMIT 1")
    CacheSchedule getNextCacheSchedule(String dTimex);

    @Query("SELECT b.dTimexxxx FROM patrol_route a, patrol_schedule b " +
            "WHERE a.schedIDxx = b.schedIDxx " +
            "AND a.sNFCIDxxx = :nfcIDxx " +
            "AND b.dTimexxxx <= :currentime " +
            "AND b.cRequestxx IN ('0', '1') " +
            "group by  a.schedIDxx, b.dTimexxxx " +
            "order by b.dTimexxxx DESC LIMIT 1")
    String getLastNFCSchedule(String nfcIDxx, String currentime);

    @Query("SELECT sWHouseNm FROM Warehouse WHERE sWHouseID = :wHouseID")
    String getWarehouseName(String wHouseID);

    @Query("SELECT cRequestxx FROM Patrol_Schedule WHERE dTimexxxx = :schedule")
    String getCRequest(String schedule);

    @Query("SELECT COUNT(*) FROM Request_Visit WHERE dSchedule = :schedule ")
    int isRequestVisit(String schedule);

    @Query("DELETE FROM Patrol_Schedule")
    void clearPatrolSchedule();

    class CacheSchedule{
        public String sNFCIDxxx;
        public String schedIDxx;
        public String dTimexxxx;

        public String getsNFCIDxxx() {
            return sNFCIDxxx;
        }

        public String getSchedIDxx() {
            return schedIDxx;
        }

        public String getdTimexxxx() {
            return dTimexxxx;
        }
    }

}
