package org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizedInterceptor implements Interceptor {

    /**
     * This disabled code has is basis for the Http request headers...
     * Must be deleted later on...
     */
//    public static HashMap getHeader1(){
//        String clientid = "GGC_BM001";
//        String productid = "gRider";
//        String imei = "GMC_SEG09";
//        String user = "GAP0190004";
//        String log = "GAP023146450";
//        String token = "12312312";
//
//        Calendar calendar = Calendar.getInstance();
//        Map<String, String> headers =
//                new HashMap<String, String>();
//        headers.put("Accept", "application/json");
//        headers.put("Content-Type", "application/json");
//        headers.put("g-api-id", productid);
//        headers.put("g-api-imei", imei);
//
//        headers.put("g-api-key", SQLUtil.dateFormat(calendar.getTime(), "yyyyMMddHHmmss"));
//        headers.put("g-api-hash", org.apache.commons.codec.digest.DigestUtils.md5Hex((String)headers.get("g-api-imei") + (String)headers.get("g-api-key")));
//        headers.put("g-api-client", clientid);
//        headers.put("g-api-user", user);
//        headers.put("g-api-log", log);
//        headers.put("g-api-mobile", "09260375777");
//        headers.put("g-char-request", "UTF-8");
//        headers.put("g-api-token", token);
//
//        return (HashMap) headers;
//    }

    private String firebaseToken = "";
    private String deviceID = "";
    private String userID = "";
    private String logNo = "";
    private String apiKey = "";
    private String apiHash = "";

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiHash() {
        return apiHash;
    }

    public void setApiHash(String apiHash) {
        this.apiHash = apiHash;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .addHeader("g-api-imei", deviceID)
                .addHeader("g-api-key", apiKey)
                .addHeader("g-api-hash", apiHash)
                .addHeader("g-api-user", userID)
                .addHeader("g-api-log", logNo)
                .addHeader("g-api-mobile", "09260375777")
                .build();

        return chain.proceed(request);
    }
}
