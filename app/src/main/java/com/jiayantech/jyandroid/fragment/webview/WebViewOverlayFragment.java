package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.widget.NotifyingScrollView;

/**
 * Created by liangzili on 15/9/14.
 */
public class WebViewOverlayFragment extends WebViewFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview_overlay, container, false);
        mScrollView = (NotifyingScrollView)mView.findViewById(R.id.layout_scroll);
        initView(inflater);
        return mView;
    }

    @Override
    protected String onGetUrl() {
        return null;
    }

    @Override
    protected String onGetUrlParams() {
        return null;
    }

    @Override
    protected String onSetTitle() {
        return null;
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        return null;
    }

    @Override
    protected View onBindHeaderLayout(LayoutInflater inflater) {
        return null;
    }
}
