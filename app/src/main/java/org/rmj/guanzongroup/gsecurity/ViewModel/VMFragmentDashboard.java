package org.rmj.guanzongroup.gsecurity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.BullsEye.obj.BranchPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleStatus;
import org.rmj.g3appdriver.lib.Notifications.Obj.Notification;
import org.rmj.g3appdriver.lib.Panalo.Obj.ILOVEMYJOB;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMFragmentDashboard extends AndroidViewModel {
    private final EmployeeMaster poEmploye;
    private final BranchPerformance poSys;
    private final Notification poNotification;
    private final Application instance;
    private final ConnectionUtil poConn;

    public VMFragmentDashboard(@NonNull Application application) {
        super(application);
        poEmploye = new EmployeeMaster(application);
        this.poSys = new BranchPerformance(application);
        this.poNotification = new Notification(application);
        this.poConn = new ConnectionUtil(application);
        this.instance = application;
    }
    // TODO: Implement the ViewModel

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }



    public LiveData<Integer> GetAllUnreadNotificationCount(){
        return poNotification.GetAllUnreadNotificationCount();
    }
}