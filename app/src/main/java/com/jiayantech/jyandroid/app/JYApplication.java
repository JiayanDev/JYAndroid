package com.jiayantech.jyandroid.app;

import com.jiayantech.jyandroid.biz.ShareBiz;
import com.jiayantech.jyandroid.biz.UmengPushBiz;
import com.jiayantech.library.base.BaseApplication;

/**
 * Created by liangzili on 15/6/24.
 */
public class JYApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        UmengPushBiz.init(getApplicationContext());
        ShareBiz.registerToWx(getApplicationContext());
    }

}
