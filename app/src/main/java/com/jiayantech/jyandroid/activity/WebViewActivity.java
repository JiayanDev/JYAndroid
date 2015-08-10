package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.factory.WebViewFragmentFactory;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebViewActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        long id = (long)getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
        long userId = (long)getIntent().getLongExtra(WebViewFragment.EXTRA_USER_ID, -1);
        String userName = getIntent().getStringExtra(WebViewFragment.EXTRA_USERNAME);
        String type = getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE);

        Fragment fragment = WebViewFragmentFactory.createFragment(type, id, userId, userName);
        return fragment;
    }

    public static void lauchActivity(Context context,long id, long userId,
                                     String userName, String type){
        context.startActivity(getLaunchIntent(context, id, userId, userName, type));
    }

    public static Intent getLaunchIntent(Context context,long id, long userId,
                                       String userName, String type){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewFragment.EXTRA_ID, id);
        intent.putExtra(WebViewFragment.EXTRA_USER_ID, userId);
        intent.putExtra(WebViewFragment.EXTRA_USERNAME, userName);
        intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
        return intent;
    }
}
