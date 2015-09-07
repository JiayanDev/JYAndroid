package com.jiayantech.umeng_push;

import android.content.Context;
import android.content.Intent;

import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangzili on 15/9/2.
 */
public class UmengIntentService extends UmengBaseIntentService {
    @Override
    protected void onMessage(Context context, Intent intent) {
        super.onMessage(context, intent);
        try {
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Intent i = new Intent(PushBroadcastReceiver.ACTION);
            intent.putExtra(PushBroadcastReceiver.EXTRA_UMESSAGE, msg.custom);
            intent.putExtra(PushBroadcastReceiver.EXTRA_TYPE, PushBroadcastReceiver.TYPE_NOTIFICATION);
            context.sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
