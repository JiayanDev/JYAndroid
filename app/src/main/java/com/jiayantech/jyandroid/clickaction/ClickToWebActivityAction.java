package com.jiayantech.jyandroid.clickaction;

import android.content.Intent;

import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.activity.WebViewActivityOverlay;
import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;

/**
 * Created by liangzili on 15/9/6.
 */
public class ClickToWebActivityAction extends ClickToActivityAction{
    public ClickToWebActivityAction(String action) {
        super(action);
    }

    @Override
    public Intent createIntent(String type, long id, String url) {
        String newType = ClickToActivityAction.convertType(type);
        if(newType == WebConstans.Type.TYPE_PERSONAL_PAGE){
            return WebViewActivityOverlay.createLaunchIntent(JYApplication.getContext(), id, newType);
        }else {
            return WebViewActivity.createLaunchIntent(JYApplication.getContext(), id, newType);
        }
    }
}
