package com.jiayantech.jyandroid.handler.umengpush;

import android.content.Context;
import android.content.Intent;

import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.library.utils.LogUtil;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

/**
 * Created by liangzili on 15/7/24.
 */
public class JYUmengNotificationClickHandler extends UmengNotificationClickHandler {
    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        super.dealWithCustomAction(context, uMessage);
        LogUtil.i("UmengPushAction", String.format("Message title: %s , ticker: %s",
                uMessage.title, uMessage.ticker));

        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(WebViewFragment.EXTRA_ID, 49);
        intent.putExtra(WebViewFragment.EXTRA_USER_ID, 18);
        intent.putExtra(WebViewFragment.EXTRA_TYPE, "diary");
        intent.putExtra(WebViewFragment.EXTRA_USERNAME, "liangzili");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity0(context, intent);
        //context.startActivity(intent);

    }

    public void startActivity(Context context, Intent intent) {
//        Intent mainIntent = new Intent();
//        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent mainIntent = context.getPackageManager().
                getLaunchIntentForPackage(context.getPackageName());
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent[] intents = new Intent[2];
        intents[0] = mainIntent;
        intents[1] = intent;
        context.startActivities(intents);
    }

    public void startActivity0(Context context, Intent mainIntent) {
//        Intent mainIntent = new Intent();
//        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainIntent = new Intent(context, PostDetailActivity.class);
        mainIntent.putExtra(WebViewFragment.EXTRA_ID, 49);
        mainIntent.putExtra(WebViewFragment.EXTRA_USER_ID, 18);
        mainIntent.putExtra(WebViewFragment.EXTRA_TYPE, "diary");
        mainIntent.putExtra(WebViewFragment.EXTRA_USERNAME, "liangzili");
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setAction(Intent.ACTION_MAIN);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        Intent[] intents = new Intent[2];
//        intents[0] = mainIntent;
//        intents[1] = intent;
        context.startActivity(mainIntent);
    }
}
