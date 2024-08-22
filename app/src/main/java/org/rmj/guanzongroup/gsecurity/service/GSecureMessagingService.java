package org.rmj.guanzongroup.gsecurity.service;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_VISIT;
import static org.rmj.guanzongroup.gsecurity.utils.BugReport.reportException;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.preferences.TokenCache;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.RequestVisitRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.route.PatrolRouteEntity;
import org.rmj.guanzongroup.gsecurity.data.room.patrol.schedule.PatrolScheduleEntity;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@AndroidEntryPoint
public class GSecureMessagingService extends FirebaseMessagingService {

    @Inject
    PatrolRepository patrolRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @Inject
    RequestVisitRepository requestVisitRepository;

    @Inject
    DataStore dataStore;

    @Inject
    TokenCache tokenCache;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("Firebase messaging service created");
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Timber.d("Firebase Token: %s", token);
        tokenCache.setFirebaseToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String message = Objects.requireNonNull(remoteMessage.getNotification()).getBody();

        Timber.d("Title: %s", title);
        Timber.d("Message: %s", message);
        Timber.d("Remote Message: %s", remoteMessage);

        showNotification(
                title,
                message
        );

        importPatrolRoutes();
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create a notification channel for Android Oreo and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_VISIT, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an explicit intent for an activity in your app
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_VISIT)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Show the notification
        notificationManager.notify(0, notificationBuilder.build());
    }
    
    @SuppressLint("CheckResult")
    private void importPatrolRoutes(){

        try{

            /*GetPatrolRouteParams params = new GetPatrolRouteParams();

            params.setSUserIDxx(dataStore.getUserId());

            patrolRepository.getPatrolRouteSchedule(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            listBaseResponse -> {

                                Timber.tag("GSecureMessagingService").d(listBaseResponse.getResult());

                                if (listBaseResponse.getResult().equalsIgnoreCase("error")) {
                                    return;
                                }

                                List<PatrolRouteEntity> patrolRoutes = listBaseResponse.getData().get(0).getSRoutexxx();
                                List<PatrolScheduleEntity> patrolSchedules = listBaseResponse.getData().get(0).getSSchedule();

                                if (patrolSchedules.isEmpty()) {
                                    Timber.tag("GSecureMessagingService").d("Imported patrol schedules is empty.");
                                    reportException("", "Imported patrol schedules is empty.");
                                }

                                patrolRepository.savePatrolRoute(patrolRoutes);
                                scheduleRepository.savePatrolSchedule(patrolSchedules);
                            },

                            throwable -> {
                                Timber.tag("GSecureMessagingService").d(throwable);
                                reportException("", throwable.toString());
                            }
                    );*/

            requestVisitRepository.getVisitRequest()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(

                            voidBaseResponse -> Timber.tag("GSecureMessagingService").d(voidBaseResponse.getResult())

                    );

        }catch (Exception e){
            Timber.tag("GSecureMessagingService").d(e);
        }
        
    }
}
