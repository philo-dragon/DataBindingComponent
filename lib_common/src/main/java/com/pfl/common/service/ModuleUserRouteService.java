package com.pfl.common.service;

import com.alibaba.android.arouter.launcher.ARouter;
import com.pfl.common.entity.module_user.UserInfo;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class ModuleUserRouteService {

    public static UserInfo getUserInfo() {
        IUserModeleService userModeleService = ARouter.getInstance().navigation(IUserModeleService.class);

        if (null != userModeleService) {
            return userModeleService.getUserInfo();
        }

        return null;
    }

}
