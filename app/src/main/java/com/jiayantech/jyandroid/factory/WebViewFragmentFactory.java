package com.jiayantech.jyandroid.factory;

import com.jiayantech.jyandroid.fragment.webview.EventDetailFragment;
import com.jiayantech.jyandroid.fragment.webview.HelpFragment;
import com.jiayantech.jyandroid.fragment.webview.PostDetailFragment;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebViewFragmentFactory {
    public static WebViewFragment createFragment(String type, long id) {
        switch (type) {
            case WebConstans.Type.TYPE_EVENT:
                return EventDetailFragment.newInstance(id);
            case WebConstans.Type.TYPE_TOPIC:
            case WebConstans.Type.TYPE_DIARY:
                return PostDetailFragment.newInstance(id, type);
            case WebConstans.Type.TYPE_HELP:
                return HelpFragment.newInstance();
            case WebConstans.Type.TYPE_PERSONAL_PAGE:
                //return PersonalPageFragment.newInstance(userId, userName);
            default:
                throw new IllegalArgumentException("WebViewFragmentFactory createFragment invalid" +
                        "type");
        }
    }
}
