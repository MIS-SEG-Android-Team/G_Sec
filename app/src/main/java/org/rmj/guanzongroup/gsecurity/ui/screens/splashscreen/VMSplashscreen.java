package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.TokenCache;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.UserProfileRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSplashscreen extends ViewModel {

    private final TokenCache tokenCache;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public VMSplashscreen(
            TokenCache tokenDeviceID,
            AuthenticationRepository authenticationRepository,
            UserProfileRepository userProfileRepository
    ) {
        this.tokenCache = tokenDeviceID;
        this.authenticationRepository = authenticationRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public void setDeviceID(String deviceID) {
        tokenCache.setDeviceID(deviceID);
    }

    public void setFirebaseToken(String token) {
        tokenCache.setFirebaseToken(token);
    }

    @SuppressLint("NewApi")
    public Boolean hasSession() {
        if (userProfileRepository.hasUserSession()) {
            String sessionDateTime = userProfileRepository.getSessionDateTime();
            if (sessionDateTime.isEmpty()) {return false; }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            LocalDate sessionDate = LocalDate.parse(sessionDateTime, formatter);
            LocalDate currentDate = LocalDate.now();
            if (sessionDate.isEqual(currentDate)) {
                return true;
            } else {
                userProfileRepository.clearCache();
                return false;
            }
        }

        return false;
    }

    public Boolean isAdmin() {
        return userProfileRepository.isAdmin();
    }
}