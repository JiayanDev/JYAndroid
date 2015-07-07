package com.jiayantech.jyandroid.customwidget.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/7.
 */
public class BaseWebChromeClient extends WebChromeClient{
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        EventBus.getDefault().post(new WebViewLoadingProgressEvent(newProgress));
    }
}
