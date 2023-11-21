package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

public interface LoginCallback {
    void onLogin(String message);
    void onSuccess(String message);
    void onFailed(String message);
}
