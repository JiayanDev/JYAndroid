package com.jiayantech.jyandroid.app;

import android.content.Context;
import android.widget.Toast;

import com.jiayantech.library.base.BaseApplication;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/6/24.
 */
public class JYApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initContext(getApplicationContext());
    }


    private void initContext(Context context) {
        //重写友盟自定义行为处理
        UmengNotificationClickHandler handler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                Toast.makeText(JYApplication.this, "Hello , I am a push notification!", Toast.LENGTH_SHORT).show();
            }
        };
        PushAgent.getInstance(this).setNotificationClickHandler(handler);
    }
}
