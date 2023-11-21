package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.signup;

public interface SignUpCallback {
    void onLoad(String message);
    void onSuccess();
    void onFailed(String message);
}
