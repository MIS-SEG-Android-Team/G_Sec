package org.rmj.guanzongroup.gsecurity.ui.screens.settings.category;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.repository.CategoryRepository;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMCategory extends ViewModel {

    private final CategoryRepository categoryRepository;

    private final MutableLiveData<Boolean> importingCategories = new MutableLiveData<>(false);
    private final MutableLiveData<String> category = new MutableLiveData<>("");
    private final MutableLiveData<String> description = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> hasCompleteInfo = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> savingCategory = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> categorySave = new MutableLiveData<>(false);

    @Inject
    public VMCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        importCategories();
    }

    public LiveData<Boolean> importingCategories() {
        return importingCategories;
    }

    public LiveData<Boolean> savingCategory() {
        return savingCategory;
    }

    public void setCategory(String value) {
        category.setValue(value);
        hasCompleteInfo.setValue(!value.trim().isEmpty() && !Objects.requireNonNull(description.getValue()).trim().isEmpty());
    }

    public void setDescription(String value) {
        description.setValue(value);
        hasCompleteInfo.setValue(!value.trim().isEmpty() && !Objects.requireNonNull(category.getValue()).trim().isEmpty());
    }

    public LiveData<Boolean> hasCompleteInfo() {
        return hasCompleteInfo;
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categoryRepository.getCategories();
    }

    public LiveData<Boolean> categorySave() {
        return categorySave;
    }

    public void resetAddCategory() {
        categorySave.setValue(false);
    }

    @SuppressLint("CheckResult")
    public void addCategory() {
        savingCategory.setValue(true);
        AddCategoryParams params = new AddCategoryParams();
        params.setSCategory(category.getValue());
        params.setSDescript(description.getValue());
        categoryRepository.addCategory(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        baseResponse -> {

                            if (baseResponse.getResult().equalsIgnoreCase("error")) {
                                savingCategory.setValue(false);
                                errorMessage.setValue(baseResponse.getError().getMessage());
                                return;
                            }

                            categorySave.setValue(true);
                            savingCategory.setValue(false);
                        },

                        throwable -> {
                            savingCategory.setValue(false);
                            errorMessage.setValue(throwable.getMessage());
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void importCategories() {
        importingCategories.setValue(true);
        DateTimeStampParams params = new DateTimeStampParams();
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
                                importingCategories.setValue(false);
                               return;
                            }

                            List<CategoryEntity> categoryEntityList = baseResponse.getData();
                            categoryRepository.saveCategories(categoryEntityList);
                            importingCategories.setValue(false);

                        },
                        throwable -> {
                            importingCategories.setValue(false);
                        }
                );
    }
}