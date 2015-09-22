package com.jiayantech.library.utils;

import android.util.Log;

import com.jiayantech.library.BuildConfig;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class LogUtil {
    public static void i(String tag, String msg) {
        if(!BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(!BuildConfig.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(!BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(!BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }
}
