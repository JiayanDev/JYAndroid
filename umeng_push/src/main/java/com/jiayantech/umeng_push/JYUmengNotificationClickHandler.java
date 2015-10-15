package com.jiayantech.umeng_push;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.umeng_push.model.BasePushMessage;
import com.jiayantech.umeng_push.model.JumpToPageData;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Map;

/**
 * Created by liangzili on 15/7/24.
 */
class JYUmengNotificationClickHandler extends UmengNotificationClickHandler {
    private Class<? extends Activity> mMainActivityClass;

    JYUmengNotificationClickHandler(Class<? extends Activity> clazz) {
        mMainActivityClass = clazz;
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage uMessage) {
        super.dealWithCustomAction(context, uMessage);
        LogUtil.i("UmengPushAction", String.format("Message title: %s , ticker: %s , custom: %s ",
                uMessage.title, uMessage.ticker, uMessage.custom));

        Map<String, Object> result = GsonUtils.build().fromJson(uMessage.custom,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        String type = null;
        String url = null;
        long id = 0;
        switch ((String) result.get("action")) {
            case UmengPushManager.ACTION_JUMP_TO_PAGE:
                BasePushMessage<JumpToPageData> jumpToPage = GsonUtils.build().fromJson(
                        uMessage.custom,
                        new TypeToken<BasePushMessage<JumpToPageData>>() {
                        }.getType());
                type = jumpToPage.data.page;
                id = jumpToPage.data.id;
                break;
            case UmengPushManager.ACTION_JUMP_TO_WEB:
                BasePushMessage<String> jumpToWeb = GsonUtils.build().fromJson(uMessage.custom,
                        new TypeToken<BasePushMessage<String>>() {
                        }.getType());
                url = jumpToWeb.data;
        }
        sendBroadcast(context, type, id, url);
    }

    private void sendBroadcast(Context context, String type, long id, String url) {
        Intent intent = new Intent();
        intent.setAction(ClickActionReceiver.ACTION);
        intent.putExtra("action", type);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        context.sendBroadcast(intent);
    }

//    private void launchApplication(Context context, Intent intent){
//        if(SystemUtils.isAppAlive(context, context.getPackageName())){
//            Intent mainIntent = new Intent(context, mMainActivityClass);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );
//            Intent[] intents = new Intent[2];
//            intents[0] = mainIntent;
//            intents[1] = intent;
//            context.startActivities(intents);
//        }
//    }

    private void launchApplication(Context context, long id, String type) {
//        if (SystemUtils.isAppAlive(context, context.getPackageName())) {
//            Intent intent = null;
//            switch (type){
//                case WebConstans.Type.TYPE_DIARY:
//                case WebConstans.Type.TYPE_TOPIC:
//                case WebConstans.Type.TYPE_EVENT:
//                case WebConstans.Type.TYPE_PERSONAL_PAGE:
//                    intent = WebViewActivity.createLaunchIntent(context, id, type);
//                    break;
//                case "my_angel":
//                    intent = new Intent(context, MyEventsActivity.class);
//                    break;
//                case "my_company":
//                    intent = new Intent(context, CompanyEventActivity.class);
//                    break;
//            }
//            Intent mainIntent = new Intent(context, MainActivity.class);
//            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            Intent[] intents = new Intent[2];
//            intents[0] = mainIntent;
//            intents[1] = intent;
//
//            context.startActivities(intents);
//        } else {
//            Intent intent = context.getPackageManager().
//                    getLaunchIntentForPackage("com.jiayantech.jyandroid");
//            Bundle args = new Bundle();
//            args.putLong(WebViewFragment.EXTRA_ID, id);
//            //args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
//            args.putString(WebViewFragment.EXTRA_TYPE, type);
//            //args.putString(WebViewFragment.EXTRA_USERNAME, userName);
//            intent.putExtra(SplashActivity.EXTRA_BUNDLE, args);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            context.startActivity(intent);
//        }
    }


}
