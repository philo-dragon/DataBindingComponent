package com.pfl.common.listener;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * Created by Administrator on 2018\1\26 0026.
 */

public class ArouterNavigationCallback implements NavigationCallback {

    /**
     * Callback when find the destination.
     * 找到了
     * @param postcard meta
     */
    @Override
    public void onFound(Postcard postcard) {

    }

    /**
     * Callback after lose your way.
     * 找不到了
     * @param postcard meta
     */
    @Override
    public void onLost(Postcard postcard) {

    }

    /**
     * Callback after navigation.
     * 跳转完了
     * @param postcard meta
     */
    @Override
    public void onArrival(Postcard postcard) {

    }

    /**
     * Callback on interrupt.
     * 被拦截了
     * @param postcard meta
     */
    @Override
    public void onInterrupt(Postcard postcard) {

    }
}
