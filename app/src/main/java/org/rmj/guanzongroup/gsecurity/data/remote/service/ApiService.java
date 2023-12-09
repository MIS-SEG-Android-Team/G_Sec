package org.rmj.guanzongroup.gsecurity.data.remote.service;

import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.response.authentication.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnel.PersonnelModel;

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
    Observable<LoginBaseResponse> loginPersonnel(
            @Body PINParams mpin
    );

    @POST("/security/mlogin.php")
    Observable<LoginBaseResponse> loginAdmin(
            @Body LoginParams params
    );

    @POST("/gsecure/auth/logout.php")
    Observable<LoginBaseResponse> logout();

    // endregion




    // region Personnel

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_personnel.php")
    Observable<BaseResponse<Void>> addPersonnel(
            @Body PersonnelParam value
    );

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
    Observable<BaseResponse<Void>> addPosition(
            @Body String value
    );

    @GET("/gsecure/personnel/get_position.php")
    List<String> getPositions();

    // endregion




    // region Warehouse

    /**
     * =============================================================================================
     * Warehouse
     * =============================================================================================
     */

    @POST("/gsecure/places/add_warehouse.php")
    Observable<BaseResponse<Void>> addWarehouse(
            @Body String warehouse
    );

    @GET("/gsecure/places/get_warehouse.php")
    List<String> getWarehouses();

    // endregion




    // region Category

    /**
     * =============================================================================================
     * Category
     * =============================================================================================
     */

    @POST("/gsecure/places/add_category.php")
    Observable<BaseResponse<Void>> addCategory(
            @Body String value
    );

    @GET("/gsecure/places/get_category.php")
    List<String> getCategories();

    // endregion




    // region NFC

    /**
     * =============================================================================================
     * NFC Tags or Places
     * =============================================================================================
     */

    @POST("/gsecure/places/nfc_add_tag.php")
    Observable<BaseResponse<Void>> addNFCTag(
            @Body String value
    );

    @GET("")
    List<String> getNFCTags();

    // endregion

}
