package org.rmj.guanzongroup.gsecurity.ui.screens.settings.addpersonnel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.PositionRepository;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMAddPersonnel extends ViewModel {

    private final PersonnelRepository personnelRepository;
    private final PositionRepository positionRepository;

    // Text field handlers
    // Personnel last name
    private final MutableLiveData<String> lastName = new MutableLiveData<>("");
    // Personnel first name
    private final MutableLiveData<String> frstName = new MutableLiveData<>("");
    // Personnel middle name
    private final MutableLiveData<String> middName = new MutableLiveData<>("");
    // Personnel position ID
    private final MutableLiveData<String> position = new MutableLiveData<>("");
    // Personnel position description
    private final MutableLiveData<String> descript = new MutableLiveData<>("");
    // Personnel account level
    private final MutableLiveData<String> userLevl = new MutableLiveData<>("0");

    // Input and Process Result Observers
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> personnelMPIN = new MutableLiveData<>("");

    @Inject
    public VMAddPersonnel(
            PersonnelRepository personnelRepository,
            PositionRepository positionRepository
    ) {
        this.personnelRepository = personnelRepository;
        this.positionRepository = positionRepository;
        checkPositions();
    }

    public void setLastName(String value) {
        lastName.setValue(value);
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                !Objects.requireNonNull(position.getValue()).isEmpty()
        );
    }
    public void setFirstName(String value) {
        frstName.setValue(value);
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(position.getValue()).isEmpty()
        );
    }
    public void setMiddleName(String value) {
        middName.setValue(value);
    }
    public void setPosition(String value) {
        position.setValue(value);
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).isEmpty()
        );
    }
    public void setPositionDescription(String value) {
        descript.setValue(value);
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(position.getValue()).isEmpty()
        );
    }
    public void setUserLevel(String value) {
        userLevl.setValue(value);
    }

    public MutableLiveData<Boolean> hasCompleteInfo() {
        return hasCompleteInfo;
    }
    public MutableLiveData<Boolean> isLoading() {
        return isLoading;
    }
    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public MutableLiveData<String> getPersonnelMPIN() {
        return personnelMPIN;
    }

    public LiveData<List<PositionEntity>> getPositions() {
        return positionRepository.getPositionFromCache();
    }

    @SuppressLint("CheckResult")
    private void checkPositions() {
        DateTimeStampParams params = new DateTimeStampParams();
        String timeStamp = positionRepository.getLatestTimeStamp();
        if (timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }
        positionRepository.getPositions(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        baseResponse -> {

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {

                                return;
                            }

                            List<PositionEntity> positionEntityList = baseResponse.getData();
                            positionRepository.savePositions(positionEntityList);
                        },
                        throwable -> {

                        }
                );
    }

    @SuppressLint("CheckResult")
    public void addPersonnel() {

        // Display loading dialog on UI...
        isLoading.setValue(true);

        AddPersonnelParam param = new AddPersonnelParam();
        param.setsLastName(lastName.getValue());
        param.setsFrstName(frstName.getValue());
        param.setsMiddName(middName.getValue());
        param.setsPositnID(position.getValue());
        param.setsPositnDs(descript.getValue());
        param.setnUserLvel(userLevl.getValue());
        personnelRepository.addPersonnel(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            isLoading.setValue(false);

                            // API Response validation
                            // Any API response with HTTP Error Code 200 is handled in this area...
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            personnelMPIN.setValue(response.getData().getNPINCodex());
                        },

                        // Exception handling...
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            isLoading.setValue(false);
                        }
                );
    }
}