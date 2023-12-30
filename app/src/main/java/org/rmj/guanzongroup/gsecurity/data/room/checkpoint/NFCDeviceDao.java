package org.rmj.guanzongroup.gsecurity.data.room.checkpoint;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface NFCDeviceDao {

    @Upsert
    void saveCheckpoints(List<NFCDeviceEntity> value);

    @Query("SELECT dTimeStmp FROM NFC_Device ORDER BY dTimeStmp DESC LIMIT 1")
    String getLatestTimeStamp();
}
