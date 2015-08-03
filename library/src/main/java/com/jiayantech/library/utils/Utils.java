package com.jiayantech.library.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.jiayantech.library.base.BaseApplication;

import java.util.List;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Utils {
    public static float dp2px(float dp) {
        return dp * BaseApplication.getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                LogUtil.i("Jiayan",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        LogUtil.i("pa",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
}
