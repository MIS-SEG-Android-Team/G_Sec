package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.mockdata.ListPatrolRoute;
import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;

import java.util.List;

public class VMItineraries extends AndroidViewModel {

    public VMItineraries(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PatrolRoute>> getItineraryList() {
        return ListPatrolRoute.getPatrolRoute();
    }
}