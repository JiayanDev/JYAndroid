package com.jiayantech.jyandroid.biz;

import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by janseon on 2015/6/30.
 *
 * @Description: 登录模块业务
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class UserBiz {
    /**
     * token过期
     */
    private static final int CODE_EXPIRE = -40;
    private static final String KEY_CONFIG_VERSION = "configVersion";

    private static final String MODEL = "user";

    private static final String ACTION_RIGISTER = MODEL + "/register";
    private static final String ACTION_QUICK_LOGIN = MODEL + "/quick_login";
    private static final String ACTION_LOGIN = MODEL + "/login";
    private static final String ACTION_BIND_ACCOUNT = MODEL + "/bind_account";
    private static final String ACTION_LOGOUT = MODEL + "/logout";

    /**
     * 注册
     *
     * @param l
     */
    private static void register(LoginResponseListener l) {
        HttpReq.post(ACTION_RIGISTER, null, l);
    }

    /**
     * 快速登录
     *
     * @param l
     */
    public static void quickLogin(LoginResponseListener l) {
        if (ConfigManager.checkEmptyToken()) {
            register(l);
        } else {
            String configVersion = ConfigManager.getConfig(KEY_CONFIG_VERSION, "0");
            HttpReq.post(ACTION_QUICK_LOGIN, HttpReq.getInitParams(KEY_CONFIG_VERSION, configVersion), l);
        }
    }

    /**
     * 用户名、密码登录
     *
     * @param name
     * @param psw
     * @param l
     */
    public static void login(String configVersion, String name, String psw, LoginResponseListener l) {
        Map<String, String> params = new HashMap<>();
        params.put("configVersion", configVersion);
        params.put("name", name);
        params.put("psw", psw);
        HttpReq.post(ACTION_LOGIN, params, l);
    }

    /**
     * 绑定帐户信息
     *
     * @param name
     * @param psw
     * @param l
     */
    public static void bindAccount(String name, String psw, ResponseListener<?> l) {
        if (!ConfigManager.checkTokenWithTips()) return;
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("psw", psw);
        HttpReq.post(ACTION_BIND_ACCOUNT, params, l);
    }

    public static void logout(ResponseListener<?> l) {
        if (!ConfigManager.checkTokenWithTips()) return;
        HttpReq.post(ACTION_LOGOUT, null, l);
    }

    /**
     *
     */
    public final static class LoginResponseListener extends ResponseListener<AppResponse<Login>> {
        private Runnable mRunnable;

        public LoginResponseListener() {
        }

        public LoginResponseListener setRunnable(Runnable r) {
            mRunnable = r;
            return this;
        }

        @Override
        public void onResponse(AppResponse<Login> response) {
            Login login = response.data;
            if (login.projectCategory != null) {
                UserManger.saveLogin(login);
                if (mRunnable != null) {
                    mRunnable.run();
                }
                return;
            }
            ConfigManager.putToken(login.token);
            quickLogin(this);
//            if (response.code == CODE_EXPIRE && mResponseListener != null && mConfigVersion != null) {
//                quickLogin(mConfigVersion, mResponseListener);
//            } else {
//                mResponseListener.onResponse(response);
//            }
        }
    }
}
