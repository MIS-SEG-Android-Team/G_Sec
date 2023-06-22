package org.rmj.guanzongroup.gsecurity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.BullsEye.obj.AreaPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.lib.Notifications.Obj.BranchOpeningMonitor;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;
import org.rmj.g3appdriver.lib.Panalo.Obj.ILOVEMYJOB;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMFragmentPersonnelHome extends AndroidViewModel {
    private final EmployeeMaster poEmploye;
    private final MutableLiveData<String> pdTransact = new MutableLiveData<>();
    public VMFragmentPersonnelHome(@NonNull Application application) {
        super(application);

        this.poEmploye = new EmployeeMaster(application);
    }
    public LiveData<EEmployeeInfo> getEmployeeInfo() {
        return poEmploye.GetEmployeeInfo();
    }
    public void SetSelectedDate(String fsVal){
        this.pdTransact.setValue(fsVal);
    }
    // TODO: Implement the ViewModel
}