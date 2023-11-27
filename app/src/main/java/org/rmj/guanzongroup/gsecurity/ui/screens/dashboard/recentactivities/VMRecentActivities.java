package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.recentactivities;

import static org.rmj.guanzongroup.gsecurity.mockdata.ListRecentActivity.getPersonnelRecentActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.pojo.activity.RecentActivity;

import java.util.List;

public class VMRecentActivities extends AndroidViewModel {

    public VMRecentActivities(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<RecentActivity>> getRecentActivity(){
        return getPersonnelRecentActivity();
    }
}