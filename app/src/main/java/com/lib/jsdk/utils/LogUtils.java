package com.lib.jsdk.utils;

import android.util.Log;

import com.lib.jsdk.sdk.JSdk;

public class LogUtils {
    private static String tag = "datdb";

    public static void d(String string) {
        if (JSdk.DEBUG) {
            Log.d(tag, string);
        }
    }

    public static void setTag(String t) {
        tag = t;
    }
}
