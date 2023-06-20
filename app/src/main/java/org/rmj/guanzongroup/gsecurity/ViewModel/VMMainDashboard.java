package org.rmj.guanzongroup.gsecurity.ViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.os.Bundle;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeRole;
import org.rmj.g3appdriver.lib.Panalo.Obj.ILOVEMYJOB;
import org.rmj.guanzongroup.gsecurity.Fragment.Fragment_AdminHome;
import org.rmj.guanzongroup.gsecurity.Fragment.Fragment_PersonnelHome;
import org.rmj.guanzongroup.gsecurity.Fragment.Fragment_PersonnelList;
import org.rmj.guanzongroup.gsecurity.R;

import java.util.List;

public class VMMainDashboard extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    //    private final DataSyncService poNetRecvr;
    private final EmployeeMaster poUser;

    public VMMainDashboard(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poUser = new EmployeeMaster(app);
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRole() {
        return poUser.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles() {
        return poUser.getChildRoles();
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo() {
        return poUser.GetEmployeeInfo();
    }

    public Fragment GetUserFragments(Integer args) {
        Fragment userLevel;
        if (args == 3) {
            userLevel = new Fragment_AdminHome();
            return userLevel;
        } else {
            userLevel = new Fragment_PersonnelHome();
            return userLevel;
        }
    }
}
