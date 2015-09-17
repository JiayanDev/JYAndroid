package com.jiayantech.jyandroid.clickaction;

import android.content.Intent;
import android.net.Uri;

import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.umeng_push.model.PushMessageClickAction;

/**
 * 点击通知栏跳转到web页面
 * Created by liangzili on 15/9/6.
 */
public class ClickToWebAction extends PushMessageClickAction{
    public ClickToWebAction(String action) {
        super(action);
    }

    @Override
    public void executeAction(String action, long id, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        JYApplication.getContext().startActivity(intent);
    }

    @Override
    public Intent createIntent(String type, long id, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        return intent;
    }
}
