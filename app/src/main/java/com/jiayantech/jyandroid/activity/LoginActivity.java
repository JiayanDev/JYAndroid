package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.commons.Constants;
import com.jiayantech.library.base.BaseActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * Created by janseon on 2015/7/6.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_direct_login;
    private Button btn_sina_login;
    private Button btn_qq_login;
    private Button btn_wechat_login;
    private Button btn_sina_logout;
    private Button btn_qq_logout;
    private Button btn_wechat_logout;

    private SocialLoginBiz mSocialLoginBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setViewsContent();
        setViewsListener();

        //finishToStartActivity(MainActivity.class);
    }


    protected void findViews() {
        btn_direct_login = (Button) this.findViewById(R.id.btn_direct_login);
        btn_wechat_login = (Button) this.findViewById(R.id.btn_wechat_login);
        btn_qq_login = (Button) this.findViewById(R.id.btn_qq_login);
        btn_sina_login = (Button) this.findViewById(R.id.btn_sina_login);

        btn_wechat_logout = (Button) this.findViewById(R.id.btn_wechat_logout);
        btn_qq_logout = (Button) this.findViewById(R.id.btn_qq_logout);
        btn_sina_logout = (Button) this.findViewById(R.id.btn_sina_logout);
    }

    protected void setViewsContent() {
        setTitle("登录");
        setSwipeBackEnable(false);
        mSocialLoginBiz = new SocialLoginBiz(this);
    }

    protected void setViewsListener() {
        btn_direct_login.setOnClickListener(this);
        btn_wechat_login.setOnClickListener(this);
        btn_qq_login.setOnClickListener(this);
        btn_sina_login.setOnClickListener(this);

        btn_wechat_logout.setOnClickListener(this);
        btn_qq_logout.setOnClickListener(this);
        btn_sina_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_direct_login:
                UserBiz.quickLogin(new UserBiz.LoginResponseListener().setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        finishToStartActivity(MainActivity.class);
                    }
                }));
                //startActivity(PhotosActivity.class);
                break;
            case R.id.btn_wechat_login:
//                mSocialLoginBiz.login(SHARE_MEDIA.WEIXIN, new SocialLoginBiz.GetUserInfoListener() {
//                    @Override
//                    public void onGetUserInfo(Map<String, Object> info) {
//                    }
//                });

                break;
            case R.id.btn_qq_login:
                mSocialLoginBiz.login(SHARE_MEDIA.QQ, new SocialLoginBiz.GetUserInfoListener() {
                    @Override
                    public void onGetUserInfo(Map<String, Object> info) {
                    }
                });
                break;
            case R.id.btn_sina_login:
                mSocialLoginBiz.login(SHARE_MEDIA.SINA, new SocialLoginBiz.GetUserInfoListener() {
                    @Override
                    public void onGetUserInfo(Map<String, Object> info) {
                    }
                });
                break;
            case R.id.btn_wechat_logout:
                mSocialLoginBiz.logout(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_qq_logout:
                mSocialLoginBiz.logout(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_sina_logout:
                mSocialLoginBiz.logout(SHARE_MEDIA.SINA);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSocialLoginBiz.onActivityResult(requestCode, resultCode, data);
    }
}

