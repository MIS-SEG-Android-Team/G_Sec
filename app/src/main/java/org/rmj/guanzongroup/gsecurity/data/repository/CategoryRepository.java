package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetCategoryParams;
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

    public Observable<BaseResponse<List<CategoryEntity>>> getCategories(GetCategoryParams params) {
        return apiService.getCategories(params);
    }
}
