package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

public interface LoginCallback {
    void onLogin(String message);
    void onAdminSuccessLogin(String message);
    void onPersonnelSuccessLogin(String message);
    void onFailed(String message);
}
