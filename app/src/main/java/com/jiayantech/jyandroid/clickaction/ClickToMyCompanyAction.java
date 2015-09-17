package com.jiayantech.jyandroid.clickaction;

import android.content.Intent;

import com.jiayantech.jyandroid.activity.CompanyEventActivity;
import com.jiayantech.jyandroid.app.JYApplication;

/**
 * Created by liangzili on 15/9/6.
 */
public class ClickToMyCompanyAction extends ClickToActivityAction{
    public ClickToMyCompanyAction() {
        super(ClickToActivityAction.PAGE_MY_COMPANY);
    }

    @Override
    public Intent createIntent(String type, long id, String url) {
        return new Intent(JYApplication.getContext(), CompanyEventActivity.class);
    }
}
