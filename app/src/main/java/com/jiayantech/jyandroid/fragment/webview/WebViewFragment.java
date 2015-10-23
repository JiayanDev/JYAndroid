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
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.jyandroid.model.web.JsCallPlayImage;
import com.jiayantech.jyandroid.model.web.JsCallShareDetail;
import com.jiayantech.jyandroid.model.web.JsCallUserInfo;
import com.jiayantech.jyandroid.model.web.UserInfo;
import com.jiayantech.jyandroid.widget.NotifyingScrollView;
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
    protected NotifyingScrollView mScrollView;
    protected FrameLayout mBottomLayout;
    protected FrameLayout mHeaderLayout;
    protected WebView mWebView;

    protected String mType;
    protected long mId;

    protected String mUrl;

    //share content
    protected String mShareTitle;
    protected String mShareContent;
    protected String mShareThumbnail;

    private boolean bEnableShare = true;

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

    /**
     * 抽象方法，返回Url
     *
     * @return
     */
    abstract protected String onGetUrl();

    /**
     * 抽象方法，返回url的query参数
     *
     * @return
     */
    abstract protected String onGetUrlParams();

    /**
     * 抽象方法，返回标题
     *
     * @return
     */
    abstract protected String onSetTitle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_webview, container, false);
        initView(inflater);
        return mView;
    }

    protected void initView(LayoutInflater inflater) {
        mContentLayout = (LinearLayout) mView.findViewById(R.id.layout_content);
        mLoadingLayout = (RelativeLayout) mView.findViewById(R.id.layout_loading);
        //mScrollView = (NotifyingScrollView)mView.findViewById(R.id.layout_scroll);
        mBottomLayout = (FrameLayout) mView.findViewById(R.id.layout_bottom);
        mHeaderLayout = (FrameLayout) mView.findViewById(R.id.layout_header);
        mWebView = (WebView) mView.findViewById(R.id.web);

        View bottomView = onBindBottomLayout(inflater);
        if (bottomView != null) {
            mBottomLayout.addView(bottomView);
        }

        View headerView = onBindHeaderLayout(inflater);
        if (headerView != null) {
            mHeaderLayout.addView(headerView);
        }

        initWebView();
    }

    private void initWebView() {
        mWebView.setWebViewClient(onSetWebViewClient());
        mWebView.setWebChromeClient(onSetWebChromeClient());

        mWebView.setVerticalScrollBarEnabled(false);

        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(false);
        //允许执行javascript
        settings.setJavaScriptEnabled(true);
        //开启Dom Storage API功能
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //设置WebView请求的UserAgent
        settings.setUserAgentString(System.getProperty("http.agent") + " jiayantech");
        //开启Database Storage API功能
        settings.setDatabaseEnabled(true);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.loadUrl(mUrl);
        //finishLoading();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSharePanel != null && mSharePanel.isShowing()) {
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

    protected abstract View onBindHeaderLayout(LayoutInflater inflater);

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_share);
        item.setVisible(bEnableShare);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
//            if (mSharePanel == null) {
//                mSharePanel = new SharePanel(getActivity(), mUrl, onGetShareTitle(),
//                        onGetShareThumbnail(), onGetShareContent());
//            }
            mSharePanel = new SharePanel(getActivity(), mUrl, onGetShareTitle(),
                        onGetShareThumbnail(), onGetShareContent());
            if (!mSharePanel.isShowing()) {
                mSharePanel.showAtLocation(mWebView, Gravity.CENTER, 0, 0);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void callJsMethod(String method, String params) {
        JsNativeBiz.callJsMethod(method, params, mWebView);
    }

    protected void onAddWebActionListener(BaseWebViewClient client) {
        //监听页面加载完成的回调
        client.addActionListener(new WebActionListener(JsNativeBiz.ACTION_HIDE_LOADING, null) {
            @Override
            public void execute(BaseJsCall data) {
                finishLoading();
            }
        });

//        //监听web页面设置title的回调
//        client.addActionListener(new WebActionListener<JsCallSetTitle>
//                (JsNativeBiz.ACTION_SET_NAVIGATION_BAR_TITLE, JsCallSetTitle.class) {
//            @Override
//            public void execute(JsCallSetTitle data) {
//                getActivity().setTitle(data.data.title);
//            }
//        });

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
                UserInfo info = new UserInfo(AppInitManger.sAppInit);
                callJsMethod(data.success, info.toString());
            }
        });

        //获取web详情页分享信息
        client.addActionListener(new WebActionListener<JsCallShareDetail>(
                JsNativeBiz.ACTION_GET_SHARE_INFO, JsCallShareDetail.class) {
            @Override
            public void execute(JsCallShareDetail data) {
                mShareContent = data.data.content;
                mShareTitle = data.data.title;
                mShareThumbnail = data.data.thumbnail;
            }
        });
    }

    protected void onAddWebRedirectListener(BaseWebViewClient client) {

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


    public boolean isSharePanelShowing() {
        return mSharePanel != null && mSharePanel.isShowing();
    }

    public void dismissSharePanel() {
        if (mSharePanel != null && mSharePanel.isShowing()) {
            mSharePanel.dismiss();
        }
    }

    public void onEvent(ShareFinishEvent event) {
//       if(mSharePanel != null && mSharePanel.isShowing()){
//           mSharePanel.dismiss();
//       }
    }

    public void enableShare(boolean flag) {
        bEnableShare = flag;
        getActivity().invalidateOptionsMenu();
    }

    public String onGetShareTitle() {
        if (mShareTitle != null) {
            return mShareTitle;
        }
        if (getActivity().getTitle() != null) {
            return getActivity().getTitle().toString();
        } else {
            return getString(R.string.app_name);
        }
    }

    public String onGetShareContent(){
        if(mShareContent != null){
            return mShareContent;
        }
        return onGetShareTitle();
    }

    public String onGetShareThumbnail() {
        return mShareThumbnail;
    }
}
