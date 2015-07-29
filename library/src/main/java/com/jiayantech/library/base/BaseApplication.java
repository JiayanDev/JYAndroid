package com.jiayantech.library.base;

import android.app.Application;

import com.jiayantech.library.comm.CrashHandler;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.HttpReq;

/**
 * Created by janseon on 2015/6/28.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication sContext;

    public static BaseApplication getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        sContext = this;
        super.onCreate();
        mBroadcastHelper = new BroadcastHelper();
        CrashHandler.getInstance().init(getApplicationContext());// 注册crashHandler
    }

    /**
     * 请求过期
     *
     * @param httpReq
     */
    public void onOverdue(HttpReq httpReq) {
    }

    /**
     * app crash之后
     *
     * @param ex
     * @param message
     */
    public void onCrash(Throwable ex, String message) {
    }

    protected BroadcastHelper mBroadcastHelper;

    @Override
    public void onTerminate() {
        super.onTerminate();
        mBroadcastHelper.onDestroy();
    }

}
