package com.jiayantech.jyandroid.fragment.webview;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/7/16.
 * BaseWebViewClient 处理Js调用Native，页面跳转等事件
 */
public class BaseWebViewClient extends WebViewClient {
    private static final String JS_PREFIX = "jiayan://js_call_native?";

    private WebViewFragment mWebViewFragment;
    private List<WebActionListener> mWebActionList = new ArrayList<>();

    public BaseWebViewClient(WebViewFragment fragment) {
        mWebViewFragment = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        url = Uri.decode(url);
        LogUtil.i("WebView", "loading url: " + url);
        if (url.startsWith(JS_PREFIX)) {
            //Js调用Native
            String action = JsNativeBiz.getJsAction(url);
            String jsonString = url.substring(url.indexOf("{"), url.lastIndexOf("}") + 1);
            callMethod(action, jsonString);
            return super.shouldOverrideUrlLoading(view, url);
        } else {
            //URL监听跳转
            URI uri = URI.create(url);
            String action = uri.getPath();
            redirectUrl(action, uri);
            return true;
        }
    }

    public void addActionListener(WebActionListener listener){
        mWebActionList.add(listener);
    }

    public void callMethod(String action, String jsonString){
        BaseJsCall params = null;

        for(WebActionListener listener: mWebActionList){
            if(listener != null && listener.getAction().equals(action)){
                if(listener.getDataType() != null) {
                    params = GsonUtils.build().fromJson(jsonString, listener.getDataType());
                }
                listener.execute(params);
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        mWebViewFragment.finishLoading();
    }

    private void navigate(long id, String type) {
        WebViewActivity.launchActivity(mWebViewFragment.getActivity(), id, mWebViewFragment.mUserId,
                mWebViewFragment.mUserName, type);
    }



    private void redirectUrl(String action, URI uri) {
        if (action.endsWith(WebConstans.Action.ACTION_DIARY)) {
            String query = uri.getQuery();
            String sub = query.substring(query.indexOf("=") + 1);
            long id = Long.valueOf(sub);
            navigate(id, "diary");
        }
    }
}
