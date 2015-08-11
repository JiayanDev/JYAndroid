package com.jiayantech.jyandroid.fragment.webview;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.ApplyEventActivity;
import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.ApplyEventFragment;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.model.web.JsCallApplyEvent;
import com.jiayantech.jyandroid.model.web.JsCallPlayImage;
import com.jiayantech.jyandroid.model.web.JsCallReply;
import com.jiayantech.jyandroid.model.web.JsCallSetTitle;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by liangzili on 15/7/16.
 * BaseWebViewClient 处理Js调用Native，页面跳转等事件
 */
public class BaseWebViewClient extends WebViewClient {
    private static final String JS_PREFIX = "jiayan://js_call_native?";

    private WebViewFragment mWebViewFragment;

    public BaseWebViewClient(WebViewFragment fragment) {
        mWebViewFragment = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith(JS_PREFIX)) {
            //Js调用Native
            String action = JsNativeBiz.getJsAction(url);
            callNativeMethod(action, url);
            return super.shouldOverrideUrlLoading(view, url);
        } else {
            //URL监听跳转
            URI uri = URI.create(url);
            String action = uri.getPath();
            redirectUrl(action, uri);

            return true;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        mWebViewFragment.finishLoading();
    }

    protected void onJsCallNativeOpenCommentPanel(JsCallReply call) {
        CommentFragment fragment = CommentFragment.newInstance(call.data.subjectId,
                call.data.subject, call.data.toUserId, call.data.toUserName);
        fragment.setTargetFragment(mWebViewFragment, WebViewFragment.REQUEST_CODE_COMMENT);
        fragment.show(mWebViewFragment.getActivity().getSupportFragmentManager(), "comment");
    }

    protected void onJsCallNativeTest(JsCallReply call) {
    }


    protected void onJsCallNativePlayImage(int index, ArrayList<String> images) {
        PhotosActivity.start(mWebViewFragment.getActivity(), "", images, index);
    }

    protected void onJsCallNativeNavigateToDiary(long id) {
        navigate(id, WebConstans.Type.TYPE_DIARY);
    }

    protected void onJsCallNativeSetTitle(String title) {
        mWebViewFragment.getActivity().setTitle(title);
    }


    protected void onJsCallApplyEvent(long id){
        Intent intent = new Intent(mWebViewFragment.getActivity(), ApplyEventActivity.class);
        intent.putExtra(ApplyEventFragment.EVENT_ID, id);
        mWebViewFragment.getActivity().startActivity(intent);
    }

    private void navigate(long id, String type) {
        WebViewActivity.lauchActivity(mWebViewFragment.getActivity(), id, mWebViewFragment.mUserId,
                mWebViewFragment.mUserName, type);
    }

    private void callNativeMethod(String action, String url) {
        switch (action) {
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

            case JsNativeBiz.ACTION_HIDE_LOADING:
                mWebViewFragment.finishLoading();
                break;

            case JsNativeBiz.ACTION_APPLY_EVENT:
                JsCallApplyEvent event = JsNativeBiz.parse(url, JsCallApplyEvent.class);
                onJsCallApplyEvent(event.data.id);

        }
    }

    private void redirectUrl(String action, URI uri) {

//        if (action.endsWith(WebViewFragment.ACTION_DIARY_HEADER)) {
//            String query = uri.getQuery();
//            String sub = query.substring(query.indexOf("=") + 1);
//            long id = Long.valueOf(sub);
//            //onJsCallNativeNavigateToDiaryHeader(id);
//        } else if (action.endsWith(WebViewFragment.ACTION_DIARY)) {
//            String query = uri.getQuery();
//            String sub = query.substring(query.indexOf("=") + 1);
//            long id = Long.valueOf(sub);
//            onJsCallNativeNavigateToDiary(id);
        if (action.endsWith(WebConstans.Action.ACTION_DIARY)) {
            String query = uri.getQuery();
            String sub = query.substring(query.indexOf("=") + 1);
            long id = Long.valueOf(sub);
            onJsCallNativeNavigateToDiary(id);
        }
//        }
    }
}
