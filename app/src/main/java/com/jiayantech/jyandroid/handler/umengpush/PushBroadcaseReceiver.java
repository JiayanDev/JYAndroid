package com.jiayantech.jyandroid.handler.umengpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiayantech.jyandroid.eventbus.UmengPushCustomMessage;
import com.jiayantech.library.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/31.
 */
public class PushBroadcaseReceiver extends BroadcastReceiver{
    public static final String EXTRA_UMESSAGE = "custom";
    public static final String ACTION = "com.jiayantech.jyandroid.PushBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String custom = intent.getStringExtra(EXTRA_UMESSAGE);
        LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                custom));
        EventBus.getDefault().post(new UmengPushCustomMessage());
    }
}
