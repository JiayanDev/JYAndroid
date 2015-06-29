package com.jiayantech.library.base;

import android.app.Application;
import android.content.Context;

import com.jiayantech.library.comm.CrashHandler;

/**
 * Created by janseon on 2015/6/28.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        // 注册crashHandler
        CrashHandler.getInstance().init(getApplicationContext());
    }

    public static Context getContext() {
        return sContext;
    }

    public static void onCrash(Throwable ex, String message) {
    }

}
