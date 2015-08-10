package com.jiayantech.jyandroid.factory;

import com.jiayantech.jyandroid.customwidget.webview.EventDetailFragment;
import com.jiayantech.jyandroid.customwidget.webview.PersonalPageFragment;
import com.jiayantech.jyandroid.customwidget.webview.PostDetailFragment;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebViewFragmentFactory {
    public static WebViewFragment createFragment(String type, long id, long userId,
                                                 String userName){
        switch (type){
            case WebViewFragment.TYPE_EVENT:
                return EventDetailFragment.newInstance(id, userId, userName);
            case WebViewFragment.TYPE_TOPIC:
            case WebViewFragment.TYPE_DIARY:
                return PostDetailFragment.newInstance(id, userId, userName, type);
            case WebViewFragment.TYPE_PERSONAL_PAGE:
                return null;
            default:
                throw new IllegalArgumentException("WebViewFragmentFactory createFragment invalid" +
                        "type");
        }
    }
}
