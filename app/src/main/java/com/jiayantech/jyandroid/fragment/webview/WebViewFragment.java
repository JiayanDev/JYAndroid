package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.eventbus.ShareFinishEvent;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.jyandroid.model.web.JsCallPlayImage;
import com.jiayantech.jyandroid.model.web.JsCallSetTitle;
import com.jiayantech.jyandroid.model.web.JsCallUserInfo;
import com.jiayantech.jyandroid.widget.SharePanel;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.utils.LogUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/7.
 */
public abstract class WebViewFragment extends BaseFragment {
    public static final String TAG = "WebViewFragment";

    public static final int REQUEST_CODE_COMMENT = 1;

    /* webview显示内容的具体信息
     * EXTRA_ID 内容的ID
     * EXTRA_TYPE 内容的类型
     * */
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TYPE = "type";

    private LinearLayout mContentLayout;
    private RelativeLayout mLoadingLayout;

    private SharePanel mSharePanel;

    protected View mView;
    protected FrameLayout mNativeLayout;
    protected WebView mWebView;

    protected String mType;
    protected long mId;

    protected String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(EXTRA_ID);
        mType = getArguments().getString(EXTRA_TYPE);

        mUrl = onGetUrl() + "?" + onGetUrlParams();

        LogUtil.i(TAG, "loading url:" + mUrl);

        getActivity().setTitle(onSetTitle());

        EventBus.getDefault().register(this);
    }

    abstract protected String onGetUrl();

    abstract protected String onGetUrlParams();

    abstract protected String onSetTitle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview, container, false);

        mContentLayout = (LinearLayout) mView.findViewById(R.id.layout_content);
        mLoadingLayout = (RelativeLayout) mView.findViewById(R.id.layout_loading);
        mNativeLayout = (FrameLayout) mView.findViewById(R.id.layout_native);
        mWebView = (WebView) mView.findViewById(R.id.web);

        View bottomView = onBindBottomLayout(inflater);
        if (bottomView != null) {
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
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mSharePanel != null && mSharePanel.isShowing()){
            mSharePanel.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mWebView != null) {
            mWebView.destroy();
        }

        if (mSharePanel != null && mSharePanel.isShowing()) {
            mSharePanel.dismiss();
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
        if (item.getItemId() == R.id.action_share) {
//            ToastUtil.showMessage("点击分享: " + mUrl);
            if (mSharePanel == null) {
                CharSequence titleChar = getActivity().getTitle();
                String title;
                if (titleChar != null) {
                    title = titleChar.toString();
                } else {
                    title = getString(R.string.app_name);
                }
                mSharePanel = new SharePanel(getActivity(), mUrl, title);
            }
            if (!mSharePanel.isShowing()) {
                mSharePanel.showAtLocation(mWebView, Gravity.CENTER, 0, 0);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void callJsMethod(String method, String params) {
        JsNativeBiz.callJsMethod(method, params, mWebView);
    }

    public void onAddWebActionListener(BaseWebViewClient client){
        //监听页面加载完成的回调
        client.addActionListener(new WebActionListener(JsNativeBiz.ACTION_HIDE_LOADING, null) {
            @Override
            public void execute(BaseJsCall data) {
                finishLoading();
            }
        });

        //监听web页面设置title的回调
        client.addActionListener(new WebActionListener<JsCallSetTitle>
                (JsNativeBiz.ACTION_SET_NAVIGATION_BAR_TITLE, JsCallSetTitle.class) {
            @Override
            public void execute(JsCallSetTitle data) {
                getActivity().setTitle(data.data.title);
            }
        });

        //监听web页面查看图片的回调
        client.addActionListener(new WebActionListener<JsCallPlayImage>(
                JsNativeBiz.ACTION_PLAY_IMAGE, JsCallPlayImage.class) {
            @Override
            public void execute(JsCallPlayImage data) {
                PhotosActivity.start(getActivity(), "", data.data.imgList, data.data.defaultIndex);
            }
        });

        //监听web页面获取用户信息
        client.addActionListener(new WebActionListener<JsCallUserInfo>(
                JsNativeBiz.ACTION_GET_USERINFO, JsCallUserInfo.class) {
            @Override
            public void execute(JsCallUserInfo data) {

            }
        });
    }


    public void finishLoading() {
        mContentLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
    }

    protected BaseWebChromeClient onSetWebChromeClient() {
        return new BaseWebChromeClient();
    }

    protected JavascriptInterface onAddJavascriptInterface() {
        return new JavascriptInterface();
    }

    protected BaseWebViewClient onSetWebViewClient() {
        BaseWebViewClient client = new BaseWebViewClient(this);
        onAddWebActionListener(client);
        return client;
    }


    public boolean isSharePanelShowing(){
        return mSharePanel != null && mSharePanel.isShowing();
    }

    public void dismissSharePanel(){
        if(mSharePanel != null && mSharePanel.isShowing()){
            mSharePanel.dismiss();
        }
    }

    public void onEvent(ShareFinishEvent event){
//       if(mSharePanel != null && mSharePanel.isShowing()){
//           mSharePanel.dismiss();
//       }
    }
}
