package com.jiayantech.jyandroid.customwidget.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.ApplyEventActivity;
import com.jiayantech.jyandroid.fragment.ApplyEventFragment;

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
                Intent intent = new Intent(getActivity(), ApplyEventActivity.class);
                intent.putExtra(ApplyEventFragment.EVENT_ID, mId);
                startActivity(intent);
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

    @Override
    protected WebViewClient onSetWebViewClient() {
        return null;
    }
}
