package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.library.http.HttpReq;

import java.util.Map;

/**
 * Created by liangzili on 15/9/11.
 */
public class EventIntroFragment extends WebViewFragment{
    public static EventIntroFragment newInstance(long id){
        EventIntroFragment fragment = new EventIntroFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_EVENT_INTRO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String onGetUrl() {
        return WebConstans.BASE_URL + WebConstans.Action.ACTION_EVENT_INTRO;
    }

    @Override
    protected String onGetUrlParams() {
        Map<String, String> params = new ArrayMap<>();
        params.put("id", String.valueOf(mId));
        return HttpReq.encodeParameters(params, "utf-8");
    }

    @Override
    protected String onSetTitle() {
        return "活动详情";
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        return null;
    }
}
