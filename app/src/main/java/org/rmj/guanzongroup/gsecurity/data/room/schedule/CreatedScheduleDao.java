package org.rmj.guanzongroup.gsecurity.data.room.schedule;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CreatedScheduleDao {

    @Insert
    void createNew(ScheduleEntity value);

    @Query("SELECT * FROM Created_Schedule ORDER BY id DESC LIMIT 1")
    LiveData<ScheduleEntity> getCreatedSchedule();

    @Update
    void updateSchedule(ScheduleEntity value);

    @Query("DELETE FROM Created_Schedule")
    void clearCache();
}
