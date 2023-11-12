package org.rmj.guanzongroup.gsecurity.task;

public interface OnLoadApplicationListener {
    Object DoInBackground();
    void OnProgress(int progress);
    void OnPostExecute(Object object);
}
