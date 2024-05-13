package org.rmj.guanzongroup.gsecurity.service;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_VISIT;

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
import org.rmj.guanzongroup.gsecurity.data.preferences.TokenCache;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class GSecureMessagingService extends FirebaseMessagingService {

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
}
