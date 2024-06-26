package org.rmj.guanzongroup.gsecurity.data.room.patrol.patrollogs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatrolLogDao {

    @Insert
    void save(PatrolLogEntity value);

    @Update
    void update(List<PatrolLogEntity> value);

    @Query("SELECT * FROM Patrol_Log ORDER BY dVisitedx DESC")
    LiveData<List<PatrolLogEntity>> getPatrolLogs();

    @Query("SELECT * FROM Patrol_Log WHERE dSchedule =:nSchedule AND sNFCIDxxx =:sNFCIDxxx AND dVisitedx LIKE '%'||:date||'%'")
    PatrolLogEntity getPatrolLog(String nSchedule, String sNFCIDxxx, String date);

    @Query("SELECT * FROM Patrol_Log WHERE cSendStat <> '1'")
    List<PatrolLogEntity> getPatrolLogsForPosting();

    @Query("SELECT * FROM Patrol_Log WHERE sNFCIDxxx=:sNFCIDxxx AND dSchedule=:dSchedule")
    PatrolLogEntity checkIfCheckpointIsVisited(String sNFCIDxxx, String dSchedule);

    @Query("SELECT * FROM Patrol_Log WHERE dSchedule=:dSchedule")
    List<PatrolLogEntity> checkIfHasPatrolForSchedule(String dSchedule);

    @Query("SELECT CASE " +
            "WHEN COUNT(*) = 0 THEN '0' " +
            "ELSE '1' " +
            "END AS result " +
            "FROM Patrol_Route a " +
            "LEFT JOIN Patrol_Log b ON a.sNFCIDxxx = b.sNFCIDxxx " +
            "WHERE b.dSchedule =:dSchedule " +
            "AND b.sNFCIDxxx IS NOT NULL")
    int checkIfPatrolFinished(String dSchedule);
}
