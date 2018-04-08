package com.pfl.module_user.constant;


import com.pfl.common.entity.module_user.User;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class UserInfoManager {

    private static UserInfoManager INSTANCE;
    private static User user;

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {

        if (null == INSTANCE) {
            synchronized (UserInfoManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new UserInfoManager();
                }
            }
        }

        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        if (user == null) {
            user = new User();
            user.setName("潘飞龙");
            user.setNickName("philo");
            user.setPhoto("http://g.hiphotos.baidu.com/image/pic/item/c8ea15ce36d3d539f09733493187e950342ab095.jpg");
            user.setType("1");
        }
        return user;
    }
}
