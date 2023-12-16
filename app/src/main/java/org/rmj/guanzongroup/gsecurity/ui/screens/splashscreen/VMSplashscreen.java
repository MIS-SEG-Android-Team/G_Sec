package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.preferences.TokenDeviceID;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSplashscreen extends ViewModel {

    private final TokenDeviceID appConfig;
    private final BaseHeaderInterceptor baseHeaderInterceptor;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public VMSplashscreen(
            TokenDeviceID appConfig,
            BaseHeaderInterceptor baseHeaderInterceptor,
            AuthenticationRepository authenticationRepository
    ) {
        this.appConfig = appConfig;
        this.baseHeaderInterceptor = baseHeaderInterceptor;
        this.authenticationRepository = authenticationRepository;
    }

    public void setDeviceID(String deviceID) {
        appConfig.setDeviceID(deviceID);
        baseHeaderInterceptor.setDeviceID(appConfig.getDeviceID());
    }

    public void setFirebaseToken() {
        appConfig.setFirebaseToken("f7qNSw8TRPWHSCga0g8YFF:APA91bG3i_lBPPWv9bbRasNzRH1XX1y0vzp6Ct8S_a-yMPDvSmud8FEVPMr26zZtBPHq2CmaIw9Rx0MZmf3sbuK44q3vQemUBoPPS4Meybw8pnTpcs3p0VbiTuoLHJtdncC6BgirJxt3");
        baseHeaderInterceptor.setFirebaseToken(appConfig.getFirebaseToken());
    }
}