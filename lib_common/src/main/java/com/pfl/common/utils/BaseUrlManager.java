package com.pfl.common.utils;

/**
 * Created by rocky on 2017/12/28.
 */

public class BaseUrlManager {

    private static String DEV_URL;
    private static String RELEASE_URL;
    private static boolean isDebug;

    public static void init(String dev_url, String release_url, boolean is_debug) {
        DEV_URL = dev_url;
        RELEASE_URL = release_url;
        isDebug = is_debug;
    }

    public static String getBaseUrl() {
        return isDebug ? DEV_URL : RELEASE_URL;
    }
}
