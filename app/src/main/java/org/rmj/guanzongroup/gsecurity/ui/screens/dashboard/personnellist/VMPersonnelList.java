package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnellist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.mockdata.ListPersonnel;
import org.rmj.guanzongroup.gsecurity.pojo.user.Personnel;

import java.util.List;

public class VMPersonnelList extends AndroidViewModel {

    public VMPersonnelList(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Personnel>> getPersonnelList(){
        return ListPersonnel.getPersonnelList();
    }
}