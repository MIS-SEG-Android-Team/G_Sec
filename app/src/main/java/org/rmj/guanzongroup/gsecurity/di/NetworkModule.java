package org.rmj.guanzongroup.gsecurity.di;

import android.app.Application;

import org.rmj.guanzongroup.gsecurity.BuildConfig;
import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.AuthorizedInterceptor;
import org.rmj.guanzongroup.gsecurity.data.remote.service.interceptor.BaseHeaderInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {


    @Singleton
    @Provides
    public Cache provideHttpCache(Application application) {
        long cacheSize = 10 * 1024 * 1024L;
        return new Cache(application.getCacheDir(), cacheSize);
    }
    @Singleton
    @Provides
    public static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        if(BuildConfig.DEBUG) {
            return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        // else if Build config is production no logcat must be printed...
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
    }

    @Singleton
    @Provides
    public static AuthorizedInterceptor provideAuthorizedInterceptor() {
        return new AuthorizedInterceptor();
    }

    @Singleton
    @Provides
    public static OkHttpClient provideOkHttpClient(
            Cache cache,
            HttpLoggingInterceptor httpLoggingInterceptor,
            BaseHeaderInterceptor baseHeaderInterceptor,
            AuthorizedInterceptor authorizationInterceptor
    ) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(baseHeaderInterceptor)
                .addNetworkInterceptor(authorizationInterceptor)
                .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY));

        return client.build();
    }

    @Singleton
    @Provides
    public static ApiService provideAPIService(
            Retrofit retrofit
    ) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public static Retrofit provideRetrofit(
            OkHttpClient okHttpClient
    ) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public static BaseHeaderInterceptor provideBaseHeaderInterceptor() {
        return new BaseHeaderInterceptor();
    }
}
