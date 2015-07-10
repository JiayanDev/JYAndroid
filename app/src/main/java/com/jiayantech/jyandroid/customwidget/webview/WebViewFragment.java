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

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.utils.ToastUtil;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * Created by liangzili on 15/7/7.
 */
public abstract class WebViewFragment extends BaseFragment{
    public static final String TAG = WebViewFragment.class.getSimpleName();

    public static final String BASE_URL = "http://app.jiayantech.com/app/html/";
    public static final String ACTION_DIARY = "diary.html";
    public static final String ACTION_DIARY_HEADER = "diaryHeader.html";

    public static final String TYPE_EVENT = "event";
    public static final String TYPE_TOPIC = "topic";
    public static final String TYPE_DIARY = "diary";
    public static final String TYPE_DIARY_HEADER = "diary_header";

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TYPE = "type";

    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    protected View mView;
    protected FrameLayout mNativeLayout;
    protected WebView mWebView;
    protected String mType;
    protected long mId;
    protected String mUrl = "http://www.baidu.com";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(EXTRA_TYPE);
        mId = getArguments().getLong(EXTRA_ID);

        setUrl();
    }

    private void setUrl(){
        if(mType.equals(TYPE_DIARY)){
            mUrl = BASE_URL + ACTION_DIARY;
        }else if(mType.equals(TYPE_DIARY_HEADER)){
            mUrl = BASE_URL + ACTION_DIARY_HEADER;
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
        mNativeLayout = (FrameLayout)mView.findViewById(R.id.layout_native);
        mWebView = (WebView)mView.findViewById(R.id.web);

        View bottomView = onBindBottomLayout(inflater);
        if(bottomView != null){
            mNativeLayout.addView(bottomView);
        }

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(onSetWebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mWebView.loadUrl("http://www.baidu.com");
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ToastUtil.showMessage(getActivity(), "the postId is " + mId);

        String content = getString(R.string.post_share_text, new Object[] {"我爱你", "http://www.baidu.com"});
        mController.setShareContent(content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.destroy();
    }

    protected abstract View onBindBottomLayout(LayoutInflater inflater);

    protected abstract BaseWebChromeClient onSetWebChromeClient();

    protected abstract JavascriptInterface onAddJavascriptInterface();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            mController.openShare(getActivity(), false);
        }
        return super.onOptionsItemSelected(item);
    }
}
