package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.task.OnDoBackgroundTaskListener;
import org.rmj.guanzongroup.gsecurity.task.OnLoadApplicationListener;
import org.rmj.guanzongroup.gsecurity.task.TaskExecutor;

public class VMSplashscreen extends AndroidViewModel {

    public VMSplashscreen(@NonNull Application application) {
        super(application);
    }

    public void startApp(OnLoadApplicationCallback callback){
        TaskExecutor loTask = new TaskExecutor();
        loTask.setOnLoadApplicationListener(new OnLoadApplicationListener() {
            @Override
            public Object DoInBackground() {
                for (int x = 0; x < 2; x++){
                    loTask.publishProgress(x);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return true;
            }

            @Override
            public void OnProgress(int progress) {
                callback.onProgress(progress);
            }

            @Override
            public void OnPostExecute(Object object) {
                boolean success = (boolean) object;
                if(!success){
                    callback.onFailed("Loading application failed...");
                    return;
                }

                callback.onFinished("Starting application...");
            }
        });
        loTask.Execute();
    }
}