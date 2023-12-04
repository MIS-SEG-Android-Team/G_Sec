package org.rmj.guanzongroup.gsecurity.mockdata;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_ALARM;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_VISIT;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.gsecurity.pojo.notification.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListNotification {

    public static LiveData<List<Notification>> getNotificationList(){
        MutableLiveData<List<Notification>> notificationList = new MutableLiveData<>();

        List<Notification> list = new ArrayList<>();
        list.add(new Notification(
                "Next Patrol Alarm",
                "Your next patrol will be in 5 minutes.",
                "Dec. 1, 2023 1:05 AM",
                "Admin",
                NOTIFICATION_ALARM));
        list.add(new Notification(
                "Next Patrol Alarm",
                "Your next patrol will be soon.",
                "Dec. 1, 2023 12:35 AM",
                "Admin",
                NOTIFICATION_ALARM));
        list.add(new Notification(
                "Patrol Check Requested",
                "Visit the Warehouse 1 Back Door, Remarks: Check for any missing umbrella.",
                "Nov. 30, 2023 10:30 PM",
                "Admin",
                NOTIFICATION_VISIT));
        list.add(new Notification(
                "Next Patrol Alarm",
                "Your next patrol will be soon.",
                "Dec. 1, 2023 12:35 AM",
                "Admin",
                NOTIFICATION_ALARM));

        notificationList.postValue(list);
        return notificationList;
    }
}
