package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/7/9.
 */
public class HelpFragment extends WebViewFragment {

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, 0);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_HELP);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        finishLoading();
    }

    @Override
    protected String onGetUrl() {
        return WebConstans.BASE_URL + WebConstans.Action.ACTION_HELP;
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

    @Override
    public void onResume() {
        super.onResume();
        ToastUtil.showMessage(String.format("eventId is %d", mId));
    }
}
