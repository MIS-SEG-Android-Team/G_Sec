package org.rmj.guanzongroup.gsecurity.ui.screens.report;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPersonnelPatrolReportParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport.PersonnelPatrolReport;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolReportRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMPatrolReport extends ViewModel {

    private final PatrolReportRepository patrolReportRepository;
    private final MutableLiveData<String> userID = new MutableLiveData<>("");
    private final MutableLiveData<String> userName = new MutableLiveData<>("");
    private final MutableLiveData<String> dateStart = new MutableLiveData<>("");
    private final MutableLiveData<String> dateEnd = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> loadingReports = new MutableLiveData<>(false);
    private final MutableLiveData<List<PersonnelPatrolReport>> reports = new MutableLiveData<>(null);

    @Inject
    public VMPatrolReport(PatrolReportRepository patrolReportRepository) {
        this.patrolReportRepository = patrolReportRepository;
    }

    public void setUserID(String value) {
        userID.setValue(value);
    }
    public void setUserName(String value) {
        userName.setValue(value);
    }

    public void setDateStart(String value) {
        dateStart.setValue(value);
        hasCompleteInfo.setValue(!value.isEmpty() && !dateEnd.getValue().isEmpty());
    }

    public void setDateEnd(String value) {
        dateEnd.setValue(value);
        hasCompleteInfo.setValue(!dateStart.getValue().isEmpty() && !value.isEmpty());
    }

    public LiveData<Boolean> completeInfo() {
        return hasCompleteInfo;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> isLoadingReports() {
        return loadingReports;
    }

    public LiveData<List<PersonnelPatrolReport>> getReports() {
        return reports;
    }

    public String getExportFileName() {
        String username = userName.getValue();
        String datefrom = dateStart.getValue();
        String datethru = dateEnd.getValue();
        return username + "_" + datefrom + " - " + datethru + "_Report";
    }

    @SuppressLint("CheckResult")
    public void getPatrolReportForPersonnel() {
        loadingReports.setValue(true);
        GetPersonnelPatrolReportParams params = new GetPersonnelPatrolReportParams();
        params.setSUserIDxx(userID.getValue());
        params.setDStartDte(dateStart.getValue());
        params.setDEndDatex(dateEnd.getValue());
        patrolReportRepository.getPersonnelPatrolReport(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingReports.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getMessage());
                                return;
                            }

                            reports.setValue(response.getData());
                        },
                        error -> {
                            loadingReports.setValue(false);
                            errorMessage.setValue(error.getMessage());
                        }
                );
    }
}