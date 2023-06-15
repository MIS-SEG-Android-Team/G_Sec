package org.rmj.guanzongroup.gsecurity.Etc;

public class VisitLists {
    String timeevisit;
    String placevisit;
    String timearrived;

    public VisitLists(String timeevisit, String placevisit, String timearrived){
        this.timeevisit = timeevisit;
        this.placevisit = placevisit;
        this.timearrived = timearrived;
    }

    public String getTimeevisit() {
        return timeevisit;
    }
    public String getPlacevisit(){
        return placevisit;
    }

    public String getTimearrived() {
        return timearrived;
    }
}
