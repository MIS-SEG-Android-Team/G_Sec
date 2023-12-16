package org.rmj.guanzongroup.gsecurity.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class TokenDeviceID {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "GSecure_Local_Configuration";

    private static final String APP_FIRST_LAUNCH = "is_first_launch";
    private static final String FIREBASE_TOKEN = "firebase_token";
    private static final String DEVICE_ID = "device_id";

    @Inject
    public TokenDeviceID(Application application){
        this.preferences = application.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setAppFirstLaunch(boolean isAppFirstLaunch){
        editor.putBoolean(APP_FIRST_LAUNCH, isAppFirstLaunch);
        editor.commit();
    }

    public boolean isAppFirstLaunch(){
        return preferences.getBoolean(APP_FIRST_LAUNCH, false);
    }

    public void setFirebaseToken(String token) {
        editor.putString(FIREBASE_TOKEN, token);
        editor.commit();
    }

    public String getFirebaseToken() {
        return preferences.getString(FIREBASE_TOKEN, "");
    }

    public void setDeviceID(String deviceID) {
        editor.putString(DEVICE_ID, deviceID);
        editor.commit();
    }

    public String getDeviceID() {
        return preferences.getString(DEVICE_ID, "");
    }
}
