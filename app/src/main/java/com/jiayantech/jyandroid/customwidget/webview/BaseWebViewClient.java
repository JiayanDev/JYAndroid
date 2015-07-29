package com.jiayantech.jyandroid.customwidget.webview;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.model.web.JsCallPlayImage;
import com.jiayantech.jyandroid.model.web.JsCallReply;
import com.jiayantech.jyandroid.model.web.JsCallScroll;
import com.jiayantech.jyandroid.model.web.JsCallSetTitle;
import com.jiayantech.library.utils.ToastUtil;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by liangzili on 15/7/16.
 */
public class BaseWebViewClient extends WebViewClient{
    private WebViewFragment mWebViewFragment;
    public BaseWebViewClient(WebViewFragment fragment){
        mWebViewFragment = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.startsWith("jiayan://js_call_native?")){
            //native与Js相互调用
            String action = JsNativeBiz.getJsAction(url);
            //ToastUtil.showMessage(action);
            switch (action){
                case JsNativeBiz.ACTION_TEST:
                    JsCallReply test = JsNativeBiz.parse(url, JsCallReply.class);
                    onJsCallNativeTest(test);
                    break;
                case JsNativeBiz.ACTION_OPEN_COMMENT_PANEL:
                    JsCallReply call = JsNativeBiz.parse(url, JsCallReply.class);
                    onJsCallNativeOpenCommentPanel(call);
                    break;
                case JsNativeBiz.ACTION_PLAY_IMAGE:
                    JsCallPlayImage playImage = JsNativeBiz.parse(url, JsCallPlayImage.class);
                    onJsCallNativePlayImage(playImage.data.defaultIndex,
                            playImage.data.imgList);
                    break;
                case JsNativeBiz.ACTION_SET_NAVIGATION_BAR_TITLE:
                    JsCallSetTitle title = JsNativeBiz.parse(url, JsCallSetTitle.class);
                    onJsCallNativeSetTitle(title.data.title);
                    break;
                case JsNativeBiz.ACTION_SCROLL_BOTTOM_TO_POS_Y:
                    JsCallScroll scroll = JsNativeBiz.parse(url, JsCallScroll.class);
                    //ToastUtil.showMessage("YPosition: " + scroll.data.posY);
                    onJscallNativeScroll(scroll.data.posY);
                    break;

            }
            return super.shouldOverrideUrlLoading(view, url);
        }else{
            //URL监听跳转
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

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mWebViewFragment.finishLoading();
    }



    protected void onJsCallNativeOpenCommentPanel(JsCallReply call){
        CommentFragment fragment = CommentFragment.newInstance(call.data.subjectId,
                call.data.subject, call.data.toUserId, call.data.toUserName);
        fragment.setTargetFragment(mWebViewFragment, WebViewFragment.REQUEST_CODE_COMMENT);
        fragment.show(mWebViewFragment.getActivity().getSupportFragmentManager(), "comment");
    }

    protected void onJsCallNativeTest(JsCallReply call){

    }

    protected void onJsCallNativeNavigateToDiaryHeader(long id){

    }

    protected void onJsCallNativePlayImage(int index, ArrayList<String> images){
        PhotosActivity.start(mWebViewFragment.getActivity(), "", images, index);
    }

    protected void onJsCallNativeNavigateToDiary(long id){

    }

    protected void onJsCallNativeSetTitle(String title){
        mWebViewFragment.getActivity().setTitle(title);
    }

    protected void onJscallNativeScroll(int posY){

    }
}
