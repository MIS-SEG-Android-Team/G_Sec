package org.rmj.guanzongroup.gsecurity.task;

public interface OnTaskExecuteListener {
    void OnPreExecute();
    Object DoInBackground(Object args);
    void OnPostExecute(Object object);
}
