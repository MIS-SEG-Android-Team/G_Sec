package org.rmj.guanzongroup.gsecurity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.Etc.Facility_List;
import org.rmj.guanzongroup.gsecurity.Etc.VisitLists;

import java.util.ArrayList;
import java.util.List;

public class VMFacilities extends AndroidViewModel {

    public VMFacilities(@NonNull Application application) {
        super(application);
    }

    public List<Facility_List> getFacilityLists(){
        List<Facility_List> list = new ArrayList<>();
        list.add(new Facility_List("Warehouse Front", "Last Visited By Guillier Gutoman", "1 time visited"));
        list.add(new Facility_List("Warehouse Back Storage","Last Visited By Guillier Gutoman", "2 times visited"));
        list.add(new Facility_List("Warehouse Parking","Last Visited By Guillier Gutoman", "3 times visited"));

        return list;
    }
}
