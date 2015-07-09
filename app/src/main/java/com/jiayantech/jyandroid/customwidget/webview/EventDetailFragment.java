package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;

/**
 * Created by liangzili on 15/7/9.
 */
public class EventDetailFragment extends WebViewFragment{
    private Button mApplyButton;

    public static EventDetailFragment newInstance(long id, String type){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putString(WebViewFragment.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_apply_event, null);
        mApplyButton = (Button)view.findViewById(R.id.btn_apply);
        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    protected BaseWebChromeClient onSetWebChromeClient() {
        return null;
    }

    @Override
    protected JavascriptInterface onAddJavascriptInterface() {
        return null;
    }
}
