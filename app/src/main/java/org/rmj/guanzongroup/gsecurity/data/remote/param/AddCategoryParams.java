package org.rmj.guanzongroup.gsecurity.data.remote.param;

//  Parameter Structure upon ApiService...
//  {
//    "sCategory": "Patrol",
//    "sDescript": "Security patrol area"
//  }
public class AddCategoryParams {
    private String sDescript;
    private String sCategory;

    public String getSDescript() { return sDescript; }
    public void setSDescript(String value) { this.sDescript = value; }

    public String getSCategory() { return sCategory; }
    public void setSCategory(String value) { this.sCategory = value; }
}
