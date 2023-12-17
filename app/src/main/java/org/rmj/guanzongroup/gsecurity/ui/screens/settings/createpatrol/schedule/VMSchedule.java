package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolSchedulerCache;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMSchedule extends ViewModel {

    private final PatrolSchedulerCache patrolSchedulerCache;

    private final MutableLiveData<Boolean> importingCheckpoints = new MutableLiveData<>(false);

    @Inject
    public VMSchedule(
            PatrolSchedulerCache patrolSchedulerCache
    ) {
        this.patrolSchedulerCache = patrolSchedulerCache;
    }

    public LiveData<Boolean> importingCheckpoints() {
        return importingCheckpoints;
    }

}