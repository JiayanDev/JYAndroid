package com.jiayantech.jyandroid.handler.umengpush;

import android.content.Context;
import android.content.Intent;

import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
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
        ToastUtil.showMessage(uMessage.custom);
        LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                uMessage.custom));
        Intent intent = new Intent(PushBroadcaseReceiver.ACTION);
        intent.putExtra(PushBroadcaseReceiver.EXTRA_UMESSAGE, uMessage.custom);
        mContext.sendBroadcast(intent);
    }
}
