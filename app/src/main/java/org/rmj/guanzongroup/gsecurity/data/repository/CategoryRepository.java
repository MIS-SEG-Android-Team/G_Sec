package org.rmj.guanzongroup.gsecurity.data.repository;

import androidx.lifecycle.LiveData;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryDao;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class CategoryRepository {

    private final ApiService apiService;
    private final CategoryDao categoryDao;

    @Inject
    public CategoryRepository(
            ApiService apiService,
            CategoryDao categoryDao
    ) {
        this.apiService = apiService;
        this.categoryDao = categoryDao;
    }

    public Observable<BaseResponse<Void>> addCategory(AddCategoryParams params) {
        return apiService.addCategory(params);
    }

    public String getLatestTimeStamp() {
        return categoryDao.getLatestTimeStamp();
    }

    public Observable<BaseResponse<List<CategoryEntity>>> getCategories(DateTimeStampParams params) {
        return apiService.getCategories(params);
    }

    public void saveCategories(List<CategoryEntity> value) {
        categoryDao.saveCategories(value);
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categoryDao.getCategories();
    }
}
