package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.biz.ShareBiz;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.comm.Property;
import com.jiayantech.library.utils.LogUtil;

/**
 * Created by liangzili on 15/7/7.
 */
public abstract class WebViewFragment extends BaseFragment{
    public static final String TAG = WebViewFragment.class.getSimpleName();

    public static final int REQUEST_CODE_COMMENT = 1;

    /* webview显示内容的具体信息
     * EXTRA_ID 内容的ID
     * EXTRA_TYPE 内容的类型
     * EXTRA_USERNAME 内容作者的用户名
     * EXTRA_USER_ID 内容作者的ID
     * */
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_USER_ID = "user_id";

    private LinearLayout mContentLayout;
    private RelativeLayout mLoadingLayout;

    protected View mView;
    protected FrameLayout mNativeLayout;
    protected WebView mWebView;

    protected String mType;
    protected long mId;
    protected String mUserName;
    protected long mUserId;

    protected String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(EXTRA_ID);
        mUserId = getArguments().getLong(EXTRA_USER_ID);
        mUserName = getArguments().getString(EXTRA_USERNAME);
        mType = getArguments().getString(EXTRA_TYPE);

        mUrl = onGetUrl() + "?" + onGetUrlParams();

        getActivity().setTitle(onSetTitle());
    }

    abstract protected String onGetUrl();
    abstract protected String onGetUrlParams();
    abstract protected String onSetTitle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview, container, false);

        mContentLayout = (LinearLayout)mView.findViewById(R.id.layout_content);
        mLoadingLayout = (RelativeLayout)mView.findViewById(R.id.layout_loading);
        mNativeLayout = (FrameLayout)mView.findViewById(R.id.layout_native);
        mWebView = (WebView)mView.findViewById(R.id.web);

        View bottomView = onBindBottomLayout(inflater);
        if(bottomView != null){
            mNativeLayout.addView(bottomView);
        }

        mWebView.setWebViewClient(onSetWebViewClient());
        mWebView.setWebChromeClient(onSetWebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);

        settings.setUserAgentString(settings.getUserAgentString() + " jiayantech");

        finishLoading();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView != null) {
            mWebView.destroy();
        }
    }

    protected abstract View onBindBottomLayout(LayoutInflater inflater);



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            ShareBiz.shareToWechat(mUrl, ShareBiz.WECHAT_TIMELINE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void callJsMethod(String method, String params){
        JsNativeBiz.callJsMethod(method, params, mWebView);
    }


    public void finishLoading(){
        mContentLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    protected BaseWebChromeClient onSetWebChromeClient(){
        return new BaseWebChromeClient();
    }

    protected JavascriptInterface onAddJavascriptInterface(){
        return new JavascriptInterface();
    }

    protected BaseWebViewClient onSetWebViewClient(){
        return new BaseWebViewClient(this);
    }
}
