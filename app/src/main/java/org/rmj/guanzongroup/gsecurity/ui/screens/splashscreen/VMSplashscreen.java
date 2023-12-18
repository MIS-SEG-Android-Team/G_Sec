package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.TokenDeviceID;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;
import org.rmj.guanzongroup.gsecurity.data.repository.AuthenticationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSplashscreen extends ViewModel {

    private final TokenDeviceID tokenDeviceID;
    private final BaseHeaderInterceptor baseHeaderInterceptor;
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public VMSplashscreen(
            TokenDeviceID tokenDeviceID,
            BaseHeaderInterceptor baseHeaderInterceptor,
            AuthenticationRepository authenticationRepository
    ) {
        this.tokenDeviceID = tokenDeviceID;
        this.baseHeaderInterceptor = baseHeaderInterceptor;
        this.authenticationRepository = authenticationRepository;
    }

    public void setDeviceID(String deviceID) {
        tokenDeviceID.setDeviceID(deviceID);
        baseHeaderInterceptor.setDeviceID(tokenDeviceID.getDeviceID());
    }

    public void setFirebaseToken() {
        tokenDeviceID.setFirebaseToken("f7qNSw8TRPWHSCga0g8YFF:APA91bG3i_lBPPWv9bbRasNzRH1XX1y0vzp6Ct8S_a-yMPDvSmud8FEVPMr26zZtBPHq2CmaIw9Rx0MZmf3sbuK44q3vQemUBoPPS4Meybw8pnTpcs3p0VbiTuoLHJtdncC6BgirJxt3");
        baseHeaderInterceptor.setFirebaseToken(tokenDeviceID.getFirebaseToken());
    }

    public Boolean hasSession() {
        return authenticationRepository.hasUserSession();
    }

    public Boolean isAdmin() {
        return authenticationRepository.isAdmin();
    }
}