package com.jiayantech.jyandroid.handler;

import android.content.Context;

import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/7/24.
 */
public class JYUmengMessageHandler extends UmengMessageHandler{

    @Override
    public void dealWithCustomMessage(Context context, UMessage uMessage) {
        ToastUtil.showMessage(uMessage.custom);
        LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                uMessage.custom));
    }
}
