package org.rmj.guanzongroup.gsecurity.base;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class GSecureApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // DebugTree has all usual logging functionality
        Timber.plant(new Timber.DebugTree());
    }
}
