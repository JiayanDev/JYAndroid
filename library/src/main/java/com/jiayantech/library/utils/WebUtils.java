package com.jiayantech.library.utils;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.jiayantech.library.base.BaseApplication;

/**
 * Created by liangzili on 15/10/7.
 */
public class WebUtils {
    /**
     * 清空WebView的缓存
     */
    public static void clearCache(){
        CookieSyncManager cookieSyncManager = CookieSyncManager.
                createInstance(BaseApplication.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
}
