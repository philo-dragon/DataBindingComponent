/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pfl.common.http.converter;

import com.google.gson.TypeAdapter;
import com.pfl.common.entity.base.HttpResponse;
import com.pfl.common.exception.ApiException;
import com.pfl.common.exception.ErrorCode;
import com.pfl.common.exception.TokenInvalidException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            HttpResponse apiModel = (HttpResponse) adapter.fromJson(value.charStream());
            if (apiModel.getCode() == ErrorCode.TOKEN_NOT_EXIST || apiModel.getCode() == ErrorCode.TOKEN_INVALID) {
                throw new TokenInvalidException();
            } else if (!apiModel.isOk()) {
                // 特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
                throw new ApiException(Integer.valueOf(apiModel.getCode()), apiModel.getMsg());
            } else if (apiModel.isOk()) {
                return apiModel.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }
}
