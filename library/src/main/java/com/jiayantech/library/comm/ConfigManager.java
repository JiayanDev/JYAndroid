package com.jiayantech.library.comm;

import android.text.TextUtils;

import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ConfigManager {
    private static final String FILE_NAME = "config";

    public static final String KEY_TOKEN = "AUTHORIZATION";

    public static void putConfig(String key, String value) {
        getDataShared().put(key, value);
    }

    public static String getConfig(String key) {
        return getDataShared().get(key);
    }

    public static String getConfig(String key, String defValue) {
        return getDataShared().get(key, defValue);
    }

    public static void putToken(String token) {
        getDataShared().put(KEY_TOKEN, token);
    }

    public static String getToken() {
        return getDataShared().get(KEY_TOKEN);
    }

    private static DataShared getDataShared() {
        return new DataShared(BaseApplication.getContext(), FILE_NAME);
    }

    public static boolean checkTokenWithTips() {
        if (TextUtils.isEmpty(getToken())) {
            ToastUtil.showMessage("Not logined");
            return false;
        }
        return true;
    }

    public static boolean checkEmptyToken() {
        return TextUtils.isEmpty(getToken());
    }
}
