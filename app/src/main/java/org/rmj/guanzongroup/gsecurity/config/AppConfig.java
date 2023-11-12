package org.rmj.guanzongroup.gsecurity.config;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
    private static final String TAG = AppConfig.class.getSimpleName();

    private final Context context;
    private static AppConfig instance;

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static final String CONFIG_NAME = "GSecure_Local_Configuration";

    private AppConfig(Context context){
        this.context = context;
        this.preferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public static AppConfig getIntance(Context context){
        if(instance == null){
            instance = new AppConfig(context);
        }

        return instance;
    }
}
