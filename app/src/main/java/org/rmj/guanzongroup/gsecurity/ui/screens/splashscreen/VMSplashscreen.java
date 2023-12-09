package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.config.AppConfig;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;
import org.rmj.guanzongroup.gsecurity.task.OnDoBackgroundTaskListener;
import org.rmj.guanzongroup.gsecurity.task.OnLoadApplicationListener;
import org.rmj.guanzongroup.gsecurity.task.TaskExecutor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSplashscreen extends ViewModel {

    private AppConfig appConfig;
    private BaseHeaderInterceptor baseHeaderInterceptor;

    @Inject
    public VMSplashscreen(AppConfig appConfig, BaseHeaderInterceptor baseHeaderInterceptor) {
        this.appConfig = appConfig;
        this.baseHeaderInterceptor = baseHeaderInterceptor;
    }

    public void setDeviceID(String deviceID) {
        appConfig.setDeviceID(deviceID);
        baseHeaderInterceptor.setDeviceID(appConfig.getDeviceID());
    }

    public void setFirebaseToken() {
        appConfig.setFirebaseToken("f7qNSw8TRPWHSCga0g8YFF:APA91bG3i_lBPPWv9bbRasNzRH1XX1y0vzp6Ct8S_a-yMPDvSmud8FEVPMr26zZtBPHq2CmaIw9Rx0MZmf3sbuK44q3vQemUBoPPS4Meybw8pnTpcs3p0VbiTuoLHJtdncC6BgirJxt3");
        baseHeaderInterceptor.setFirebaseToken(appConfig.getFirebaseToken());
    }

    public void startApp(OnLoadApplicationCallback callback){
        TaskExecutor loTask = new TaskExecutor();
        loTask.setOnLoadApplicationListener(new OnLoadApplicationListener() {
            @Override
            public Object DoInBackground() {
                for (int x = 0; x < 2; x++){
                    loTask.publishProgress(x);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return true;
            }

            @Override
            public void OnProgress(int progress) {
                callback.onProgress(progress);
            }

            @Override
            public void OnPostExecute(Object object) {
                boolean success = (boolean) object;
                if(!success){
                    callback.onFailed("Loading application failed...");
                    return;
                }

                callback.onFinished("Starting application...");
            }
        });
        loTask.Execute();
    }
}