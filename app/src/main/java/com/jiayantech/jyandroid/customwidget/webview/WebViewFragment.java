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

/**
 * Created by liangzili on 15/7/7.
 */
public abstract class WebViewFragment extends BaseFragment{
    public static final String TAG = WebViewFragment.class.getSimpleName();

    //public static final String BASE_URL = "http://app.jiayantech.com/app/htm/";
    public static final String BASE_URL = Property.getProperty("html.url");
    public static final String ACTION_DIARY = "diary.html";
    public static final String ACTION_DIARY_HEADER = "diaryheader.html";

    /* webview显示内容的类型 */
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_TOPIC = "topic";
    public static final String TYPE_DIARY = "diary";
    public static final String TYPE_DIARY_HEADER = "diary_header";

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

    protected String mUrl = "http://www.baidu.com";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(EXTRA_ID);
        mUserId = getArguments().getLong(EXTRA_USER_ID);
        mUserName = getArguments().getString(EXTRA_USERNAME);
        mType = getArguments().getString(EXTRA_TYPE);

        setUrl();
    }

    private void setUrl(){
//        if(mType.equals(TYPE_DIARY)){
//            mUrl = BASE_URL + ACTION_DIARY;
//        }else if(mType.equals(TYPE_DIARY_HEADER)){
//            mUrl = BASE_URL + ACTION_DIARY_HEADER;
//        }else if(mType.equals(TYPE_TOPIC)){
//            mUrl = BASE_URL + ACTION_DIARY;
//        }
        switch (mType.toString()){
            case TYPE_DIARY:
                mUrl = BASE_URL + ACTION_DIARY;
                break;
            case TYPE_DIARY_HEADER:
                mUrl = BASE_URL + ACTION_DIARY_HEADER;
                break;
            case TYPE_TOPIC:
                mUrl = BASE_URL + ACTION_DIARY;
                break;
            default:
                throw new IllegalArgumentException(String.format("type %s not supported.", mType));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(mUrl);
        sb.append("?");
        sb.append("id=");
        sb.append(mId);
        mUrl = sb.toString();
    }

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
        //ToastUtil.showMessage(getActivity(), "the postId is " + mId);

        //String content = getString(R.string.post_share_text, new Object[] {"我爱你", "http://www.baidu.com"});
        //mController.setShareContent(content);

        //ToastUtil.showMessage(getActivity(), mUrl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.destroy();
    }

    protected abstract View onBindBottomLayout(LayoutInflater inflater);

    protected abstract BaseWebChromeClient onSetWebChromeClient();

    protected abstract JavascriptInterface onAddJavascriptInterface();

    protected abstract WebViewClient onSetWebViewClient(

    );

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

    public void scrollToY(int y){

        //mWebView.scrollBy(0, y);
    }

    public void finishLoading(){
        mContentLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    interface onPageLoadingFinishListener{
        void onPageLoadFinish();
    }
}
