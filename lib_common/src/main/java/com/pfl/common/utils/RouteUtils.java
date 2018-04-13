package com.pfl.common.utils;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class RouteUtils {

    /**
     * app 模块
     */
    public static final String APP_MAIN_ACTIVITY = "/app/main";

    /**
     * module_user 模块
     */
    public static final String MODULE_USER_ACTIVITY_LOGIN = "/module_user/activity_login";
    public static final String MODULE_USER_ACTIVITY_REGIST = "/module_user/activity_retist";
    public static final String MODULE_USER_ACTIVITY_SETTING = "/module_user/activity_setting";
    public static final String MODULE_USER_ACTIVITY_ACCOUNT_INFO = "/module_user/activity_account_info";
    public static final String MODULE_USER_ACTIVITY_UPDATE_PASSWORD = "/module_user/activity_update_password";
    public static final String MODULE_USER_ACTIVITY_TEST = "/module_user/activity_test";


    public static final String MODULE_USER_FRAGMENT_MINE = "/module_user/fragment_mine";
    public static final String MODULE_USER_SCALE_RECYCLER_VIEW_FRAGMENT = "/module_user/fragment_scale_recycler_view";

    public static final String MODULE_USER_LISTENER_USER_INFO = "/module_user/listener_user_info";


    /** ===================================================================================================================================  **/


    /** ====================== 启动Activity不带动画 ============================= **/
    /**
     * 启动Activity
     *
     * @param path
     */
    public static void actionStart(String path) {
        actionStart(path, new HashMap<String, String>());
    }


    /**
     * 启动Activity
     * parameters 携带参数
     *
     * @param path
     */
    public static void actionStart(String path, Map<String, String> parameters) {
        Postcard build = ARouter.getInstance().build(path);

        for (Map.Entry<String, String> stringStringEntry : parameters.entrySet()) {
            build.withString(stringStringEntry.getKey(), stringStringEntry.getValue());
        }

        build.navigation();
    }


    /** ====================== 启动Activity带有动画 ============================= **/

    /**
     * 启动Activity
     * enterId 进入动画
     * exitId 退出动画
     *
     * @param path
     */
    public static void actionStart(String path, int enterId, int exitId) {
        actionStart(path, new HashMap<String, String>(), enterId, exitId);
    }

    /**
     * 启动Activity
     * parameters 携带参数
     * enterId 进入动画
     * exitId 退出动画
     *
     * @param path
     */
    public static void actionStart(String path, Map<String, String> parameters, int enterId, int exitId) {
        Postcard build = ARouter.getInstance().build(path);

        for (Map.Entry<String, String> stringStringEntry : parameters.entrySet()) {
            build.withString(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        build.withTransition(enterId, exitId);
        build.navigation();
    }


    /** ====================== 获取Fragment ============================= **/

    /**
     * 获取Fragment
     *
     * @param path
     */
    public static Fragment newFragment(String path) {
        return newFragment(path, new HashMap<String, String>());
    }


    /**
     * 获取Fragment
     * parameters 携带参数
     *
     * @param path
     */
    public static Fragment newFragment(String path, Map<String, String> parameters) {
        Postcard build = ARouter.getInstance().build(path);

        for (Map.Entry<String, String> stringStringEntry : parameters.entrySet()) {
            build.withString(stringStringEntry.getKey(), stringStringEntry.getValue());
        }

        Object navigation = build.navigation();
        if (!(navigation instanceof Fragment)) {
            throw new RuntimeException("path is not Fragment");
        }

        return (Fragment) navigation;
    }
}
