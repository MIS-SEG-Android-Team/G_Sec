package org.rmj.guanzongroup.gsecurity.service;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.DEFAULT_TIME_FORMAT;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolCache;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.ui.activity.AlarmActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class TimeCheckService extends Service {
    private static final String TAG = TimeCheckService.class.getSimpleName();

    private static final int ALARM_NOTIFICATION_ID = 123;
    private static final int FOREGROUND_SERVICE_NOTIFICATION_ID = 199;
    private static final String FOREGROUND_SERVICE_CHANNEL = "ForegroundServiceChannel";
    private static final String ALARM_CHANNEL = "ServiceAlarmChannel";

    private static final long CHECK_INTERVAL = 60 * 1000; // Check every 5 minutes

    @Inject
    ScheduleRepository scheduleRepository;

    @Inject
    PatrolCache patrolCache;

    private MediaPlayer mediaPlayer;


    private Handler handler;
    private final Runnable timeCheckRunnable = new Runnable() {
        @Override
        public void run() {
            checkDatabase();
            handler.postDelayed(this, CHECK_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createForegroundServiceChannel();
        Notification notification = createForegroundServiceNotification();
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        handler.post(timeCheckRunnable);
        Timber.tag(TAG).d("Service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.tag(TAG).d("Service destroyed");
        handler.removeCallbacks(timeCheckRunnable);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("NewApi")
    private void checkDatabase() {
        try {

            List<PatrolScheduleEntity> patrolSchedule = scheduleRepository.getPatrolScheduleList();
            if (patrolSchedule == null) {
                return;
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);

            // Parse the current time
            LocalTime currentTime = LocalTime.now();
            Timber.tag(TAG).d("Current Time: %s", currentTime.format(dateTimeFormatter));

            patrolSchedule.sort(new TimeComparator());
            for (int x = 0; x < patrolSchedule.size(); x++) {
                PatrolScheduleEntity schedule = patrolSchedule.get(x);

                // Parse the time from the list
                LocalTime patrolTime = LocalTime.parse(schedule.getDTimexxxx(), dateTimeFormatter);
//                Timber.tag(TAG).d("Patrol Schedule: %s", patrolTime);

                int comparison = currentTime.compareTo(patrolTime);

                if (comparison < 0) {
                    patrolCache.setPatrolStarted(false);
                    Duration duration = Duration.between(currentTime, patrolTime);

                    long minutes = duration.toMinutes() % 60;

                    if (minutes <= 10) {
                        if (minutes > 5) {
                            showNotification("Patrol Reminder", "Your upcoming patrol schedule will start in " + minutes + " minutes.");
                        } else {
                            showNotification("Patrol Reminder", "Your upcoming patrol schedule will start soon.");
                        }
                    }
                    break;
                }

                if (comparison > 0) {
                    Duration duration = Duration.between(currentTime, patrolTime);

                    long minutes = duration.toMinutes() % 60;

                    boolean patrolStarted = patrolCache.getPatrolStarted();

                    if (!patrolStarted) {
                        if (minutes <= 1 && minutes > -25) {
                            patrolCache.setPatrolSchedule(patrolTime.format(dateTimeFormatter));
                            Timber.tag(TAG).d("Patrol Schedule: %s", patrolTime.format(dateTimeFormatter));
                            Intent intent = new Intent(this, AlarmActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Timber.tag(TAG).d("Starting alarm activity...");
                            break;
                        }
                    }

                    Timber.tag(TAG).d("Validating time...");
                    int nextPatrol = x + 1;
                    if (nextPatrol < patrolSchedule.size()) {
                        Timber.tag(TAG).d("Validating next patrol time...");
                        LocalTime nextPatrolSchedule = LocalTime.parse(patrolSchedule.get(nextPatrol).getDTimexxxx(), dateTimeFormatter);

                        comparison = currentTime.compareTo(nextPatrolSchedule);
                        if (comparison > 0) {
                            continue;
                        }

                        if (comparison < 0) {
                            patrolCache.setPatrolSchedule(patrolTime.format(dateTimeFormatter));
                            Timber.tag(TAG).d("Patrol Schedule: %s", patrolTime.format(dateTimeFormatter));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    ALARM_CHANNEL,
                    "Alarm Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ALARM_CHANNEL)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ALARM_NOTIFICATION_ID, builder.build());
        playSound();
    }

    private void createForegroundServiceChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    FOREGROUND_SERVICE_CHANNEL,
                    "Patrol Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification createForegroundServiceNotification() {
        Intent notificationIntent = new Intent(this, AuthenticationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, FOREGROUND_SERVICE_CHANNEL)
                .setContentTitle("GSecure Service")
                .setContentText("Patrol alarm is active.")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent);

        return builder.build();
    }

    static class TimeComparator implements Comparator<PatrolScheduleEntity> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);

        @Override
        public int compare(PatrolScheduleEntity o1, PatrolScheduleEntity o2) {
            try {
                Date date1 = dateFormat.parse(o1.getDTimexxxx());
                Date date2 = dateFormat.parse(o2.getDTimexxxx());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0; // Handle parsing error
            }
        }
    }

    private void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.notification);
        mediaPlayer.setOnCompletionListener(MediaPlayer::release);
        mediaPlayer.start();
    }
}
