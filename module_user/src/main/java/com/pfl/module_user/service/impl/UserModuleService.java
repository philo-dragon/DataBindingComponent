package com.pfl.module_user.service.impl;

import android.content.Context;
import android.os.UserManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.entity.module_user.User;
import com.pfl.common.service.IUserModeleService;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.constant.UserInfoManager;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

/**
 * ARouter跨模块的服务调用
 * 提供其它模块获取用户信息使用
 */
@Route(path = RouteUtils.MODULE_USER_LISTENER_USER_INFO)
public class UserModuleService implements IUserModeleService {

    @Override
    public User getUserInfo() {
        return UserInfoManager.getInstance().getUser();
    }

    @Override
    public void init(Context context) {

    }
}
