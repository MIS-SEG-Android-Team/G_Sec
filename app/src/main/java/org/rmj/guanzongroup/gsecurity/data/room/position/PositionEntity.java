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
    @ColumnInfo(name = "sPostnNme")
    protected String sPostnNme;
    @ColumnInfo(name = "dModified")
    protected String dModified;
    @ColumnInfo(name = "dCreatedx")
    protected String dCreatedx;

    @NonNull
    public String getsPositnID() { return sPositnID; }
    public void setsPositnID(@NonNull String sPositnID) { this.sPositnID = sPositnID; }

    public String getDCreatedx() { return dCreatedx; }
    public void setDCreatedx(String value) { this.dCreatedx = value; }

    public String getDModified() { return dModified; }
    public void setDModified(String value) { this.dModified = value; }

    public String getSPostnNme() { return sPostnNme; }
    public void setSPostnNme(String value) { this.sPostnNme = value; }
}
