package org.rmj.guanzongroup.gsecurity.di;

import android.app.Application;

import org.rmj.guanzongroup.gsecurity.data.preferences.AuthenticationCache;
import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;
import org.rmj.guanzongroup.gsecurity.data.preferences.TokenDeviceID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class PreferencesModule {

    @Provides
    @Singleton
    public static DataStore provideDataStore(Application application) {
        return new DataStore(application);
    }

    @Provides
    @Singleton
    public static PatrolSchedulerCache providePatrolSchedulerCache(Application application) {
        return new PatrolSchedulerCache(application);
    }

    @Provides
    @Singleton
    public static TokenDeviceID provideTokenDeviceID(Application application) {
        return new TokenDeviceID(application);
    }

    @Provides
    @Singleton
    public static AuthenticationCache provideAuthenticationCache(Application application) {
        return new AuthenticationCache(application);
    }
}
