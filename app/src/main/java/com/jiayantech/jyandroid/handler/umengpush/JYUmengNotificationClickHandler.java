package com.jiayantech.jyandroid.handler.umengpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.activity.CompanyEventActivity;
import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.SplashActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.biz.UmengPushBiz;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.jyandroid.model.umengpush.BasePushMessage;
import com.jiayantech.jyandroid.model.umengpush.JumpToPageData;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.SystemUtils;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Map;

/**
 * Created by liangzili on 15/7/24.
 */
public class JYUmengNotificationClickHandler extends UmengNotificationClickHandler {
    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        super.dealWithCustomAction(context, uMessage);
        LogUtil.i("UmengPushAction", String.format("Message title: %s , ticker: %s , custom: %s ",
                uMessage.title, uMessage.ticker, uMessage.custom));

        Map<String, Object> result = GsonUtils.build().fromJson(uMessage.custom,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        switch ((String) result.get("action")) {
            case UmengPushBiz.ACTION_JUMP_TO_PAGE:
                BasePushMessage<JumpToPageData> jumpToPage = GsonUtils.build().fromJson(
                        uMessage.custom,
                        new TypeToken<BasePushMessage<JumpToPageData>>() {
                        }.getType());
                //UmengPushManager.getInstance().dispatch(jumpToPage);
                String type = null;
                switch (jumpToPage.data.page) {
                    case UmengPushBiz.JUMP_TO_PAGE_DIARY:
                        type = WebConstans.Type.TYPE_DIARY;
                        break;
                    case UmengPushBiz.JUMP_TO_PAGE_TOPIC:
                        type = WebConstans.Type.TYPE_TOPIC;
                        break;
                    case UmengPushBiz.JUMP_TO_PAGE_MY_ANGEL:
                        type = "my_angel";
                        break;
                    case UmengPushBiz.JUMP_TO_PAGE_MY_COMPANY:
                        type = "my_company";
                        break;
                }
                launchApplication(context, jumpToPage.data.id, type);
                break;
            case UmengPushBiz.ACTION_JUMP_TO_WEB:
                BasePushMessage<String> jumpToWeb = GsonUtils.build().fromJson(uMessage.custom,
                        new TypeToken<BasePushMessage<String>>() {
                        }.getType());
                //UmengPushManager.getInstance().dispatch(jumpToWeb);
                break;
        }

        launchApplication(context, 60L, "diary");
    }

    @Override
    public void handleMessage(Context context, UMessage uMessage) {
        super.handleMessage(context, uMessage);
        LogUtil.i("UmengPushAction", String.format("handleMessage title: %s , ticker: %s , custom: %s ",
                uMessage.title, uMessage.ticker, uMessage.custom));
    }

    private void launchApplication(Context context, long id, String type) {
        if (SystemUtils.isAppAlive(context, context.getPackageName())) {
            Intent intent = null;
            switch (type){
                case WebConstans.Type.TYPE_DIARY:
                case WebConstans.Type.TYPE_TOPIC:
                case WebConstans.Type.TYPE_EVENT:
                case WebConstans.Type.TYPE_PERSONAL_PAGE:
                    intent = WebViewActivity.createLaunchIntent(context, id, type);
                    break;
                case "my_angel":
                    intent = new Intent(context, MyEventsActivity.class);
                    break;
                case "my_company":
                    intent = new Intent(context, CompanyEventActivity.class);
                    break;
            }
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Intent[] intents = new Intent[2];
            intents[0] = mainIntent;
            intents[1] = intent;

            context.startActivities(intents);
        } else {
            Intent intent = context.getPackageManager().
                    getLaunchIntentForPackage("com.jiayantech.jyandroid");
            Bundle args = new Bundle();
            args.putLong(WebViewFragment.EXTRA_ID, id);
            //args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
            args.putString(WebViewFragment.EXTRA_TYPE, type);
            //args.putString(WebViewFragment.EXTRA_USERNAME, userName);
            intent.putExtra(SplashActivity.EXTRA_BUNDLE, args);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }


}
