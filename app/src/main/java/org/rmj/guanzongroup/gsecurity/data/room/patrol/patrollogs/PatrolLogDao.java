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
}
