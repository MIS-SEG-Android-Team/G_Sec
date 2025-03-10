package org.rmj.guanzongroup.gsecurity.data.room.user_log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

@Dao
public interface UserLogDao {

    @Upsert
    void saveUserLog(EUserLog value);

    @Query("DELETE FROM User_Log")
    void clearUserLog();

    @Query("SELECT sLogDatexx FROM User_Log WHERE sUserIDxx == :userIDxx")
    LiveData<String> getLastLog(String userIDxx);
}
