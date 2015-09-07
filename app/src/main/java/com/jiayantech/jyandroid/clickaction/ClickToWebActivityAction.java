package com.jiayantech.jyandroid.clickaction;

import android.content.Intent;

import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.app.JYApplication;

/**
 * Created by liangzili on 15/9/6.
 */
public class ClickToWebActivityAction extends ClickToActivityAction{
    public ClickToWebActivityAction(String action) {
        super(action);
    }

    @Override
    protected Intent createIntent(String action, long id) {
        return WebViewActivity.createLaunchIntent(JYApplication.getContext(), id, action);
    }
}
