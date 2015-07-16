package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.customwidget.webview.PostDetailFragment;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/8.
 */
public class PostDetailActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        long id = getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
        long userId = getIntent().getLongExtra(WebViewFragment.EXTRA_USER_ID, -1);
        String userName = getIntent().getStringExtra(WebViewFragment.EXTRA_USERNAME);
        String type = getIntent().getStringExtra(WebViewFragment.EXTRA_TYPE);

        return PostDetailFragment.newInstance(id, userId, userName, type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.up_indicator);
    }
}
