package org.rmj.guanzongroup.gsecurity.data.room.position;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Position")
public class PositionEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sPositnID")
    protected String sPositnID;
    @ColumnInfo(name = "sPositnNm")
    protected String sPositnNm;
    @ColumnInfo(name = "cRecdStat")
    protected String cRecdStat;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;

    @NonNull
    public String getsPositnID() { return sPositnID; }
    public void setsPositnID(@NonNull String sPositnID) { this.sPositnID = sPositnID; }

    public String getcRecdStat() {
        return cRecdStat;
    }

    public void setcRecdStat(String cRecdStat) {
        this.cRecdStat = cRecdStat;
    }

    public String getSPositnNm() { return sPositnNm; }
    public void setSPositnNm(String value) { this.sPositnNm = value; }

    public String getdTimeStmp() {
        return dTimeStmp;
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }
}
