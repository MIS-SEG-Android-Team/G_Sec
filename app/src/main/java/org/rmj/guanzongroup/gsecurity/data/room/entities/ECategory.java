package org.rmj.guanzongroup.gsecurity.data.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "NFC_Category")
public class ECategory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sCatIDxxx")
    private int CatIDxxx;
    @ColumnInfo(name = "sCategory", defaultValue = "")
    private String Category;
    @ColumnInfo(name = "sDescript", defaultValue = "")
    private String Descript;
    @ColumnInfo(name = "sCreatedx", defaultValue = "")
    private String Createdx;
    @ColumnInfo(name = "dModified", defaultValue = "CURRENT_TIMESTAMP")
    private Date Modified;

    public ECategory() {
    }

    public int getCatIDxxx() {
        return CatIDxxx;
    }

    public void setCatIDxxx(int catIDxxx) {
        CatIDxxx = catIDxxx;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public String getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(String createdx) {
        Createdx = createdx;
    }

    public Date getModified() {
        return Modified;
    }

    public void setModified(Date modified) {
        Modified = modified;
    }
}
