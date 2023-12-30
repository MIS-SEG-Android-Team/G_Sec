package org.rmj.guanzongroup.gsecurity.ui.screens.settings.nfc;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.repository.CategoryRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.CheckpointRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class VMAddCheckpoint extends ViewModel {

    private final WarehouseRepository warehouseRepository;
    private final CategoryRepository categoryRepository;
    private final CheckpointRepository checkpointRepository;

    private final MutableLiveData<String> warehouse = new MutableLiveData<>("");
    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<String> description = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> addingCheckpoint = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> checkpointAdded = new MutableLiveData<>(false);

    @Inject
    public VMAddCheckpoint(
            CategoryRepository categoryRepository,
            WarehouseRepository warehouseRepository,
            CheckpointRepository checkpointRepository
    ) {
        this.warehouseRepository = warehouseRepository;
        this.categoryRepository = categoryRepository;
        this.checkpointRepository = checkpointRepository;

        importCategories(); // import to update the category list on local

        try {

            // Consecutive Http Request to server causes errors such as
            // ({"result":"error","error":{"code":0,"message":"Reload failed...\n"}})
            // Added 1 second delay in order to prevent this error from occurring
            Thread.sleep(1000);
            Timber.d("1 second suspension...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        importWarehouse(); // import to update the category list on local
    }

    public void setWarehouse(String value) {
        warehouse.setValue(value);
        hasCompleteInfo.setValue(!value.isEmpty() &&
                !Objects.requireNonNull(category.getValue()).isEmpty() &&
                !Objects.requireNonNull(description.getValue()).isEmpty());
    }

    public void setCategory(String value) {
        category.setValue(value);
        hasCompleteInfo.setValue(!value.isEmpty() &&
                !Objects.requireNonNull(warehouse.getValue()).isEmpty() &&
                !Objects.requireNonNull(description.getValue()).isEmpty());
    }

    public void setDescription(String value) {
        description.setValue(value);
        hasCompleteInfo.setValue(!value.isEmpty() &&
                !Objects.requireNonNull(description.getValue()).isEmpty() &&
                !Objects.requireNonNull(category.getValue()).isEmpty());
    }

    public LiveData<Boolean> hasCompleteInfo() {
        return hasCompleteInfo;
    }

    public String getWarehouseID() {
        return warehouse.getValue();
    }

    public String getCategoryID() {
        return category.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categoryRepository.getCategories();
    }

    public LiveData<List<WarehouseEntity>> getWarehouses() {
        return warehouseRepository.getWarehouseList();
    }

    public LiveData<Boolean> addingCheckpoint() {
        return addingCheckpoint;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> checkpointAdded() {
        return checkpointAdded;
    }

    public void addCheckpoint() {
        AddNfcTagParams params = new AddNfcTagParams();
        params.setSWHouseID(warehouse.getValue());
        params.setSCatIDxxx(category.getValue());
        params.setSDescript(description.getValue());
        params.setCRecdStat("1");
    }


    @SuppressLint("CheckResult")
    public void importCategories() {
        GetCategoryParams params = new GetCategoryParams();
        String timeStamp = categoryRepository.getLatestTimeStamp();
        if (timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        categoryRepository.getCategories(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<CategoryEntity> categoryEntityList = baseResponse.getData();
                            categoryRepository.saveCategories(categoryEntityList);

                        },
                        throwable -> {

                        }
                );
    }

    @SuppressLint("CheckResult")
    private void importWarehouse() {
        GetWarehouseParams params = new GetWarehouseParams();
        String timeStamp = warehouseRepository.getLatestTimeStamp();

        if(timeStamp != null) {
            params.setDTimeStmp(timeStamp);
        }

        warehouseRepository.getWarehouse(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baseResponse -> {

                            if(baseResponse.getResult().equalsIgnoreCase("error")) {
                                return;
                            }

                            List<WarehouseEntity> warehouseEntityList = baseResponse.getData();
                            warehouseRepository.saveWarehouses(warehouseEntityList);
                        },
                        throwable -> {

                        }
                );
    }
}