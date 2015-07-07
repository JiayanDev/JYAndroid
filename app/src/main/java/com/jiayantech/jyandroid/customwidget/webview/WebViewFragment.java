package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseFragment;

/**
 * Created by liangzili on 15/7/7.
 */
public class WebViewFragment extends BaseFragment{
    public static final String TAG = WebViewFragment.class.getSimpleName();

    public static final String TYPE_EVENT = "event";
    public static final String TYPE_TOPIC = "topic";
    public static final String TYPE_DIARY = "diary";

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TYPE = "type";

    protected View mView;
    protected FrameLayout mNativeLayout;
    protected WebView mWebView;
    protected String mType;
    protected long mId;

    public static WebViewFragment newInstance(long id, String type){
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);
        args.putString(EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(EXTRA_TYPE);
        mId = getArguments().getLong(EXTRA_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview, container, false);
        mNativeLayout = (FrameLayout)mView.findViewById(R.id.layout_native);
        mWebView = (WebView)mView.findViewById(R.id.web);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setWebChromeClient(WebChromeClient client){
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(client);
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        
    }
}
