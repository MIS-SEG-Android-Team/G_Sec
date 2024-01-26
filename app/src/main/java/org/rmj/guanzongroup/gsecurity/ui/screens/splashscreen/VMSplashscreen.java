package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.TokenCache;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSplashscreen extends ViewModel {

    private final TokenCache tokenCache;
    private final BaseHeaderInterceptor baseHeaderInterceptor;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public VMSplashscreen(
            TokenCache tokenDeviceID,
            BaseHeaderInterceptor baseHeaderInterceptor,
            AuthenticationRepository authenticationRepository
    ) {
        this.tokenCache = tokenDeviceID;
        this.baseHeaderInterceptor = baseHeaderInterceptor;
        this.authenticationRepository = authenticationRepository;
    }

    public void setDeviceID(String deviceID) {
        tokenCache.setDeviceID(deviceID);
    }

    public void setFirebaseToken(String token) {
        tokenCache.setFirebaseToken(token);
    }

    public Boolean hasSession() {
        return authenticationRepository.hasUserSession();
    }

    public Boolean isAdmin() {
        return authenticationRepository.isAdmin();
    }
}