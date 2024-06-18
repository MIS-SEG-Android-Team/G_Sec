package org.rmj.guanzongroup.gsecurity.data.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthenticationCache {


    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "AuthenticationCache";

    private static final String DEFAULT_AUTHENTICATION = "default_auth";
    private static final String AUTHENTICATION_SESSION_TIMEOUT = "session_timeout";

    @Inject
    public AuthenticationCache(Application application) {
        this.preferences = application.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public void setDefaultAuthentication(int method) {
        editor.putInt(DEFAULT_AUTHENTICATION, method);
        editor.commit();
    }

    public int getDefaultAuthentication() {
        return preferences.getInt(DEFAULT_AUTHENTICATION, 0);
    }

    public void setAuthenticationSessionTimeout(String dateTime) {
        editor.putString(AUTHENTICATION_SESSION_TIMEOUT, dateTime);
        editor.commit();
    }

    public String getAuthenticationSessionTimeout() {
        return preferences.getString(AUTHENTICATION_SESSION_TIMEOUT, "");
    }

    public static final int MPIN = 0;
    public static final int EMAIL = 1;
}
