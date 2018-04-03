/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pfl.common.http;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.pfl.common.entity.base.AccessToken;
import com.pfl.common.entity.base.HttpResponse;
import com.pfl.common.exception.TokenInvalidException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.HttpException;
import retrofit2.http.FieldMap;

/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class ProxyHandler implements InvocationHandler {

    private final static String TOKEN = "access_token";
    private final static int REFRESH_TOKEN_VALID_TIME = 3600;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean mIsTokenNeedRefresh;

    private Object mProxyObject;
    private RetrofitService retrofitService;

    public ProxyHandler(Object proxyObject, RetrofitService service) {
        mProxyObject = proxyObject;
        retrofitService = service;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        return Observable.just("").flatMap(new Function<Object, Observable<?>>() {
            @Override
            public Observable<?> apply(Object o) throws Exception {
                try {
                    try {
                        if (mIsTokenNeedRefresh) {
                            updateMethodToken(method, args);
                        }
                        return (Observable<?>) method.invoke(mProxyObject, args);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {

            @Override
            public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
                return observable.flatMap(new Function<Throwable, Observable<?>>() {

                    @Override
                    public Observable<?> apply(Throwable throwable) throws Exception {
                        if (throwable instanceof HttpException) {
                            HttpException httpException = (HttpException) throwable;
                            int code = httpException.code();
                            if (code == 401) {
                                return refreshTokenWhenTokenInvalid();
                            }
                        } else if (throwable instanceof TokenInvalidException) {
                            return refreshTokenWhenTokenInvalid();
                        }

                        return Observable.error(throwable);
                    }
                });
            }
        });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {
            // Have refreshed the token successfully in the valid time.
            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                mIsTokenNeedRefresh = true;
                return Observable.just(true);
            } else {
                retrofitService
                        .getToken("1", "2", "3")
                        .subscribe(new Consumer<AccessToken>() {
                            @Override
                            public void accept(AccessToken accessTokenHttpResponse) throws Exception {
                                mIsTokenNeedRefresh = true;
                                tokenChangedTime = new Date().getTime();
                                SPUtils.getInstance().put("token", accessTokenHttpResponse.getAccess_token());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mRefreshTokenError = throwable;
                                throwable.printStackTrace();
                            }
                        });
                if (mRefreshTokenError != null) {
                    return Observable.error(mRefreshTokenError);
                } else {
                    return Observable.just(true);
                }
            }
        }
    }

    /**
     * Update the token of the args in the method.
     * PS： 因为这里使用的是 GET 请求，所以这里就需要对 Query 的参数名称为 token 的方法。
     * 若是 POST 请求，或者使用 Body ，自行替换。因为 参数数组已经知道，进行遍历找到相应的值，进行替换即可（更新为新的 token 值）。
     */
    private void updateMethodToken(Method method, Object[] args) {
        if (mIsTokenNeedRefresh && !TextUtils.isEmpty(GlobalToken.getToken())) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof FieldMap) {
                            for (int i1 = 0; i1 < args.length; i1++) {
                                if (args[i1] instanceof Map) {
                                    HashMap<String, String> map = (HashMap<String, String>) args[i1];
                                    map.put(TOKEN, SPUtils.getInstance().getString("token", GlobalToken.getToken()));
                                    args[i1] = map;
                                }
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }
}
