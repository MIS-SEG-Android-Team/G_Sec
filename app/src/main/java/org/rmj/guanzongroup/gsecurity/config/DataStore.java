package org.rmj.guanzongroup.gsecurity.config;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataStore {


    private final SharedPreferences preferences;

    private final SharedPreferences.Editor editor;

    private static final String DATA_STORE_NAME = "GSecure_Data_Store";

    private static final String CLIENT_ID = "client_id";
    private static final String BRANCH_CODE = "branch_code";
    private static final String BRANCH_NAME = "branch_name";
    private static final String LOG_NUMBER = "log_number";
    private static final String USER_ID = "user_id";
    private static final String EMAIL_ADD = "email_address";
    private static final String USER_NAME = "user_name";
    private static final String USER_LEVEL = "user_level";
    private static final String DEPARTMENT_ID = "department_id";
    private static final String POSITION_ID = "position_id";
    private static final String EMPLOYEE_LEVEL = "employee_level_id";
    private static final String EMPLOYEE_ID = "employee_id";
    private static final String MAIN_OFFICE = "is_main_office";
    private static final String SELFIE_LOG_ALLOWED = "selfie_log_allowed";
    private static final String ALLOWED_UPDATE = "allowed_update";

    @Inject
    public DataStore(Application application) {
        this.preferences = application.getSharedPreferences(DATA_STORE_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
    }

    public String getClientId() {
        return preferences.getString(CLIENT_ID, "");
    }

    public void setClientId(String value) {
        editor.putString(CLIENT_ID, value);
        editor.commit();
    }

    public String getBranchCode() {
        return BRANCH_CODE;
    }

    public void setBranchCode(String value) {
        editor.putString(BRANCH_CODE, value);
        editor.commit();
    }

    public String getBranchName() {
        return preferences.getString(BRANCH_NAME, "");
    }

    public void setBranchName(String value) {
        editor.putString(BRANCH_NAME, value);
    }

    public String getLogNumber() {
        return preferences.getString(LOG_NUMBER, "");
    }

    public void setLogNumber(String value) {
        editor.putString(LOG_NUMBER, value);
        editor.commit();
    }

    public String getUserId() {
        return preferences.getString(USER_ID, "");
    }

    public void setUserId(String value) {
        editor.putString(USER_ID, value);
        editor.commit();
    }

    public String getEmailAdd() {
        return preferences.getString(EMAIL_ADD, "");
    }

    public void setEmailAdd(String value) {
        editor.putString(EMAIL_ADD, value);
        editor.commit();
    }

    public String getUserName() {
        return preferences.getString(USER_NAME, "");
    }

    public void setUserName(String value) {
        editor.putString(USER_NAME, value);
        editor.commit();
    }

    public String getUserLevel() {
        return preferences.getString(USER_LEVEL, "");
    }

    public void setUserLevel(String value) {
        editor.putString(USER_LEVEL, value);
        editor.commit();
    }

    public String getDepartmentId() {
        return preferences.getString(DEPARTMENT_ID, "");
    }

    public void setDepartmentId(String value) {
        editor.putString(DEPARTMENT_ID, value);
        editor.commit();
    }

    public String getPositionId() {
        return preferences.getString(POSITION_ID, "");
    }

    public void setPositionId(String value) {
        editor.putString(POSITION_ID, value);
        editor.commit();
    }

    public String getEmployeeLevel() {
        return preferences.getString(EMPLOYEE_LEVEL, "");
    }

    public void setEmployeeLevel(String value) {
        editor.putString(EMPLOYEE_LEVEL, value);
        editor.commit();
    }

    public String getEmployeeId() {
        return preferences.getString(EMPLOYEE_ID, "");
    }

    public void setEmployeeId(String value) {
        editor.putString(EMPLOYEE_ID, value);
        editor.commit();
    }

    public String getMainOffice() {
        return preferences.getString(MAIN_OFFICE, "");
    }

    public void setMainOffice(String value) {
        editor.putString(MAIN_OFFICE, value);
    }

    public String getSelfieLogAllowed() {
        return preferences.getString(SELFIE_LOG_ALLOWED, "");
    }

    public void setSelfieLogAllowed(String value) {
        editor.putString(SELFIE_LOG_ALLOWED, value);
        editor.commit();
    }

    public String getAllowedUpdate() {
        return preferences.getString(ALLOWED_UPDATE, "");
    }

    public void setAllowedUpdate(String value) {
        editor.putString(ALLOWED_UPDATE, value);
        editor.commit();
    }

    public void clearDataStore() {
        editor.clear();
        editor.commit();
    }
}
