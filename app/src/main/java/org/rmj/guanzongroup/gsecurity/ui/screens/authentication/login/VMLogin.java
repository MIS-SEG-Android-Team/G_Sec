package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import static org.rmj.guanzongroup.gsecurity.constants.Messages.getLocalMessage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.guanzongroup.gsecurity.pojo.login.LoginCredentials;
import org.rmj.guanzongroup.gsecurity.task.OnTaskExecuteListener;
import org.rmj.guanzongroup.gsecurity.task.TaskExecutor;

public class VMLogin extends AndroidViewModel {

    private String message = "";
    public VMLogin(@NonNull Application application) {
        super(application);
    }

    public void login(LoginCredentials loginCredentials, LoginCallback callback){
        TaskExecutor.Execute(loginCredentials, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.onLogin("Authenticating", "Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {
                try{

                } catch (Exception e){
                    e.printStackTrace();
                    message = getLocalMessage(e);
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {
                callback.onSuccess("Login success");
            }
        });
    }
}