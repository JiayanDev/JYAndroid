package com.jiayantech.umeng_push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by liangzili on 15/9/6.
 */
public class ClickActionReceiver extends BroadcastReceiver{
    public static final String ACTION = "com.jiayantech.jyandroid.ClickActionReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        long id = intent.getLongExtra("id", -1);
        String url = intent.getStringExtra("url");

        UmengPushManager.getInstance().handleClickActionFromNotification(action, id, url);
    }
}
