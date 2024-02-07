package org.rmj.guanzongroup.gsecurity.ui.screens.request;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.GetActivePersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.RequestSiteVisitParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.repository.CheckpointRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.RequestVisitRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class VMVisitArea extends ViewModel {

    private final CheckpointRepository checkpointRepository;
    private final PersonnelRepository personnelRepository;
    private final RequestVisitRepository requestVisitRepository;
    private final WarehouseRepository warehouseRepository;

    private final MutableLiveData<Boolean> loadingWarehouse = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loadingPersonnel = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> loadingCheckpoint = new MutableLiveData<>(false);
    private final MutableLiveData<List<ActivePersonnelModel>> personnelList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<NFCDeviceEntity>> checkpointList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> branch = new MutableLiveData<>("");
    private final MutableLiveData<String> warehouseID = new MutableLiveData<>("");
    private final MutableLiveData<String> personnelID = new MutableLiveData<>("");
    private final MutableLiveData<String> checkpointID = new MutableLiveData<>("");
    private final MutableLiveData<String> scheduleTime = new MutableLiveData<>("");
    private final MutableLiveData<String> remarks = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> sendingRequest = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> requestSent = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMVisitArea(CheckpointRepository checkpointRepository,
                       PersonnelRepository personnelRepository,
                       RequestVisitRepository requestVisitRepository,
                       WarehouseRepository warehouseRepository) {
        this.checkpointRepository = checkpointRepository;
        this.personnelRepository = personnelRepository;
        this.requestVisitRepository = requestVisitRepository;
        this.warehouseRepository = warehouseRepository;

        importWarehouse();
        getPersonnels();
    }

    public void setBranch(String value) {
        Timber.tag("Branch ID").d(value);
        this.branch.setValue(value);
    }

    public void setWarehouseID(String value) {
        Timber.tag("Warehouse ID").d(value);
        this.warehouseID.setValue(value);
    }

    public void setPersonnelID(String value) {
        Timber.tag("Personnel ID").d(value);
        this.personnelID.setValue(value);
    }

    public void setCheckpointID(String value) {
        Timber.tag("Checkpoint ID").d(value);
        this.checkpointID.setValue(value);
    }

    public void setScheduleTime(String value) {
        Timber.tag("Schedule Time").d(value);
        this.scheduleTime.setValue(value);
    }

    public void setRemarks(String value) {
        Timber.tag("Remarks").d(value);
        this.remarks.setValue(value);
    }

    public LiveData<String> getWarehouseID() {
        return warehouseID;
    }
    public LiveData<String> getBranch() {
        return branch;
    }

    @SuppressLint("CheckResult")
    private void importWarehouse() {
        loadingWarehouse.setValue(true);

        DateTimeStampParams params = new DateTimeStampParams();
        String timeStamp = warehouseRepository.getLatestTimeStamp();

        if(timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        warehouseRepository.getWarehouse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingWarehouse.setValue(false);

                            if(response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<WarehouseEntity> warehouseEntityList = response.getData();
                            warehouseRepository.saveWarehouses(warehouseEntityList);
                        },
                        error -> loadingWarehouse.setValue(false)
                );
    }

    @SuppressLint("CheckResult")
    public void getNfcTags(String warehouseID) {
        loadingCheckpoint.setValue(true);
        GetNFCTagsParams params = new GetNFCTagsParams();
        params.setSWhouseID(warehouseID);
        String timeStamp = checkpointRepository.getLatestTimeStamp();
        if (timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        checkpointRepository.getNFCTags(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            loadingCheckpoint.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<NFCDeviceEntity> nfcDeviceEntities = response.getData();
                            checkpointRepository.saveNfcTags(nfcDeviceEntities);
                            checkpointList.setValue(nfcDeviceEntities);
                        },
                        error -> loadingCheckpoint.setValue(false)
                );
    }

    @SuppressLint("CheckResult")
    private void getPersonnels() {
        loadingPersonnel.setValue(true);
        GetActivePersonnelParams params = new GetActivePersonnelParams();
        params.setSWHouseID(warehouseID.getValue());
        personnelRepository.getActivePersonnels(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            loadingPersonnel.setValue(false);

                            if (response.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<ActivePersonnelModel> personnelModels = response.getData();
                            personnelList.setValue(personnelModels);
                        },
                        error -> {
                            loadingPersonnel.setValue(false);
                        }
                );
    }

    public LiveData<Boolean> isLoadingWarehouse(){ return loadingWarehouse; }
    public LiveData<Boolean> isLoadingPersonnel(){ return loadingPersonnel; }
    public LiveData<Boolean> isLoadingCheckpoint(){ return loadingCheckpoint; }
    public LiveData<List<WarehouseEntity>> getWarehouseList() {
        return warehouseRepository.getWarehouseList();
    }
    public LiveData<List<ActivePersonnelModel>> getPersonnelList() { return personnelList; }
    public LiveData<List<NFCDeviceEntity>> getCheckpointList() { return checkpointList; }
    public LiveData<Boolean> sendingRequest() { return sendingRequest; }
    public LiveData<Boolean> hasSentRequest() { return requestSent; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    @SuppressLint("CheckResult")
    public void sendVisitationRequest() {
        sendingRequest.setValue(true);
        RequestSiteVisitParams params = new RequestSiteVisitParams();
        params.setSWHouseID(warehouseID.getValue());
        params.setSNFCIDxxx(checkpointID.getValue());
        params.setSUserIDxx(personnelID.getValue());
        params.setDTimexxxx(scheduleTime.getValue());
        params.setSRemarksx(remarks.getValue());
        requestVisitRepository.sendVisitationRequest(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            sendingRequest.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }
                            requestSent.setValue(true);
                        },
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            sendingRequest.setValue(false);
                        }
                );
    }
}