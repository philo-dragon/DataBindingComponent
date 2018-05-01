package com.pfl.common.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pfl.common.converter.StringConverter;
import com.pfl.common.http.ProxyHandler;
import com.pfl.common.http.RetrofitService;
import com.pfl.common.http.converter.GsonConverterFactory;
import com.pfl.common.utils.BaseUrlManager;
import com.pfl.common.utils.LoggerInterceptor;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class NetworkModule {

    private static final long TIMEOUT = 15;
    private static final long MAX_TRY_COUNT = 3;

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < MAX_TRY_COUNT) {
                    tryCount++;
                    response = chain.proceed(request);
                }
                return response;
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Interceptor netIntercepter) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(netIntercepter)
                .addNetworkInterceptor(new LoggerInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrlManager.getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    @Provides
    @Singleton
    RetrofitService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(RetrofitService.class);
    }

    //网络拦截器：失败重连 3 次
    private Interceptor retryWhenIntercepter = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < MAX_TRY_COUNT) {
                tryCount++;
                response = chain.proceed(request);
            }
            return response;
        }
    };

}