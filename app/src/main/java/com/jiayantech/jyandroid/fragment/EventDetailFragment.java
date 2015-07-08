package com.jiayantech.jyandroid.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.jyandroid.customwidget.webview.BaseWebChromeClient;
import com.jiayantech.jyandroid.customwidget.webview.JavascriptInterface;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;

/**
 * Created by liangzili on 15/7/8.
 */
public class EventDetailFragment extends WebViewFragment{
    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected BaseWebChromeClient onSetWebChromeClient() {
        return null;
    }

    @Override
    protected JavascriptInterface onAddJavascriptInterface() {
        return null;
    }
}
