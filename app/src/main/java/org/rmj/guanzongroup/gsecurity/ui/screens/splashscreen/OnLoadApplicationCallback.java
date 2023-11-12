package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

public interface OnLoadApplicationCallback {
    void onProgress(int progress);
    void onFinished(String args);
    void onFailed(String message);
}
