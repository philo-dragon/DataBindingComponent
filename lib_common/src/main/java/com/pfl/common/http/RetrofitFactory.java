package com.pfl.common.http;

import com.pfl.common.http.converter.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitFactory {

    private static RetrofitFactory INSTANCE;
    private static final long MAX_TRY_COUNT = 3;

    private RetrofitFactory() {
    }

    public static RetrofitFactory getInstance() {
        if (null == INSTANCE) {
            synchronized (RetrofitFactory.class) {
                if (null == INSTANCE) {
                    INSTANCE = new RetrofitFactory();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * @param tClass         请求数据类型接口Service
     * @param currentService 请求数据真实接口Service
     * @param tokenService   请求Token接口 Service
     * @param <T>            返回代理请求数据类型接口
     * @return
     */
    public <T> T getProxy(Class<T> tClass, Object currentService, RetrofitService tokenService) {
        T t = (T) currentService;
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new ProxyHandler(t, tokenService));
    }

}