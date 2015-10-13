package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.http.HttpReq;

import java.util.Map;

/**
 * Created by liangzili on 15/10/12.
 */
public class ApplymentListFragment extends WebViewFragment{

    public static ApplymentListFragment newInstance(long id){
        ApplymentListFragment fragment = new ApplymentListFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        //args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
        //args.putString(WebViewFragment.EXTRA_USERNAME, userName);
        args.putString(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_APPLYMENT_LIST);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        enableShare(false);
    }

    @Override
    protected String onGetUrl() {
        return WebConstans.BASE_URL + WebConstans.Action.ACTION_APPLYMENT_LIST;
    }

    @Override
    protected String onGetUrlParams() {
        Map<String, String> params = new ArrayMap<>();
        params.put("id", String.valueOf(mId));
        return HttpReq.encodeParameters(params, "utf-8");
    }

    @Override
    protected String onSetTitle() {
        return getString(R.string.title_applyment_list);
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
