package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import static org.rmj.guanzongroup.gsecurity.constants.Messages.PLEASE_WAIT;
import static org.rmj.guanzongroup.gsecurity.constants.Messages.getMessage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.rmj.guanzongroup.gsecurity.pojo.login.LoginCredentials;

public class VMLogin extends AndroidViewModel {

    public VMLogin(@NonNull Application application) {
        super(application);
    }

    public void login(LoginCredentials loginCredentials, LoginCallback callback){

        callback.onLogin(PLEASE_WAIT);

        LoginCredentials.Validator validator = new LoginCredentials.Validator();

        if(!validator.isDataValid(loginCredentials)){
            callback.onFailed(validator.getMessage());
            return;
        }

        callback.onPersonnelSuccessLogin("Login success");
    }
}