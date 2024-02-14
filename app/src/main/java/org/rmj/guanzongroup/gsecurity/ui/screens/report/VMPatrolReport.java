package org.rmj.guanzongroup.gsecurity.ui.screens.report;

import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPersonnelPatrolReportParams;
import org.rmj.guanzongroup.gsecurity.data.repository.PatrolReportRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMPatrolReport extends ViewModel {

    private final PatrolReportRepository patrolReportRepository;

    @Inject
    public VMPatrolReport(PatrolReportRepository patrolReportRepository) {
        this.patrolReportRepository = patrolReportRepository;
    }

    @SuppressLint("CheckResult")
    public void getPatrolReportForPersonnel(String userID) {
        GetPersonnelPatrolReportParams params = new GetPersonnelPatrolReportParams();
        params.setSUserIDxx(userID);
        patrolReportRepository.getPersonnelPatrolReport(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                        },
                        error -> {

                        }
                );
    }
}