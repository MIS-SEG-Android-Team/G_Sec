package org.rmj.guanzongroup.gsecurity.mockdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.gsecurity.pojo.activity.RecentActivity;

import java.util.ArrayList;
import java.util.List;

public class ListRecentActivity {

    public static LiveData<List<RecentActivity>> getPersonnelRecentActivity() {
        MutableLiveData<List<RecentActivity>> recentActivityList = new MutableLiveData<>();

        List<RecentActivity> list = new ArrayList<>();
        list.add(new RecentActivity(
                "Warehouse 2 Back Door",
                "Warehouse Anolid",
                "2:25 AM"));
        list.add(new RecentActivity(
                "Warehouse 2 Barracks",
                "Warehouse Anolid",
                "2:45 AM"));
        list.add(new RecentActivity(
                "Warehouse 2 BasketBall Court",
                "Warehouse Anolid",
                "2:55 AM"));
        list.add(new RecentActivity(
                "Warehouse 1 Stock Room",
                "Warehouse Anolid",
                "3:10 AM"));
        list.add(new RecentActivity(
                "Warehouse 1 Back Door",
                "Warehouse Anolid",
                "3:15 AM"));
        list.add(new RecentActivity(
                "Warehouse 1 Pantry",
                "Warehouse Anolid",
               "4:35 AM"));

        recentActivityList.postValue(list);
        return recentActivityList;
    }
}
