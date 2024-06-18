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

    @Query("SELECT * FROM Request_Visit WHERE dVisitedx IS NULL ORDER BY dTimexxxx DESC")
    LiveData<RequestVisitEntity> getRequestedVisit();
}
