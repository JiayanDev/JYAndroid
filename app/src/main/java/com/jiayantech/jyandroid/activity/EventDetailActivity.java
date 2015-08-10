//package com.jiayantech.jyandroid.activity;
//
//import android.support.v4.app.Fragment;
//
//import com.jiayantech.jyandroid.customwidget.webview.EventDetailFragment;
//import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
//import com.jiayantech.library.base.SingleFragmentActivity;
//
///**
// * Created by liangzili on 15/7/9.
// */
//public class EventDetailActivity extends SingleFragmentActivity{
//    @Override
//    protected Fragment createFragment() {
//        long id = getIntent().getLongExtra(WebViewFragment.EXTRA_ID, -1);
//        long userId = getIntent().getLongExtra(WebViewFragment.EXTRA_USER_ID, -1);
//        String userName = getIntent().getStringExtra(WebViewFragment.EXTRA_USERNAME);
//
//        return EventDetailFragment.newInstance(id, userId, userName, "event");
//    }
//}
