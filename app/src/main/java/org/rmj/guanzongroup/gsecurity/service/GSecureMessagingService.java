package org.rmj.guanzongroup.gsecurity.service;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dagger.hilt.android.AndroidEntryPoint;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
@AndroidEntryPoint
public class GSecureMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    }
}
