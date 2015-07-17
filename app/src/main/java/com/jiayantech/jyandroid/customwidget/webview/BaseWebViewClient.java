package com.jiayantech.jyandroid.customwidget.webview;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.PostActivity;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.model.web.ReplyJsCall;
import com.jiayantech.library.utils.ToastUtil;

import java.net.URI;
import java.util.List;

/**
 * Created by liangzili on 15/7/16.
 */
public class BaseWebViewClient extends WebViewClient{
    private Context mContext;
    public BaseWebViewClient(Context context){
        mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.startsWith("jiayan://js_call_native?")){
            //native与Js相互调用
            String action = JsNativeBiz.getJsAction(url);
            ToastUtil.showMessage(action);
            switch (action){
                case JsNativeBiz.ACTION_TEST:
                    ReplyJsCall test = JsNativeBiz.parse(url, ReplyJsCall.class);
                    onJsCallNativeTest(test);
                    break;
                case JsNativeBiz.ACTION_OPEN_COMMENT_PANEL:
                    ReplyJsCall call = JsNativeBiz.parse(url, ReplyJsCall.class);
                    onJsCallNativeOpenCommentPanel(call);
                    break;
                case JsNativeBiz.ACTION_PLAY_IMAGE:


            }
            return super.shouldOverrideUrlLoading(view, url);
        }else{
            URI uri = URI.create(url);
            String action = uri.getPath();
            if(uri.getPath().endsWith(WebViewFragment.ACTION_DIARY_HEADER)){
                String query = uri.getQuery();
                String sub = query.substring(query.indexOf("=") + 1);
                long id = Long.valueOf(sub);
                onJsCallNativeNavigateToDiaryHeader(id);
            }else if (uri.getPath().endsWith(WebViewFragment.ACTION_DIARY)){
                String query = uri.getQuery();
                String sub = query.substring(query.indexOf("=") + 1);
                long id = Long.valueOf(sub);
                onJsCallNativeNavigateToDiary(id);
            }
            return true;
        }


    }

    protected void onJsCallNativeOpenCommentPanel(ReplyJsCall call){

    }

    protected void onJsCallNativeTest(ReplyJsCall call){

    }

    protected void onJsCallNativeNavigateToDiaryHeader(long id){

    }

    protected void onJsCallNativeViewImage(List<String> images){

    }

    protected void onJsCallNativeNavigateToDiary(long id){

    }
}
