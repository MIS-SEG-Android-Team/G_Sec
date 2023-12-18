package org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor;

import androidx.annotation.NonNull;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.util.SECUtil;
import org.rmj.guanzongroup.gsecurity.data.remote.util.SQLUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class AuthorizedInterceptor implements Interceptor {

    private final String userID;
    private final String logNo;

    @Inject
    public AuthorizedInterceptor(DataStore dataStore) {
        userID = dataStore.getUserId();
        logNo = dataStore.getLogNumber();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        request = request.newBuilder()
                .addHeader("g-api-user", userID)
                .addHeader("g-api-log", logNo)
                .build();

        return chain.proceed(request);
    }
}
