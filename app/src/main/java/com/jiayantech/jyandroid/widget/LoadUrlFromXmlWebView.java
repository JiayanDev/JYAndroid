package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;

/**
 * Created by liangzili on 15/10/15.
 */
public class LoadUrlFromXmlWebView extends WebView {
    String mUrl;

    public LoadUrlFromXmlWebView(Context context) {
        super(context);
    }

    public LoadUrlFromXmlWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadUrlFromXmlWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.LoadUrlFromXmlWebView);
//            mUrl = a.getString(R.styleable.LoadUrlFromXmlWebView_url);
            mUrl = WebConstans.BASE_URL + WebConstans.Action.ACTION_ABOUT_ANGEL;
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadUrl(mUrl);
//            }
//        }, 2000);
        loadUrl(mUrl);
    }



}
