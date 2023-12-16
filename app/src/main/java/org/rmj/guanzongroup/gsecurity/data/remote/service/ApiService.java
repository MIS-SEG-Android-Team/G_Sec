package org.rmj.guanzongroup.gsecurity.data.remote.service;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPositionParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetBranchParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPositionParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.AddPatrolScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.CategoryModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.WarehouseModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.authentication.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.branch.BranchResponse;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // region Authentication

    /**
     * =============================================================================================
     * Authentication
     * =============================================================================================
     */

    @POST("/security/signup.php")
    Observable<BaseResponse<Void>> createAccount(String value);

    @POST("/gsecure/auth/login_mpin.php")
    Observable<LoginBaseResponse> loginPersonnel(@Body PINParams mpin);

    @POST("/security/mlogin.php")
    Observable<LoginBaseResponse> loginAdmin(@Body LoginParams params);

    @POST("/gsecure/auth/logout.php")
    Observable<BaseResponse<Void>> logout();

    // endregion




    // region Personnel

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_personnel.php")
    Observable<BaseResponse<Void>> addPersonnel(@Body PersonnelParam value);

    @GET("/gsecure/personnel/add_position.php")
    Observable<BaseResponse<List<PersonnelModel>>> getPersonnels();

    // endregion




    // region Position

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_position.php")
    Observable<BaseResponse<Void>> addPosition(@Body AddPositionParams value);

    @GET("/gsecure/personnel/get_position.php")
    Observable<BaseResponse<List<PositionEntity>>> getPositions(@Body GetPositionParams params);

    // endregion




    // region Warehouse

    /**
     * =============================================================================================
     * Warehouse
     * =============================================================================================
     */

    @POST("/gsecure/places/add_warehouse.php")
    Observable<BaseResponse<Void>> addWarehouse(@Body AddWarehouseParams params);

    @GET("/gsecure/places/get_warehouse.php")
    Observable<BaseResponse<List<WarehouseModel>>> getWarehouses();

    @POST("/gsecure/places/get_warehouse.php")
    Observable<BaseResponse<List<WarehouseModel>>> getUpdatedWarehouses(@Body GetWarehouseParams params);

    @POST("/integsys/param/download_branch.php")
    Observable<BranchResponse<List<BranchEntity>>> getBranches(@Body GetBranchParams params);

    // endregion




    // region Category

    /**
     * =============================================================================================
     * Category
     * =============================================================================================
     */

    @POST("/gsecure/places/add_category.php")
    Observable<BaseResponse<Void>> addCategory(@Body AddCategoryParams params);

    @GET("/gsecure/places/get_category.php")
    Observable<BaseResponse<List<CategoryModel>>> getCategories();

    @GET("/gsecure/places/get_category.php")
    Observable<BaseResponse<List<CategoryModel>>> getUpdateCategories(@Body GetCategoryParams params);

    // endregion




    // region NFC

    /**
     * =============================================================================================
     * NFC Tags or Places
     * =============================================================================================
     */

    @POST("/gsecure/places/nfc_add_tag.php")
    Observable<BaseResponse<Void>> addNFCTag(@Body AddNfcTagParams params);

    @GET("")
    List<String> getNFCTags();

    // endregion

    // region Scheduler

    @POST("patrol/create_schedule")
    Observable<BaseResponse<Void>> addNewSchedule(@Body AddPatrolScheduleParams params);
}
