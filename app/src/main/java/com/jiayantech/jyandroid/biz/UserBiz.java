package com.jiayantech.jyandroid.biz;

import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.VerifyPhoneActivity;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.comm.MD5;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;

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
    private static final String KEY_CONFIG_VERSION = "configVersion";
    public static final String KEY_PHONE = "phoneNum";
    public static final String KEY_PHONE_CODE_RESPONSE = "confirmId";
    public static final String KEY_RESPONSE = "receipt";
    public static final String KEY_SOCIAL_RESPONSE = "social_receipt";

    public static final String SOCIAL_TYPE = "social_type";
    public static final String SOCIAL_TYPE_WECHAT = "wx";

    public static final String SOCIAL_CODE = "code";


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
    private static final String ACTION_UPDATE_PASS = ACTION_UPDATE + "/psw";

    private static final String ACTION_DELETE = MODEL + "/delete";

    /**
     * 注册
     *
     * @param l
     */
    private static void register(RegisterResponseListener l) {
        HttpReq.post(ACTION_REGISTER, null, l);
    }

    /**
     * 第三方注册
     *
     * @param receipt
     * @param phoneNum
     * @param l
     */
    public static void register(String receipt, String social_type, String social_response, String phoneNum, RegisterResponseListener l) {
        Map<String, String> params = HttpReq.getInitParams(KEY_RESPONSE, receipt);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        //HttpReq.putParams(params, social_type, social_code);
        //HttpReq.putParams(params, "psw", MD5.encode(psw));
        HttpReq.putParams(params, social_type + "Receipt", social_response);
        l.phoneNum = phoneNum;
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
    public static void register(String receipt, String phoneNum, String name, int gender, String psw, RegisterResponseListener l) {
        Map<String, String> params = HttpReq.getInitParams(KEY_RESPONSE, receipt);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "gender", gender);
        HttpReq.putParams(params, "psw", MD5.encode(psw));
        l.phoneNum = phoneNum;
        HttpReq.post(ACTION_REGISTER, params, l);
    }

    public static void sendPhoneCode(String phoneNum, ResponseListener<?> l) {
        HttpReq.get(ACTION_PHONE_SEND_CODE, HttpReq.getInitParams(KEY_PHONE, phoneNum), l);
    }

    public static void confirmPhoneCode(String phoneCodeResponse, String code, ResponseListener<?> l) {
        Map<String, String> params = HttpReq.getInitParams("code", code);
        //HttpReq.putParams(params, social_type, social_code);
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
        Map<String, String> params = HttpReq.getInitParams("configVersion", ConfigManager.getConfig(KEY_CONFIG_VERSION, "0"));
        postLogin(ACTION_QUICK_LOGIN, params, l);
    }

    public static void wechatLogin(final LoginResponseListener l) {
        SocialLoginBiz.wechatLogin(new SocialLoginBiz.GetCodeListener() {
            @Override
            public void onGetCode(String code) {
                l.social_type = SOCIAL_TYPE_WECHAT;
                l.social_code = code;
                LogUtil.i(SOCIAL_TYPE_WECHAT, code);
                socialLogin(SOCIAL_TYPE_WECHAT, code, l);
            }
        });
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
        params.put("psw", MD5.encode(psw));
        l.phoneNum = phoneNum;
        postLogin(ACTION_LOGIN, params, l);
    }

    public static void socialLogin(String social_type, String code, LoginResponseListener l) {
        postLogin(ACTION_LOGIN, HttpReq.getInitParams(social_type + "Code", code), l);
    }

    private static void postLogin(String action, Map<String, String> params, ResponseListener<?> l) {
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
        Map<String, String> params = HttpReq.getInitParams("type", type);
        HttpReq.putParams(params, "name", name);
        HttpReq.putParams(params, "psw", MD5.encode(psw));
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, "confirmCode", confirmCode);
        HttpReq.putParams(params, "wxCode", wxCode);
        HttpReq.post(ACTION_BIND_ACCOUNT, params, l);
    }

    public static void detail(ResponseListener<?> l) {
        HttpReq.get(ACTION_DETAIL, null, l);
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

    public static void update(String receipt, String phoneNum, String psw, ResponseListener<?> l) {
        Map<String, String> params = HttpReq.getInitParams(KEY_RESPONSE, receipt);
        HttpReq.putParams(params, KEY_PHONE, phoneNum);
        HttpReq.putParams(params, "psw", MD5.encode(psw));
        HttpReq.post(ACTION_UPDATE_PASS, params, l);
    }

    public static void logout(ResponseListener<?> l) {
        HttpReq.post(ACTION_LOGOUT, null, l);
    }

    public static void delete(ResponseListener<?> l) {
        HttpReq.post(ACTION_DELETE, null, l);
    }

    /**
     *
     */
    public static class LoginResponseListener extends ResponseListener<AppResponse<AppInit>> {
        private String phoneNum;
        private String social_type;
        private String social_code;
        private BaseActivity mActivity;

        public LoginResponseListener(BaseActivity activity) {
            mActivity = activity;
            if (mActivity != null) mActivity.showProgressDialog();
        }

        public LoginResponseListener(BaseActivity activity, String phoneNum) {
            this(activity);
            this.phoneNum = phoneNum;
        }

        @Override
        public void onResponse(AppResponse<AppInit> response) {
            if (mActivity != null) mActivity.dismissProgressDialog();
            AppInit appInit = response.data;
            AppInitManger.save(appInit);
            String social_response = getSocialReceipt(appInit, social_type);
            if (appInit.register && TextUtils.isEmpty(social_response) && !TextUtils.isEmpty(AppInitManger.getPhoneNum())) {
                BroadcastHelper.send(Broadcasts.ACTION_LOGINED);
                if (!TextUtils.isEmpty(phoneNum)) ConfigManager.putConfig(KEY_PHONE, phoneNum);
                onFinishResult();
            } else {
                if (mActivity != null) {
                    ToastUtil.showMessage(R.string.msg_account_not_register);
                    Intent intent = new Intent(mActivity, VerifyPhoneActivity.class);
                    if (!TextUtils.isEmpty(social_type)) {
                        intent.putExtra(UserBiz.KEY_SOCIAL_RESPONSE, social_response);
                        intent.putExtra(UserBiz.SOCIAL_TYPE, social_type);
                        intent.putExtra(UserBiz.SOCIAL_CODE, social_code);
                    }
                    mActivity.startActivityForResult(intent, new ActivityResult() {
                        @Override
                        public void onActivityResult(Intent data) {
                            UserBiz.quickLogin(new UserBiz.LoginResponseListener(mActivity));
                        }
                    });
                }
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            if (mActivity != null) mActivity.dismissProgressDialog();
            if (!(error instanceof HttpReq.MsgError)) {
                if (AppInitManger.getProjectCategoryData() != null) {
                    onFinishResult();
                }
            }
        }

        private void onFinishResult() {
            if (mActivity != null) ActivityResult.onFinishResult(mActivity);
        }
    }

    private static String getSocialReceipt(AppInit appInit, String social_type) {
        if (SOCIAL_TYPE_WECHAT.equals(social_type)) {
            return appInit.wxReceipt;
        }
        return null;
    }

    public static class RegisterResponseListener extends ResponseListener<AppResponse<AppInit>> {
        private String phoneNum;
        private BaseActivity mActivity;

        public RegisterResponseListener(BaseActivity activity) {
            mActivity = activity;
            mActivity.showProgressDialog();
        }

        @Override
        public void onResponse(AppResponse<AppInit> response) {
            AppInitManger.saveToken(response.data);
            quickLogin(new LoginResponseListener(mActivity, phoneNum));
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mActivity.dismissProgressDialog();
        }
    }
}
