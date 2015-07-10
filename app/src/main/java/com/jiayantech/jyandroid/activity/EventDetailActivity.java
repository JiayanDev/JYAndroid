package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.customwidget.webview.EventDetailFragment;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/9.
 */
public class EventDetailActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return EventDetailFragment.newInstance(getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1)
                , "event");
    }
}
