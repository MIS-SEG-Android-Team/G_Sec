package org.rmj.guanzongroup.gsecurity.task;

public interface OnDoBackgroundTaskListener {
    Object DoInBackground(Object args);
    void OnPostExecute(Object object);
}
