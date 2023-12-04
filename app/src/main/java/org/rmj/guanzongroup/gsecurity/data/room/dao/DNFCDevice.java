package org.rmj.guanzongroup.gsecurity.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.guanzongroup.gsecurity.data.repository.nfc.NFCDevice;
import org.rmj.guanzongroup.gsecurity.data.room.entities.ENFCDevice;

import java.util.List;

@Dao
public interface DNFCDevice {

    @Insert
    void insert(ENFCDevice nfcDevice);

    @Update
    void update(ENFCDevice nfcDevice);

    @Query("SELECT * FROM NFC_Device")
    LiveData<List<NFCDevice>> getNFCDevices();
}
