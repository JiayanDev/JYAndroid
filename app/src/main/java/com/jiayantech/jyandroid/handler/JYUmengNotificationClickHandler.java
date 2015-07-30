package com.jiayantech.jyandroid.handler;

import android.content.Context;

import com.jiayantech.library.utils.LogUtil;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/7/24.
 */
public class JYUmengNotificationClickHandler extends UmengNotificationClickHandler{
    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        super.dealWithCustomAction(context, uMessage);
        LogUtil.i("UmengPushAction", String.format("Message title: %s , ticker: %s",
                uMessage.title, uMessage.ticker));
    }
}
