package com.jiayantech.jyandroid.fragment.webview;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.activity.WebViewActivityOverlay;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;

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
    private List<WebRedirectListener> mWebRedirectList = new ArrayList<>();

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
            return true;
        } else {
            //URL监听跳转
            Uri uri = Uri.parse(url);
            if(uri.getHost().equals(Uri.parse(WebConstans.BASE_URL).getHost())) {
                String action = uri.getPath();
                return redirectUrl(action, uri);
            }
            return false;

        }
    }

    public void addActionListener(WebActionListener listener){
        mWebActionList.add(listener);
    }

    public void addRedirectListener(WebRedirectListener listener){
        mWebRedirectList.add(listener);
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
        if(type == WebConstans.Type.TYPE_PERSONAL_PAGE){
            WebViewActivityOverlay.launchActivity(mWebViewFragment.getActivity(), id, type);
        }else {
            WebViewActivity.launchActivity(mWebViewFragment.getActivity(), id, type);
        }
    }



    private boolean redirectUrl(String action, Uri uri) {
        long id;
        try {
            id = Long.valueOf(uri.getQueryParameter("id"));
        }catch (NumberFormatException e){
            e.printStackTrace();
            id = 0;
        }catch (UnsupportedOperationException e){
            e.printStackTrace();
            id = 0;
        }
        String type = null;
        switch (uri.getPath()){
            case WebConstans.Action.ACTION_DIARY:
                type = WebConstans.Type.TYPE_DIARY;
                break;
            case WebConstans.Action.ACTION_TOPIC:
                type = WebConstans.Type.TYPE_TOPIC;
                break;
            case WebConstans.Action.ACTION_EVENT:
                type = WebConstans.Type.TYPE_EVENT;
                break;
            case WebConstans.Action.ACTION_PERSONAL_PAGE:
                type = WebConstans.Type.TYPE_PERSONAL_PAGE;
                break;
            case WebConstans.Action.ACTION_EVENT_INTRO:
                type = WebConstans.Type.TYPE_EVENT_INTRO;
                break;
            case WebConstans.Action.ACTION_APPLYMENT_LIST:
                type = WebConstans.Type.TYPE_APPLYMENT_LIST;
                break;
        }
        if(type != null){
            navigate(id, type);
            return true;
        }else{
            return false;
        }
    }
}
