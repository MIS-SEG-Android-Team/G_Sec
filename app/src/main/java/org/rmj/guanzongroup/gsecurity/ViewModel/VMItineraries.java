package org.rmj.guanzongroup.gsecurity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import org.rmj.guanzongroup.gsecurity.Etc.VisitLists;

import java.util.ArrayList;
import java.util.List;

public class VMItineraries extends AndroidViewModel {

    public VMItineraries(@NonNull Application application) {
        super(application);
    }
    public List<VisitLists> getVisitLists(){
        List<VisitLists> list = new ArrayList<>();
        list.add(new VisitLists("8:00 PM", "Warehouse Front", "7:30 PM"));
        list.add(new VisitLists("9:00 PM", "Warehouse Back Storage", "9:15 PM"));
        list.add(new VisitLists("10:00 PM", "Warehouse Parking", "9:50 PM"));

        return list;
    }

    public void importVisitList(){}
}
