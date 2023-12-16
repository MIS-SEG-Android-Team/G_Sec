package org.rmj.guanzongroup.gsecurity.ui.screens.settings.position;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPositionParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPositionParams;
import org.rmj.guanzongroup.gsecurity.data.repository.PositionRepository;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMPosition extends ViewModel {

    private final PositionRepository repository;

    private final MutableLiveData<Boolean> finishImporting = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);
    private final MutableLiveData<String> position = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> savingPosition = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> successfullySave = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public VMPosition(
            PositionRepository repository
    ) {
        this.repository = repository;
        importPositions();
    }

    public LiveData<Boolean> finishImporting() {
        return finishImporting;
    }

    public LiveData<Boolean> hasCompleteInfo() {
        return hasCompleteInfo;
    }

    public LiveData<Boolean> savingPosition() {
        return savingPosition;
    }

    public LiveData<Boolean> successfullySave() {
        return successfullySave;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setPosition(String value) {
        position.setValue(value);
        hasCompleteInfo.setValue(!value.trim().isEmpty());
    }

    public LiveData<List<PositionEntity>> getPositions() {
        return repository.getPositionFromLocal();
    }

    @SuppressLint("CheckResult")
    public void addPosition() {
        savingPosition.setValue(true);
        AddPositionParams params = new AddPositionParams();
        params.setSPositnNm(position.getValue());
        repository.addPosition(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            savingPosition.setValue(false);
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {

                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            successfullySave.setValue(true);
                        },
                        throwable -> {
                            savingPosition.setValue(false);
                            errorMessage.setValue(throwable.getMessage());
                        }
                );
    }

    private void importPositions() {
        String timeStamp = repository.getPositionLatestTimeStamp();
        if(timeStamp == null || timeStamp.isEmpty()) {
            importRoles();
        } else {
            importUpdatedRoles(timeStamp);
        }
    }

    @SuppressLint("CheckResult")
    private void importUpdatedRoles(String timeStamp) {
        finishImporting.setValue(false);

        GetPositionParams params = new GetPositionParams();
        params.setTimestamp(timeStamp);
        repository.getUpdatedPositions(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        // All 200 HttpCode will be handled here whether success, failed or error....
                        baseResponse -> {

                            // Check if the API response is error...
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<PositionEntity> positionEntityList = baseResponse.getData();
                            repository.savePositions(positionEntityList);

                            finishImporting.setValue(true);
                        },

                        // All non 200 HttpCode will be handled here.
                        // HttpCode response that's not 200 is considered error...
                        // Exceptions handler...
                        throwable -> {
                            finishImporting.setValue(true);
                        }
                );
    }

    @SuppressLint("CheckResult")
    private void importRoles() {
        finishImporting.setValue(false);
        repository.getPositions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        // All 200 HttpCode will be handled here whether success, failed or error....
                        baseResponse -> {

                            // Check if the API response is error...
                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<PositionEntity> positionEntityList = baseResponse.getData();
                            repository.savePositions(positionEntityList);

                            finishImporting.setValue(true);
                        },

                        // All non 200 HttpCode will be handled here.
                        // HttpCode response that's not 200 is considered error...
                        // Exceptions handler...
                        throwable -> {
                            finishImporting.setValue(true);
                        }
                );
    }
}