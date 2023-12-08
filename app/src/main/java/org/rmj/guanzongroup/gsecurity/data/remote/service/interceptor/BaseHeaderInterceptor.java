package org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor;

import androidx.annotation.NonNull;

import org.rmj.guanzongroup.gsecurity.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
public class BaseHeaderInterceptor implements Interceptor {

    private String deviceID = "";


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("g-api-id", BuildConfig.PRODUCT_ID)
                .addHeader("g-api-client", BuildConfig.CLIENT_ID)
                .addHeader("g-char-request", BuildConfig.CHAR_REQUEST)
                .build();

        return chain.proceed(request);
    }
}
