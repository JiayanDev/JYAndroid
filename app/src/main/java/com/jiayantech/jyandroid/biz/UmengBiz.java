package com.jiayantech.jyandroid.biz;

import android.content.Context;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

/**
 * Created by liangzili on 15/7/6.
 */
public class UmengBiz {
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

    public static void uploadDeviceToken(Context context){
        String device_token = UmengRegistrar.getRegistrationId(context);
        if(!device_token.equals("")){
            //上传设备token到服务器
        }
    }
}
