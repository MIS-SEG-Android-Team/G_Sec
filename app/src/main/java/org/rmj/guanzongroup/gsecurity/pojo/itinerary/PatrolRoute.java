package org.rmj.guanzongroup.gsecurity.pojo.itinerary;

public class PatrolRoute {

    private String NfcID;
    private String Description;
    private String Category;
    private String Warehouse;
    private String patrolType;
    private boolean visited = false;

    public PatrolRoute() {

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(String warehouse) {
        Warehouse = warehouse;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(String patrolType) {
        this.patrolType = patrolType;
    }

    public String getNfcID() {
        return NfcID;
    }

    public void setNfcID(String nfcID) {
        NfcID = nfcID;
    }
}
