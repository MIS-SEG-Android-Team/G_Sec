package org.rmj.guanzongroup.gsecurity.data.remote.service;

import org.rmj.guanzongroup.gsecurity.data.remote.param.AddCategoryParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddNfcTagParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPersonnelParam;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddPositionParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.AddWarehouseParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.DeactivatePersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetActivePersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetBranchParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetNFCTagsParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetPersonnelPatrolReportParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.GetRecentActivityParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.LoginParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PINParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.PostPatrolParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.RequestSiteVisitParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.timestamp.DateTimeStampParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolroute.UpdatePatrolRouteParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolschedule.UpdatePatrolScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepersonnel.UpdatePatrolPersonnelParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.AddPersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.authentication.LoginBaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.base.BaseResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.branch.BranchResponse;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrol.PatrolRouteModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport.PersonnelPatrolReport;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
import org.rmj.guanzongroup.gsecurity.data.remote.response.recentactivity.RecentActivityModel;
import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchEntity;
import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;
import org.rmj.guanzongroup.gsecurity.data.room.request.RequestVisitEntity;
import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
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

    @POST("/security/glogin.php")
    Observable<LoginBaseResponse> loginAdmin(@Body LoginParams params);

    @POST("/gsecure/auth/logout.php")
    Observable<BaseResponse<Void>> logout();

    @POST("/security/logout.php")
    Observable<BaseResponse<Void>> logoutAdmin();

    // endregion




    // region Personnel

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_personnel.php")
    Observable<BaseResponse<AddPersonnelModel>> addPersonnel(@Body AddPersonnelParam value);

    @POST("/gsecure/personnel/get_personnels.php")
    Observable<BaseResponse<List<PersonnelModel>>> getPersonnels(@Body DateTimeStampParams value);

    @POST("/gsecure/personnel/get_active_personnel.php")
    Observable<BaseResponse<List<ActivePersonnelModel>>> getActivePersonnels(@Body GetActivePersonnelParams value);

    @POST("/gsecure/personnel/deactivate_personnel.php")
    Observable<BaseResponse<Void>> deactivatePersonnelAccount(@Body DeactivatePersonnelParams value);

    // endregion




    // region Position

    /**
     * =============================================================================================
     * Personnel
     * =============================================================================================
     */

    @POST("/gsecure/personnel/add_position.php")
    Observable<BaseResponse<Void>> addPosition(@Body AddPositionParams value);

    @POST("/gsecure/personnel/get_position.php")
    Observable<BaseResponse<List<PositionEntity>>> getPositions(@Body DateTimeStampParams params);

    // endregion




    // region Warehouse

    /**
     * =============================================================================================
     * Warehouse
     * =============================================================================================
     */

    @POST("/gsecure/place/add_warehouse.php")
    Observable<BaseResponse<Void>> addWarehouse(@Body AddWarehouseParams params);

    @POST("/gsecure/place/get_warehouse.php")
    Observable<BaseResponse<List<WarehouseEntity>>> getWarehouses(@Body DateTimeStampParams params);

    @POST("/integsys/param/download_branch.php")
    Observable<BranchResponse<List<BranchEntity>>> getBranches(@Body GetBranchParams params);

    // endregion




    // region Category

    /**
     * =============================================================================================
     * Category
     * =============================================================================================
     */

    @POST("/gsecure/place/add_category.php")
    Observable<BaseResponse<Void>> addCategory(@Body AddCategoryParams params);

    @POST("/gsecure/place/get_category.php")
    Observable<BaseResponse<List<CategoryEntity>>> getCategories(@Body DateTimeStampParams params);


    // endregion




    // region NFC

    /**
     * =============================================================================================
     * NFC Tags or Place
     * =============================================================================================
     */

    @POST("/gsecure/place/nfc_add_tag.php")
    Observable<BaseResponse<Void>> addNFCTag(@Body AddNfcTagParams params);

    @POST("/gsecure/place/get_nfc_tags.php")
    Observable<BaseResponse<List<NFCDeviceEntity>>> getNFCTags(@Body GetNFCTagsParams params);

    // endregion

    // region Scheduler

    @POST("/gsecure/patrol/create_schedule.php")
    Observable<BaseResponse<Void>> addNewSchedule(@Body CreateScheduleParams params);

    @POST("/gsecure/patrol/update_patrol_schedule.php")
    Observable<BaseResponse<Void>> updateSchedule(@Body UpdatePatrolScheduleParams params);

    @POST("/gsecure/patrol/update_patrol_route.php")
    Observable<BaseResponse<Void>> updatePatrolRoute(@Body UpdatePatrolRouteParams params);

    @POST("/gsecure/patrol/update_patrol_personnel.php")
    Observable<BaseResponse<Void>> updatePatrolPersonnel(@Body UpdatePatrolPersonnelParams params);

    // endregion

    // region Patrol
    // Use this API call only if the user is not Admin account,
    // this is only intended to automatically save data on cache/database
    @POST("/gsecure/patrol/get_patrol_route.php")
    Observable<BaseResponse<List<PatrolRouteModel>>> getPatrolRoute(@Body GetPatrolRouteParams params);

    @POST("/gsecure/patrol/get_recent_activity.php")
    Observable<BaseResponse<List<RecentActivityModel>>> getRecentActivity(@Body GetRecentActivityParams params);

    @POST("/gsecure/patrol/post_place_visited.php")
    Observable<BaseResponse<Void>> postPlaceVisited(@Body PostPatrolParams params);

    // This API call is only use for updating personnel patrol route and schedules...
    @POST("/gsecure/patrol/get_patrol_route.php")
    Observable<BaseResponse<List<PersonnelPatrolModel>>> getPatrolRouteForUpdate(@Body GetPatrolRouteParams params);

    // endregion

    // region Notification

    @POST("/gsecure/patrol/send_visit_request.php")
    Observable<BaseResponse<Void>> sendVisitationRequest(@Body RequestSiteVisitParams params);

    @POST("/gsecure/notification/send_request.php")
    Observable<BaseResponse<Void>> sendVisitedNotification(@Body RequestVisitEntity params);

    // endregion

    // region Reports

    @POST("/gsecure/reports/get_personnel_report.php")
    Observable<BaseResponse<List<PersonnelPatrolReport>>> getPersonnelPatrolReport(@Body GetPersonnelPatrolReportParams params);

}
