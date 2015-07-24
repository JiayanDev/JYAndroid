package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class OtherLoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_login;
    private ImageView img_wechat_login;
    private ImageView img_qq_login;
    private ImageView img_sina_login;

    private SocialLoginBiz mSocialLoginBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_login);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        btn_login = (Button) this.findViewById(R.id.btn_login);
        img_wechat_login = (ImageView) this.findViewById(R.id.img_wechat_login);
        img_qq_login = (ImageView) this.findViewById(R.id.img_qq_login);
        img_sina_login = (ImageView) this.findViewById(R.id.img_sina_login);
    }

    protected void setViewsContent() {
        setTitle(getString(R.string.login) + getString(R.string.app_name));
        mSocialLoginBiz = new SocialLoginBiz(this);
    }

    protected void setViewsListener() {
        btn_login.setOnClickListener(this);
        img_wechat_login.setOnClickListener(this);
        img_qq_login.setOnClickListener(this);
        img_sina_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_wechat_login:
                UserBiz.wechatLogin(new UserBiz.LoginResponseListener().setRunnable(new Runnable() {
                    @Override
                    public void run() {
                        finishToStartActivity(MainActivity.class);
                    }
                }));
                break;
            case R.id.img_qq_login:
                mSocialLoginBiz.login(SHARE_MEDIA.QQ, new SocialLoginBiz.GetUserInfoListener() {
                    @Override
                    public void onGetUserInfo(Map<String, Object> info) {
                    }
                });
                break;
            case R.id.img_sina_login:
                mSocialLoginBiz.login(SHARE_MEDIA.SINA, new SocialLoginBiz.GetUserInfoListener() {
                    @Override
                    public void onGetUserInfo(Map<String, Object> info) {
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSocialLoginBiz.onActivityResult(requestCode, resultCode, data);
    }
}

