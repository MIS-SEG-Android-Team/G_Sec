package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

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

        callback.onLogin("Authenticating", "Please wait...");

        LoginCredentials.Validator validator = new LoginCredentials.Validator();

        if(!validator.isDataValid(loginCredentials)){
            callback.onFailed(validator.getMessage());
            return;
        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(
                loginCredentials.getEmail(),
                loginCredentials.getPassword()
        ).addOnCompleteListener(task -> {
            if(!task.isSuccessful()) {
                Exception exception = task.getException();
                FirebaseAuthException authException = (FirebaseAuthException) exception;

                assert authException != null;
                String errorCode = authException.getErrorCode();

                String message = getMessage(errorCode);
                callback.onFailed(message);
                return;
            }

            callback.onSuccess("Login success");
        });
    }
}