package org.rmj.guanzongroup.gsecurity.service;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.CHANNEL_ID;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.ui.activity.AlarmActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class TimeCheckService extends Service {
    private static final String TAG = TimeCheckService.class.getSimpleName();

    private static final long CHECK_INTERVAL = 5 * 60 * 1000; // Check every 5 minutes

    @Inject
    ScheduleRepository scheduleRepository;

    private Handler handler;
    private Runnable timeCheckRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.tag(TAG).d("Service created");
        handler = new Handler();
        timeCheckRunnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        handler.post(timeCheckRunnable);

        List<PatrolScheduleEntity> patrolSchedule = scheduleRepository.getPatrolScheduleList();
        if (patrolSchedule == null) {
            return;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag(TAG).d("Service destroyed");
        handler.removeCallbacks(timeCheckRunnable);
    }

    private void checkDatabase() {

    }

    @SuppressLint("MissingPermission")
    private void showNotification() {
        Intent intent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Patrol Reminder")
                .setContentText("It's time to patrol the checkpoints.")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Patrol Channel";
            String description = "Channel for Patrol Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
