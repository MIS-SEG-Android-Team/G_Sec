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

    @Query("UPDATE Patrol_Schedule SET cRequestxx = '2' WHERE dTimexxxx = :schedule AND cRequestxx = '1' " +
            "AND :nfcIDxx = (SELECT sNFCIDxxx FROM Patrol_Route WHERE schedIDxx = Patrol_Schedule.schedIDxx) ")
    void updateCRequest(String schedule, String nfcIDxx);

    @Query("SELECT * FROM Patrol_Schedule WHERE cRequestxx IN ('0', '1') ORDER BY dTimexxxx ASC")
    List<PatrolScheduleEntity> getPatrolScheduleList();

    @Query("SELECT dTimexxxx FROM Patrol_Schedule WHERE dTimexxxx < :schedule ORDER BY dTimexxxx DESC LIMIT 1")
    String getRecentSchedule(String schedule);

    @Query("SELECT a.sNFCIDxxx, b.dTimexxxx FROM Patrol_Route a, Patrol_Schedule b " +
            "WHERE a.schedIDxx = b.schedIDxx " +
            "AND b.dTimexxxx > :dTimex " +
            "ORDER BY b.dTimexxxx ASC LIMIT 1")
    CacheSchedule getNextCacheSchedule(String dTimex);

    @Query("SELECT sWHouseNm FROM Warehouse WHERE sWHouseID = :wHouseID")
    String getWarehouseName(String wHouseID);

    @Query("SELECT cRequestxx FROM Patrol_Schedule WHERE dTimexxxx = :schedule")
    String getCRequest(String schedule);

    @Query("DELETE FROM Patrol_Schedule")
    void clearPatrolSchedule();

    class CacheSchedule{
        public String sNFCIDxxx;
        public String dTimexxxx;

        public String getsNFCIDxxx() {
            return sNFCIDxxx;
        }

        public String getdTimexxxx() {
            return dTimexxxx;
        }
    }

}
