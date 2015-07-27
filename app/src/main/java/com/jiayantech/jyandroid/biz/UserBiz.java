package com.jiayantech.jyandroid.biz;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;

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
    public static final String KEY_PHONE = "phoneNum";
    public static final String KEY_PHONE_CODE_RESPONSE = "confirmId";
    public static final String KEY_PHONE_CODE_CONFIRM_RESPONSE = "receipt";


    public static final String SOCIAL_CODE = "social_code";
    public static final String SOCIAL_CODE_TYPE = "social_code_type";
    public static final String KEY_SOCIAL_COED_WECHAT = "wxCode";

    private static final String MODEL = "user";

    private static final String ACTION_PHONE_SEND_CODE = MODEL + "/confirm_code/code";
    private static final String ACTION_PHONE_CONFIRM_CODE = MODEL + "/confirm_code/confirm";

    private static final String ACTION_REGISTER = MODEL + "/register";
    private static final String ACTION_QUICK_LOGIN = MODEL + "/quick_login";
    private static final String ACTION_LOGIN = MODEL + "/login";
    private static final String ACTION_BIND_ACCOUNT = MODEL + "/bind_account";
    private static final String ACTION_LOGOUT = MODEL + "/logout";
    private static final String ACTION_DETAIL = MODEL + "/detail";
    private static final String ACTION_UPDATE = MODEL + "/update";


    /**
     * 注册
     *
     * @param l
     */
    private static void register(LoginResponseListener l) {
        HttpReq.post(ACTION_REGISTER, null, l);
    }

    /**
     * 第三方注册
     *
     * @param receipt
     * @param phoneNum
     * @param social_code_type
     * @param social_code
     * @param psw
     * @param l
     */
    public static void register(String receipt, String phoneNum, String social_code_type, String social_code, String psw, LoginResponseListener l) {
        Map<String, String> params = HttpReq.getInitParams(KEY_PHONE_CODE_CONFIRM_RESPONSE, receipt);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, social_code_type, social_code);
        HttpReq.putParams(params, "psw", psw);
        HttpReq.post(ACTION_REGISTER, params, l);
    }

    /**
     * 手机注册
     *
     * @param receipt
     * @param phoneNum
     * @param name
     * @param gender
     * @param psw
     * @param l
     */
    public static void register(String receipt, String phoneNum, String name, int gender, String psw, LoginResponseListener l) {
        Map<String, String> params = HttpReq.getInitParams(KEY_PHONE_CODE_CONFIRM_RESPONSE, receipt);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "gender", gender);
        HttpReq.putParams(params, "psw", psw);
        HttpReq.post(ACTION_REGISTER, params, l);
    }

    public static void sendPhoneCode(String phoneNum, ResponseListener<?> l) {
        HttpReq.get(ACTION_PHONE_SEND_CODE, HttpReq.getInitParams(KEY_PHONE, phoneNum), l);
    }

    public static void confirmPhoneCode(String social_code_type, String social_code, String phoneCodeResponse, String code, ResponseListener<?> l) {
        Map<String, String> params = HttpReq.getInitParams("code", code);
        HttpReq.putParams(params, social_code_type, social_code);
        HttpReq.putParams(params, KEY_PHONE_CODE_RESPONSE, phoneCodeResponse);
        HttpReq.post(ACTION_PHONE_CONFIRM_CODE, params, l);
    }

    /**
     * 快速登录
     *
     * @param l
     */
    public static void quickLogin(ResponseListener<?> l) {
//        if (ConfigManager.checkEmptyToken()) {
//            register(l);
//        } else {
//            postLogin(new HashMap<String, String>(), l);
//        }
        postLogin(ACTION_QUICK_LOGIN, new HashMap<String, String>(), l);
    }

    public static void wechatLogin(final LoginResponseListener l) {
//        SocialLoginBiz.wechatLogin(new SocialLoginBiz.GetCodeListener() {
//            @Override
//            public void onGetCode(String code) {
//                if (l.mRegisterRunnable != null) l.mRegisterRunnable.code = code;
//                //socialLogin(KEY_SOCIAL_COED_WECHAT, code, l);
//                LogUtil.i("wxCode", code);
//            }
//        });
        socialLogin(KEY_SOCIAL_COED_WECHAT, "0211a93fbdccc024cce8897b8e19f65c", l);
    }

    /**
     * 用户名、密码登录
     *
     * @param phoneNum
     * @param psw
     * @param l
     */
    public static void login(String phoneNum, String psw, LoginResponseListener l) {
        Map<String, String> params = new HashMap<>();
        params.put(KEY_PHONE, phoneNum);
        params.put("psw", psw);
        postLogin(ACTION_LOGIN, params, l);
    }

    public static void socialLogin(String platformCodeKey, String code, LoginResponseListener l) {
        postLogin(ACTION_LOGIN, HttpReq.getInitParams(platformCodeKey, code), l);
    }

    private static void postLogin(String action, Map<String, String> params, ResponseListener<?> l) {
        params.put("configVersion", ConfigManager.getConfig(KEY_CONFIG_VERSION, "0"));
        params.put("deviceToken", UmengPushBiz.getDeviceToken());
        HttpReq.post(action, params, l);
    }

    /**
     * 绑定帐户信息
     *
     * @param type
     * @param name
     * @param psw
     * @param phoneNum
     * @param confirmCode
     * @param wxCode
     * @param l
     */
    public static void bindAccount(String type, String name, String psw, String phoneNum, String confirmCode, String wxCode, ResponseListener<?> l) {
        if (!ConfigManager.checkTokenWithTips()) return;
        Map<String, String> params = HttpReq.getInitParams("type", type);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "psw", psw);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, "confirmCode", confirmCode);
        HttpReq.putParams(params, "wxCode", wxCode);
        HttpReq.post(ACTION_BIND_ACCOUNT, params, l);
    }

    public static void detail(ResponseListener<?> l) {
        if (!ConfigManager.checkTokenWithTips()) return;
        HttpReq.post(ACTION_DETAIL, null, l);
    }

    public static void update(String avatar, String name, String gender, String province, String city, String birthday, String phoneNum, ResponseListener<?> l) {
        Map<String, String> params = HttpReq.getInitParams("avatar", avatar);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "gender", gender);
        HttpReq.putParams(params, "province", province);
        HttpReq.putParams(params, "city", city);
        HttpReq.putParams(params, "birthday", birthday);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.post(ACTION_UPDATE, params, l);
    }

    public static void logout(ResponseListener<?> l) {
        if (!ConfigManager.checkTokenWithTips()) return;
        HttpReq.post(ACTION_LOGOUT, null, l);
    }

    /**
     *
     */
    public static class LoginResponseListener extends ResponseListener<AppResponse<AppInit>> {
        private RegisterRunnable mRegisterRunnable;
        private BaseActivity mActivity;

        public LoginResponseListener(BaseActivity activity) {
            mActivity = activity;
            mActivity.showProgressDialog();
        }

        public void success() {
            mActivity.dismissProgressDialog();
            ActivityResult.onFinishResult(mActivity);
        }


        public LoginResponseListener setRegisterRunnable(RegisterRunnable r) {
            mRegisterRunnable = r;
            return this;
        }

        @Override
        public void onLoadResponse(AppResponse<AppInit> response) {
            AppInit login = response.data;
            if (login.projectCategory != null) {
                AppInitManger.save(login);
            }
        }

        @Override
        public void onResponse(AppResponse<AppInit> response) {
            AppInit appInit = response.data;
            AppInitManger.save(appInit);
            success();
            if (mRegisterRunnable != null) {
                mRegisterRunnable.run();
            }

//            AppInit appInit = response.data;
////            if (login.projectCategory != null) {
////                AppInitManger.save(login);
////                success();
////                if (mRegisterRunnable != null) {
////                    mRegisterRunnable.run();
////                }
////                return;
////            }
//            if (appInit.id != 0) {
//                AppInitManger.save(appInit);
//                success();
//                if (mRegisterRunnable != null) {
//                    mRegisterRunnable.run();
//                }
//                return;
//            }
//            ConfigManager.putToken(appInit.token);
//            quickLogin(this);
////            if (response.code == CODE_EXPIRE && mResponseListener != null && mConfigVersion != null) {
////                quickLogin(mConfigVersion, mResponseListener);
////            } else {
////                mResponseListener.onResponse(response);
////            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mActivity.dismissProgressDialog();
            if (!(error instanceof HttpReq.MsgError)) {
                if (AppInitManger.getProjectCategoryData() != null) {
                    success();
                }
            }
        }
    }

    public static abstract class RegisterRunnable implements Runnable {
        String code;

        @Override
        public final void run() {
            onRegister(code);
        }

        public abstract void onRegister(String code);
    }
}
