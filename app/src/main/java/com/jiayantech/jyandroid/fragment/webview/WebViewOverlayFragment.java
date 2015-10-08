package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PublishDiaryActivity;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.widget.NotifyingScrollView;

/**
 * Created by liangzili on 15/9/14.
 */
public class WebViewOverlayFragment extends WebViewFragment{
    private FloatingActionButton mPublishButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview_overlay, container, false);
        mScrollView = (NotifyingScrollView)mView.findViewById(R.id.layout_scroll);
        initView(inflater);
        return mView;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPublishButton = (FloatingActionButton) mView.findViewById(R.id.button_publish);
        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PublishDiaryActivity.class);
            }
        });
    }

    @Override
    public void finishLoading() {
        super.finishLoading();
        if(mId == AppInitManger.getUserId()){
            mPublishButton.setVisibility(View.VISIBLE);
        }
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
