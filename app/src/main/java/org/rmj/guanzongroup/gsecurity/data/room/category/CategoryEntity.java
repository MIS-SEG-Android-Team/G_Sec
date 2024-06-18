package org.rmj.guanzongroup.gsecurity.data.room.category;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Category")
public class CategoryEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sCatIDxxx")
    protected String sCatIDxxx;
    @ColumnInfo(name = "sCategory")
    protected String sCategory;
    @ColumnInfo(name = "sDescript")
    protected String sDescript;
    @ColumnInfo(name = "dTimeStmp")
    protected String dTimeStmp;

    public CategoryEntity() {
    }

    @NonNull
    public String getsCatIDxxx() {
        return sCatIDxxx;
    }

    public void setsCatIDxxx(@NonNull String sCatIDxxx) {
        this.sCatIDxxx = sCatIDxxx;
    }

    public String getDTimeStmp() { return dTimeStmp; }
    public void setDTimeStmp(String value) { this.dTimeStmp = value; }

    public String getSDescript() { return sDescript; }
    public void setSDescript(String value) { this.sDescript = value; }

    public String getSCategory() { return sCategory; }
    public void setSCategory(String value) { this.sCategory = value; }
}
