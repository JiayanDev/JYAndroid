package com.jiayantech.library.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
    public String version = "1.0.0";


    public static BaseApplication getContext() {
        return sContext;
    }

    /**
     * 获取app的版本信息
     */
    public String getVersionName() {
        PackageManager packageManager = getPackageManager();// 获取packagemanager的实例
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);// getPackageName()是你当前类的包名，0代表是获取版本信息
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    @Override
    public void onCreate() {
        sContext = this;
        super.onCreate();
        mBroadcastHelper = new BroadcastHelper();
        //CrashHandler.getInstance().init(getApplicationContext());// 注册crashHandler
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
