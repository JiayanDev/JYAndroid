package com.jiayantech.jyandroid.handler.umengpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.SplashActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.SystemUtils;
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

        launchApplication(context, 60L, 18L, "diary", "liangzili");
    }

    private void launchApplication(Context context, long id, long userId, String type,
                                   String userName) {
        if (SystemUtils.isAppAlive(context, context.getPackageName())) {
//            Intent intent = new Intent(context, PostDetailActivity.class);
//            intent.putExtra(WebViewFragment.EXTRA_ID, id);
//            intent.putExtra(WebViewFragment.EXTRA_USER_ID, userId);
//            intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
//            intent.putExtra(WebViewFragment.EXTRA_USERNAME, userName);
            Intent intent = WebViewActivity.getLaunchIntent(context, id, userId, userName, type);

            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent[] intents = new Intent[2];
            intents[0] = mainIntent;
            intents[1]= intent;

            context.startActivities(intents);
        } else {
            Intent intent = context.getPackageManager().
                    getLaunchIntentForPackage("com.jiayantech.jyandroid");
            Bundle args = new Bundle();
            args.putLong(WebViewFragment.EXTRA_ID, id);
            args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
            args.putString(WebViewFragment.EXTRA_TYPE, type);
            args.putString(WebViewFragment.EXTRA_USERNAME, userName);
            intent.putExtra(SplashActivity.EXTRA_BUNDLE, args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }


}
