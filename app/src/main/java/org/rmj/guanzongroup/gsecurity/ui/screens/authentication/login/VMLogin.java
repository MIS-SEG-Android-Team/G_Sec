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

        if(loginCredentials.getEmail().equalsIgnoreCase("admin") &&
            loginCredentials.getPassword().equalsIgnoreCase("admin")) {

            callback.onAdminSuccessLogin("Login success");
        } else if(loginCredentials.getEmail().equalsIgnoreCase("user") &&
                loginCredentials.getPassword().equalsIgnoreCase("user")) {

            callback.onPersonnelSuccessLogin("Login success");
        } else {

            callback.onFailed("Invalid email or password.");
        }
    }
}