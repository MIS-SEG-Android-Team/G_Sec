package org.rmj.guanzongroup.gsecurity.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.model.iPM;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Etc.Branch;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMFragmentAdminHome extends AndroidViewModel {
    private final Application instance;

    private final EmployeeMaster poEmployee;
    private iPM poApp;
    private final Branch pobranch;
    private final ConnectionUtil poConn;

    private final AppConfigPreference poConfigx;
    private final MutableLiveData<String> psVersion = new MutableLiveData<>();
    public VMFragmentAdminHome(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmployee = new EmployeeMaster(application);
        this.pobranch = new Branch(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.psVersion.setValue(poConfigx.getVersionInfo());
        this.poConn = new ConnectionUtil(application);
    }
    // TODO: Implement the ViewModel

}