package com.jiayantech.jyandroid.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jiayantech.jyandroid.commons.Constants;
import com.jiayantech.jyandroid.wxapi.WXEntryActivity;
import com.jiayantech.library.base.BaseApplication;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;

/**
 * Created by janseon on 2015/7/6.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SocialLoginBiz {
    //////////////////原生sdk 登录

    public static void wechatLogin(final GetCodeListener getCodeListener) {
        WXEntryActivity.setGetCodeListener(getCodeListener);
        IWXAPI api = WXAPIFactory.createWXAPI(BaseApplication.getContext(), Constants.WECHAT_appId);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "jiayantech";
        api.sendReq(req);
    }

    public static void qqLogin(final GetCodeListener getCodeListener) {
        WXEntryActivity.setGetCodeListener(getCodeListener);
        IWXAPI api = WXAPIFactory.createWXAPI(BaseApplication.getContext(), Constants.WECHAT_appId);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "jiayantech";
        api.sendReq(req);
    }

    public static void sinaLogin(final GetCodeListener getCodeListener) {
        WXEntryActivity.setGetCodeListener(getCodeListener);
        IWXAPI api = WXAPIFactory.createWXAPI(BaseApplication.getContext(), Constants.WECHAT_appId);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "jiayantech";
        api.sendReq(req);
    }

    public interface GetCodeListener {
        void onGetCode(String code);
    }

    //////////////////友盟

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     */

    public void login(final SHARE_MEDIA platform, final GetUserInfoListener getUserInfoListener) {
        mController.doOauthVerify(mActivity, platform,
                new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast.makeText(mActivity, "授权开始",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA platform) {
                        Toast.makeText(mActivity, "授权失败..." + e,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        String uid = value.getString("uid");
                        if (!TextUtils.isEmpty(uid)) {
                            getUserInfo(platform, getUserInfoListener);
                        } else {
                            Toast.makeText(mActivity, "授权失败..." + value,
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(mActivity, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 注销本次登陆
     *
     * @param platform
     */
    public void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(mActivity, platform, new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(mActivity, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(SHARE_MEDIA platform, final GetUserInfoListener getUserInfoListener) {
        mController.getPlatformInfo(mActivity, platform,
                new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (info != null) {
                            Toast.makeText(mActivity, info.toString(),
                                    Toast.LENGTH_SHORT).show();
                            if (getUserInfoListener != null) {
                                getUserInfoListener.onGetUserInfo(info);
                            }
                        }
                    }
                });
    }


    public interface GetUserInfoListener {
        void onGetUserInfo(Map<String, Object> info);
    }


    public SocialLoginBiz(Activity activity) {
        mActivity = activity;
        UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
        // 添加新浪sso授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }

    private final Activity mActivity;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constants.DESCRIPTOR);


    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform() {
        String appId = Constants.WECHAT_appId;
        String appSecret = Constants.WECHAT_appSecret;
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mActivity, appId, appSecret);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {
        String appId = Constants.QQ_appId;
        String appKey = Constants.QQ_appSecret;
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, appId, appKey);
        qqSsoHandler.setTargetUrl("http://www.umeng.com");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
                mActivity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    // 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                resultCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
