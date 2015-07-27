package com.jiayantech.jyandroid.handler;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.utils.ToastUtil;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/7/24.
 */
public class JYUmengMessageHandler extends UmengMessageHandler{
//    @Override
//    public Notification getNotification(Context context, UMessage uMessage) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setContentTitle(uMessage.title);
//        builder.setTicker(uMessage.ticker);
//        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.category_breast_normal));
//        builder.setSmallIcon(R.drawable.icon_write_press);
//        Notification notification = builder.build();
//
//        return notification;
//    }


    @Override
    public void dealWithCustomMessage(Context context, UMessage uMessage) {
        ToastUtil.showMessage(uMessage.custom);
    }
}
