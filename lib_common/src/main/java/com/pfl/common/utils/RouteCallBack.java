package com.pfl.common.utils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created by rocky on 2018/4/3.
 */

public class RouteCallBack implements NavigationCallback {


    /**
     * Callback when find the destination.
     * 找到了
     *
     * @param postcard meta
     */
    public void onFound(Postcard postcard) {
    }


    /**
     * Callback after lose your way.
     * 找不到了
     *
     * @param postcard meta
     */
    public void onLost(Postcard postcard) {
        RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_LOGIN);
    }


    /**
     * Callback after navigation.
     * 跳转完了
     *
     * @param postcard meta
     */
    public void onArrival(Postcard postcard) {
    }


    /**
     * Callback on interrupt.
     * 被拦截了
     *
     * @param postcard meta
     */
    public void onInterrupt(Postcard postcard) {
    }


}
