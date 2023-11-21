package org.rmj.guanzongroup.gsecurity.ui.screens.settings;

public class SettingsCallback {

    public interface LogoutUserCallback {
        void onLoad(String message);
        void onSuccess();
        void onFailed(String message);
    }
}
