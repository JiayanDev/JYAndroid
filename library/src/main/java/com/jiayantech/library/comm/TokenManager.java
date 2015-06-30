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
public class TokenManager {
    private static final String FILE_NAME = "token";
    public static final String KEY_TOKEN = "token";

    public static void putToken(String token) {
        new DataShared(BaseApplication.getContext(), FILE_NAME).put(KEY_TOKEN, token);
    }

    public static String getToken() {
        return new DataShared(BaseApplication.getContext(), FILE_NAME).get(KEY_TOKEN);
    }

    public static boolean checkTokenWithTips() {
        if (TextUtils.isEmpty(getToken())) {
            ToastUtil.showMessage("Not logined");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyToken() {
        return TextUtils.isEmpty(getToken());
    }
}
