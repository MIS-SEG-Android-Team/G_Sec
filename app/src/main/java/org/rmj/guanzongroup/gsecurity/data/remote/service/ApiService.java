package org.rmj.guanzongroup.gsecurity.data.remote.service;

import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.login.LoginBaseResponse;

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

    @POST("")
    void createAccount(String value);

    @POST("/gsecure/auth/login_mpin.php")
    Observable<LoginBaseResponse> loginPersonnel(@Body PINParams mpin);

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
    void addPersonne(String value);

    @GET("/gsecure/personnel/add_position.php")
    List<String> getPersonnels();

    // endregion




    // region Position

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_position.php")
    void addPosition(String value);

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
    void addWarehouse(String warehouse);
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
    void addCategory(String value);

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
    void addNFCTag(String value);

    @GET("")
    List<String> getNFCTags();

    // endregion

}
