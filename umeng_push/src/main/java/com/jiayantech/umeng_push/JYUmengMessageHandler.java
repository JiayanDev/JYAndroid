package com.jiayantech.umeng_push;

import android.content.Context;
import android.content.Intent;

import com.jiayantech.library.utils.LogUtil;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/7/24.
 * 处理友盟推送，包括通知栏通知，和自定义消息
 */
public class JYUmengMessageHandler extends UmengMessageHandler{
    private Context mContext;
    public JYUmengMessageHandler(Context context){
        mContext = context;
    }

    @Override
    public void dealWithCustomMessage(Context context, UMessage uMessage) {
        LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                uMessage.custom));
        sendBroadcast(mContext, uMessage);
    }

    @Override
    public void dealWithNotificationMessage(Context context, UMessage uMessage) {
        super.dealWithNotificationMessage(context, uMessage);
        if(uMessage.custom != null && !uMessage.custom.equals("")) {
            LogUtil.i("UmengPushMessage", String.format("Notification Message custom: %s",
                    uMessage.custom));
            sendBroadcast(mContext, uMessage);
        }
    }

    public static void sendBroadcast(Context context, UMessage msg){
        Intent intent = new Intent(PushBroadcastReceiver.ACTION);
        intent.putExtra(PushBroadcastReceiver.EXTRA_UMESSAGE, msg.custom);
        context.sendBroadcast(intent);
    }
}
