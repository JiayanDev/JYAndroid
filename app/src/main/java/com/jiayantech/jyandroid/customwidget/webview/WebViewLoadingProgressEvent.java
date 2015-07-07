package com.jiayantech.jyandroid.customwidget.webview;

/**
 * Created by liangzili on 15/7/7.
 */
public class WebViewLoadingProgressEvent {
    private int mProgress;
    public WebViewLoadingProgressEvent(int progress){
        mProgress = progress;
    }

    public int getProgress(){
        return mProgress;
    }
}
