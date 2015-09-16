package com.jiayantech.jyandroid.clickaction;

import android.content.Intent;

import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.app.JYApplication;

/**
 * Created by liangzili on 15/9/6.
 */
public class ClickToMyAngelAction extends ClickToActivityAction{
    public ClickToMyAngelAction() {
        super(ClickToActivityAction.PAGE_MY_ANGEL);
    }

    @Override
    protected Intent createIntent(String type, long id) {
        return new Intent(JYApplication.getContext(), MyEventsActivity.class);
    }
}
