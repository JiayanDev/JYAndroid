package com.jiayantech.jyandroid.fragment.webview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.jiayantech.jyandroid.activity.PediaActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.model.web.JsCallSetTitle;
import com.jiayantech.library.utils.LogUtil;

/**
 * Created by liangzili on 15/12/1.
 */
public class PediaFragment extends WebViewFragment{
    public static final String EXTRA_URL = "url";

    public static Fragment newInstance(String url){
        Fragment fragment = new PediaFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(EXTRA_URL);
        if(mUrl == null){
            getActivity().finish();
        }

    }

    @Override
    protected String onGetUrl() {
        return mUrl;
    }

    @Override
    protected String onGetUrlParams() {
        return "";
    }

    @Override
    protected String onSetTitle() {
        return "";
    }

    @Override
    protected BaseWebViewClient onSetWebViewClient() {
        BaseWebViewClient webViewClient = new BaseWebViewClient(this) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                LogUtil.d("PediaFragment", "loading url: " + uri);
                if (uri.getHost().startsWith(Uri.parse(WebConstans.PEDIA_BASE_URL).getHost())) {
                    Intent intent = new Intent(getActivity(), PediaActivity.class);
                    intent.putExtra(PediaFragment.EXTRA_URL, url);
                    getActivity().startActivity(intent);
                    return true;
                } else {
                    if (url.startsWith(JS_PREFIX)) {
                        //Js调用Native
                        url = Uri.decode(url);
                        String action = JsNativeBiz.getJsAction(url);
                        String jsonString = url.substring(url.indexOf("{"), url.lastIndexOf("}") + 1);
                        if(action.equals(JsNativeBiz.ACTION_SET_NAVIGATION_BAR_TITLE)) {
                            callMethod(action, jsonString);
                        }
                        return true;
                    }
                    return false;
                }
            }
        };
        //onAddWebActionListener(webViewClient);
                //监听web页面设置title的回调
        webViewClient.addActionListener(new WebActionListener<JsCallSetTitle>
                (JsNativeBiz.ACTION_SET_NAVIGATION_BAR_TITLE, JsCallSetTitle.class) {
            @Override
            public void execute(JsCallSetTitle data) {
                getActivity().setTitle(data.data.title);
            }
        });
        return webViewClient;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_webview, container, false);
////        mWebView = (WebView)view.findViewById(R.id.web);
////        initWebView();
////        finishLoading();
////        return view;
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        finishLoading();
        enableShare(false);
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
