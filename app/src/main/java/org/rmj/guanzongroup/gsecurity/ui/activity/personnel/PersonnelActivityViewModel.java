package org.rmj.guanzongroup.gsecurity.ui.activity.personnel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PersonnelActivityViewModel extends ViewModel {

    private final MutableLiveData<Boolean> notificationPermissionEnabled = new MutableLiveData<>(false);

    @Inject
    public PersonnelActivityViewModel() {

    }

    public void setNotificationPermissionEnabled(boolean value) {
        notificationPermissionEnabled.setValue(value);
    }

    public LiveData<Boolean> isNotificationPermissionEnabled() {
        return notificationPermissionEnabled;
    }
}
