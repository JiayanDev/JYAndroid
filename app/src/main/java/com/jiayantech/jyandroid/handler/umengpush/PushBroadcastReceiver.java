package com.jiayantech.jyandroid.handler.umengpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.biz.UmengPushBiz;
import com.jiayantech.jyandroid.model.umengpush.BasePushMessage;
import com.jiayantech.jyandroid.model.umengpush.JumpToPageData;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;

import java.util.Map;

/**
 * Created by liangzili on 15/7/31.
 */
public class PushBroadcastReceiver extends BroadcastReceiver{
    public static final String EXTRA_UMESSAGE = "custom";
    public static final String ACTION = "com.jiayantech.jyandroid.PushBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String custom = intent.getStringExtra(EXTRA_UMESSAGE);
        LogUtil.i("UmengPushMessage", String.format("Message custom: %s",
                custom));
        //EventBus.getDefault().post(new UmengPushCustomMessage());
        Map<String, Object> result = GsonUtils.build().fromJson(custom,
                new TypeToken<Map<String, Object>>(){}.getType());
        switch ((String)result.get("action")){
            case UmengPushBiz.ACTION_JUMP_TO_PAGE:
                BasePushMessage<JumpToPageData> jumpToPage = GsonUtils.build().fromJson(custom,
                        new TypeToken<BasePushMessage<JumpToPageData>>(){}.getType());
                UmengPushManager.getInstance().dispatch(jumpToPage);
                break;
            case UmengPushBiz.ACTION_JUMP_TO_WEB:
                BasePushMessage<String> jumpToWeb = GsonUtils.build().fromJson(custom,
                        new TypeToken<BasePushMessage<String>>(){}.getType());
                UmengPushManager.getInstance().dispatch(jumpToWeb);
                break;
        }
    }
}
