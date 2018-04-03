package com.pfl.common.utils;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 日志拦截器 打印日志
 * author PFL
 * time 2017/3/10 12:17
 */

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        Request request = chain.request();
        String method = request.method();


        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            sb.append(chain.request().url() + " , ");

            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + " , ");
                }
                sb.delete(sb.length() - 1, sb.length());
            }
            LogUtils.d(sb.toString());
        }

        LogUtils.d(content);

        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
