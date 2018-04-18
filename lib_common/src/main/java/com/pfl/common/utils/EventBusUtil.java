package com.pfl.common.utils;

import org.greenrobot.eventbus.EventBus;


/**
 * EventBus工具类
 */
public class EventBusUtil {

    private EventBusUtil() {
    }

    public static void regist(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregist(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void postMessage(Object object) {
        EventBus.getDefault().post(object);
    }

}
