package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.notifications;

import static org.rmj.guanzongroup.gsecurity.mockdata.ListNotification.getNotificationList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.pojo.notification.Notification;

import java.util.List;

public class VMNotifications extends AndroidViewModel {

    public VMNotifications(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Notification>> getNotifications() {
        return getNotificationList();
    }
}