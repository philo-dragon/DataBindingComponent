package com.pfl.common.entity.base;

/**
 * Created by rocky on 2017/12/20.
 */

public class AccessToken {

    private String access_token;//token
    private String expires_in;//有效时长

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
