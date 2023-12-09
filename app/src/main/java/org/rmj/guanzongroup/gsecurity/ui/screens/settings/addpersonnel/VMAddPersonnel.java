package org.rmj.guanzongroup.gsecurity.ui.screens.settings.addpersonnel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.rmj.guanzongroup.gsecurity.data.remote.param.PersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.repository.authentication.PersonnelRepository;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMAddPersonnel extends ViewModel {

    private final PersonnelRepository repository;

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
    private final MutableLiveData<Boolean> isPersonnelAdded = new MutableLiveData<>(false);

    @Inject
    public VMAddPersonnel(
            PersonnelRepository personnelRepository
    ) {
        this.repository = personnelRepository;
    }

    public void setLastName(String value) {
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                !Objects.requireNonNull(position.getValue()).isEmpty()
        );
        lastName.setValue(value);
    }
    public void setFirstName(String value) {
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(position.getValue()).isEmpty()
        );
        frstName.setValue(value);
    }
    public void setMiddleName(String value) {
        middName.setValue(value);
    }
    public void setPosition(String value) {
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(descript.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).isEmpty()
        );
        position.setValue(value);
    }
    public void setPositionDescription(String value) {
        hasCompleteInfo.setValue(
                !value.trim().isEmpty() &&
                        !Objects.requireNonNull(frstName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(lastName.getValue()).trim().isEmpty() &&
                        !Objects.requireNonNull(position.getValue()).isEmpty()
        );
        descript.setValue(value);
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
    public MutableLiveData<Boolean> isPersonnelAdded() {
        return isPersonnelAdded;
    }
    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @SuppressLint("CheckResult")
    public void addPersonnel() {

        // Display loading dialog on UI...
        isLoading.setValue(true);

        PersonnelParam param = new PersonnelParam();
        param.setsLastName(lastName.getValue());
        param.setsFrstName(frstName.getValue());
        param.setsMiddName(middName.getValue());
        param.setsPositnID(position.getValue());
        param.setsPositnDs(descript.getValue());
        param.setnUserLvel(userLevl.getValue());
        repository.addPersonnel(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            // API Response validation
                            // Any API response with HTTP Error Code 200 is handled in this area...
                            if (baseResponse.getResult().equalsIgnoreCase("error")) {

                                isLoading.setValue(false);
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            isLoading.setValue(false);
                            isPersonnelAdded.setValue(true);

                        },

                        // Exception handling...
                        throwable -> {
                            errorMessage.setValue(throwable.getMessage());
                            isLoading.setValue(false);
                        }
                );
    }
}