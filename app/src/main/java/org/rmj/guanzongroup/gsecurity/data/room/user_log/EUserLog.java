package org.rmj.guanzongroup.gsecurity.data.room.user_log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "User_Log", primaryKeys = {"sUserIDxx", "sLogDatexx"})
public class EUserLog {

    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    protected String sUserIDxx;

    @NonNull
    @ColumnInfo(name = "sLogDatexx")
    protected String sLogDatexx;

    @NonNull
    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public void setsUserIDxx(@NonNull String sUserIDxx) {
        this.sUserIDxx = sUserIDxx;
    }

    @NonNull
    public String getsLogDatexx() {
        return sLogDatexx;
    }

    public void setsLogDatexx(@NonNull String sLogDatexx) {
        this.sLogDatexx = sLogDatexx;
    }
}
