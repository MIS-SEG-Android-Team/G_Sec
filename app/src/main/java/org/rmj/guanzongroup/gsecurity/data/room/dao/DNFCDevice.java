package org.rmj.guanzongroup.gsecurity.data.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.guanzongroup.gsecurity.data.room.entities.ENFCDevice;

@Dao
public interface DNFCDevice {

    @Insert
    void insert(ENFCDevice nfcDevice);
}
