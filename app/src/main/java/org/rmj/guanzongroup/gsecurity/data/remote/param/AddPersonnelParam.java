package org.rmj.guanzongroup.gsecurity.data.remote.param;

/**
 * PersonnelParam => use as parameter to
 * forward the information of new added personnel...
 */
public class AddPersonnelParam {

    private String sLastName;
    private String sFrstName;
    private String sMiddName;
    private String sPositnID;
    private String sPositnDs;
    private String nUserLvel;

    /**
     * Parameter structure upon ApiService...
     * {
     *   "sLastName": "Garcia",
     *   "sFrstName": "Christian",
     *   "sMiddName": "Aquino",
     *   "sPositnID": "POSITION01",
     *   "sPositnDs": "Security Officer",
     *   "nUserLvel": "0"
     * }
     */
    public AddPersonnelParam() {
    }

    public String getsLastName() {
        return sLastName;
    }

    public void setsLastName(String sLastName) {
        this.sLastName = sLastName;
    }

    public String getsFrstName() {
        return sFrstName;
    }

    public void setsFrstName(String sFrstName) {
        this.sFrstName = sFrstName;
    }

    public String getsMiddName() {
        return sMiddName;
    }

    public void setsMiddName(String sMiddName) {
        this.sMiddName = sMiddName;
    }

    public String getsPositnID() {
        return sPositnID;
    }

    public void setsPositnID(String sPositnID) {
        this.sPositnID = sPositnID;
    }

    public String getsPositnDs() {
        return sPositnDs;
    }

    public void setsPositnDs(String sPositnDs) {
        this.sPositnDs = sPositnDs;
    }

    public String getnUserLvel() {
        return nUserLvel;
    }

    public void setnUserLvel(String nUserLvel) {
        this.nUserLvel = nUserLvel;
    }
}
