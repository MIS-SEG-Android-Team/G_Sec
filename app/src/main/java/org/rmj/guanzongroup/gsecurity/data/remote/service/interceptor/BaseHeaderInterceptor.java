package org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import org.rmj.guanzongroup.gsecurity.BuildConfig;
import org.rmj.guanzongroup.gsecurity.data.remote.util.SECUtil;
import org.rmj.guanzongroup.gsecurity.data.remote.util.SQLUtil;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

@Singleton
public class BaseHeaderInterceptor implements Interceptor {

    private String firebaseToken;
    private String deviceID;

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    @Inject
    public BaseHeaderInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        String apiKey = SQLUtil.dateFormat(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
        String apiHash = SECUtil.md5Hex(deviceID + apiKey);

        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("g-char-request", BuildConfig.CHAR_REQUEST)
                .addHeader("g-api-imei", deviceID)
                .addHeader("g-api-key", apiKey)
                .addHeader("g-api-hash", apiHash.toLowerCase())
                .addHeader("g-api-mobile", "09260375777")
                .addHeader("g-api-id", BuildConfig.PRODUCT_ID)
                .addHeader("g-api-client", BuildConfig.CLIENT_ID)
                .addHeader("g-api-token", firebaseToken)
                .build();

        return chain.proceed(request);
    }
}
