package org.rmj.guanzongroup.gsecurity.ViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeRole;
import org.rmj.guanzongroup.gsecurity.Fragment.Dashboard.Fragment_Dashboard;
import org.rmj.guanzongroup.gsecurity.Fragment.Dashboard.Fragment_HomePersonnel;
import org.rmj.guanzongroup.gsecurity.Fragment.Home.Fragment_AdminHome;
import org.rmj.guanzongroup.gsecurity.Fragment.Home.Fragment_PersonnelHome;

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

    public Fragment GetUserFragments(EEmployeeInfo args) {
        Fragment userLevel;
        switch (args.getEmpLevID()) {
            case 3:
                userLevel = new Fragment_Dashboard();
                break;
            case 4:
                userLevel = new Fragment_HomePersonnel();
                break;
            default:
                userLevel = new Fragment_Dashboard();
                break;
//                switch (args.getDeptIDxx()) {
//                    case "032":
//                        userLevel = new Fragment_Dashboard();
//                        break;
//                    default:
//                        userLevel = new Fragment_Dashboard();
//                        break;
//                }
        }
        return userLevel;
    }
}


