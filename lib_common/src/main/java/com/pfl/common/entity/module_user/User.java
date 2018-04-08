package com.pfl.common.entity.module_user;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.pfl.common.BR;
import com.pfl.common.entity.base.BaseEntyty;


/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class User extends BaseEntyty {

    private String account = "";
    private String password = "";

    private String confirmPassword = "";
    private String checkCode = "";

    @Bindable
    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyPropertyChanged(BR.confirmPassword);
    }

    @Bindable
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
        notifyPropertyChanged(BR.checkCode);
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Bindable
    public String getCheckCode() {
        return checkCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", checkCode='" + checkCode + '\'' +
                '}';
    }
}
