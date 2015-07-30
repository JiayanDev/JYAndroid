package com.jiayantech.jyandroid.customwidget.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.model.web.JsCallPlayImage;
import com.jiayantech.jyandroid.model.web.JsCallReply;
import com.jiayantech.jyandroid.model.web.JsCallScroll;
import com.jiayantech.jyandroid.model.web.JsCallSetTitle;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.utils.ToastUtil;

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
        mWebViewFragment.finishLoading();
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
        navigate(id, WebViewFragment.TYPE_DIARY, PostDetailActivity.class);
    }

    protected void onJsCallNativeSetTitle(String title) {
        mWebViewFragment.getActivity().setTitle(title);
    }

    protected void onJsCallNativeNavigateToDiaryHeader(long id) {
        navigate(id, WebViewFragment.TYPE_DIARY_HEADER, PostDetailActivity.class);
    }

    private void navigate(long id, String type, Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(mWebViewFragment.getActivity(), clazz);
        intent.putExtra(WebViewFragment.EXTRA_ID, id);
        intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
        intent.putExtra(WebViewFragment.EXTRA_USER_ID, mWebViewFragment.mUserId);
        intent.putExtra(WebViewFragment.EXTRA_USERNAME, mWebViewFragment.mUserName);
        mWebViewFragment.getActivity().startActivity(intent);
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
        }
    }

    private void redirectUrl(String action, URI uri) {

        if (action.endsWith(WebViewFragment.ACTION_DIARY_HEADER)) {
            String query = uri.getQuery();
            String sub = query.substring(query.indexOf("=") + 1);
            long id = Long.valueOf(sub);
            onJsCallNativeNavigateToDiaryHeader(id);
        } else if (action.endsWith(WebViewFragment.ACTION_DIARY)) {
            String query = uri.getQuery();
            String sub = query.substring(query.indexOf("=") + 1);
            long id = Long.valueOf(sub);
            onJsCallNativeNavigateToDiary(id);
        }
    }
}
