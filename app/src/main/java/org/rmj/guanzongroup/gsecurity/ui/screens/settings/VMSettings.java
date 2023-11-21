package org.rmj.guanzongroup.gsecurity.ui.screens.settings;

import static org.rmj.guanzongroup.gsecurity.constants.Messages.PLEASE_WAIT;
import static org.rmj.guanzongroup.gsecurity.constants.Messages.UNEXPECTED_ERROR_OCCURRED;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class VMSettings extends AndroidViewModel {

    public VMSettings(@NonNull Application application) {
        super(application);
    }

    public void logoutUser(SettingsCallback.LogoutUserCallback callback){
        callback.onLoad(PLEASE_WAIT);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        firebaseAuth.addAuthStateListener(firebaseAuth1 -> {
            if(firebaseAuth1.getCurrentUser() == null){
                callback.onSuccess();
            } else {
                callback.onFailed(UNEXPECTED_ERROR_OCCURRED);
            }
        });
    }
}