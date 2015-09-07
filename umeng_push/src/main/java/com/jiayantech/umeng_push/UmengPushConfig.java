package com.jiayantech.umeng_push;

import android.app.Activity;

/**
 * Created by liangzili on 15/9/6.
 */
public class UmengPushConfig {
    //应用的主Activity，用于点击通知跳转
    private Class<? extends Activity> mMainActivityClass;

    public UmengPushConfig setMainActivityClass(Class<? extends Activity> clazz){
        mMainActivityClass = clazz;
        return this;
    }

    public Class<? extends Activity> getMainActivityClass() {
        return mMainActivityClass;
    }
}
