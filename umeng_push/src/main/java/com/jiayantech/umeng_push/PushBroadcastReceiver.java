package com.jiayantech.umeng_push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.umeng_push.model.BasePushMessage;
import com.jiayantech.umeng_push.model.JumpToPageData;

import java.util.Map;

/**
 * Created by liangzili on 15/7/31.
 */
public class PushBroadcastReceiver extends BroadcastReceiver{
    public static final String EXTRA_UMESSAGE = "custom";
    public static final String EXTRA_TYPE = "type";

    public static final String TYPE_CUSTOME_MESSAGE = "custome_message"; //推送过来的是普通的消息
    public static final String TYPE_NOTIFICATION = "notification"; //推送过来的是一个Notification

    public static final String ACTION = "com.jiayantech.jyandroid.PushBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String custom = intent.getStringExtra(EXTRA_UMESSAGE);
            LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                    custom));
            Map<String, Object> result = GsonUtils.build().fromJson(custom,
                    new TypeToken<Map<String, Object>>(){}.getType());
            switch ((String)result.get("action")){
                case UmengPushManager.ACTION_JUMP_TO_PAGE:
                    BasePushMessage<JumpToPageData> jumpToPage = GsonUtils.build().fromJson(custom,
                            new TypeToken<BasePushMessage<JumpToPageData>>(){}.getType());
                    UmengPushManager.getInstance().dispatch(jumpToPage);
                    break;
                case UmengPushManager.ACTION_JUMP_TO_WEB:
                    BasePushMessage<String> jumpToWeb = GsonUtils.build().fromJson(custom,
                            new TypeToken<BasePushMessage<String>>(){}.getType());
                    UmengPushManager.getInstance().dispatch(jumpToWeb);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
