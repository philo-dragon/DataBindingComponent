package com.pfl.common.http;

import com.pfl.common.entity.base.AccessToken;
import com.pfl.common.entity.base.HttpResponse;
import com.pfl.common.entity.module_user.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    /**
     * 获取请求令牌
     *
     * @param grant_type
     * @param client_id
     * @param client_secret
     * @return
     */
    @FormUrlEncoded
    @POST("user/token")
    Observable<AccessToken> getToken(@Field("grant_type") String grant_type,
                                                   @Field("client_id") String client_id,
                                                   @Field("client_secret") String client_secret
    );

}
