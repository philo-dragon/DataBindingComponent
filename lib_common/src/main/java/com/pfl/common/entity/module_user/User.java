package com.pfl.common.entity.module_user;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.pfl.common.BR;
import com.pfl.common.entity.base.BaseEntyty;


/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class User extends BaseEntyty {

    private String name;
    private String nickName;
    private String type;
    private String photo;

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getType() {
        return type;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", type='" + type + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
