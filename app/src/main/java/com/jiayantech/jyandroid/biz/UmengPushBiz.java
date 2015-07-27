package com.jiayantech.jyandroid.biz;

import android.content.Context;

import com.jiayantech.jyandroid.handler.JYUmengMessageHandler;
import com.jiayantech.jyandroid.handler.JYUmengNotificationClickHandler;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * Created by liangzili on 15/7/6.
 */
public class UmengPushBiz {
    private static Context appContext;
    /**
     * enable or disable umeng push notification service
     * @param enable
     */
    public static void enablePush(Context context, boolean enable){
        if(enable){
            PushAgent.getInstance(context).enable();
        }else{
            PushAgent.getInstance(context).disable();
        }
    }

    /**
     * 上传umeng推送的device_token
     *
     */
    public static String getDeviceToken(){
        String device_token = UmengRegistrar.getRegistrationId(appContext);
        return device_token;
    }

    public static void init(Context applicationContext) {
        appContext = applicationContext;
        //重写友盟自定义行为处理
        PushAgent.getInstance(applicationContext).setNotificationClickHandler(
                new JYUmengNotificationClickHandler());
        //重写友盟推送通知栏
        PushAgent.getInstance(applicationContext).setMessageHandler(new JYUmengMessageHandler());
    }
}
