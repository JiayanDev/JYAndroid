package com.jiayantech.jyandroid.clickaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.SplashActivity;
import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.library.utils.SystemUtils;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.umeng_push.model.PushMessageClickAction;

/**
 * 点击通知栏跳转到本地的Activity
 * Created by liangzili on 15/9/6.
 */
public abstract class ClickToActivityAction extends PushMessageClickAction{
    public static final String TYPE_DIARY = "diary_detail";
    public static final String TYPE_TOPIC = "topic_detail";
    public static final String TYPE_MY_ANGEL = "my_angel";
    public static final String TYPE_MY_COMPANY = "my_company";

    public ClickToActivityAction(String action) {
        super(action);
    }

    @Override
    public void executeAction(String action, long id, String url) {
        Context context = JYApplication.getContext();
        Intent activityIntent = createIntent(convertType(action), id);
        if (SystemUtils.isAppAlive(context, context.getPackageName())) {
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Intent[] intents = new Intent[2];
            intents[0] = mainIntent;
            intents[1] = activityIntent;
            context.startActivities(intents);
        } else {
            Intent intent = context.getPackageManager().
                    getLaunchIntentForPackage("com.jiayantech.jyandroid");
            Bundle args = new Bundle();
            args.putLong(WebViewFragment.EXTRA_ID, id);
            args.putString(WebViewFragment.EXTRA_TYPE, convertType(action));
            intent.putExtra(SplashActivity.EXTRA_BUNDLE, args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }

    protected abstract Intent createIntent(String type, long id);

    public static String convertType(String action){
        String type = null;
        switch (action){
            case UmengPushManager.JUMP_TO_PAGE_DIARY:
                type = WebConstans.Type.TYPE_DIARY;
                break;
            case UmengPushManager.JUMP_TO_PAGE_TOPIC:
                type = WebConstans.Type.TYPE_TOPIC;
                break;
            case UmengPushManager.JUMP_TO_PAGE_MY_ANGEL:
                type = TYPE_MY_ANGEL;
                break;
            case UmengPushManager.JUMP_TO_PAGE_MY_COMPANY:
                type = TYPE_MY_COMPANY;
                break;
        }
        return type;
    }
}
