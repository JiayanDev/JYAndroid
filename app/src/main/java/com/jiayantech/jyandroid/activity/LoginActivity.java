package com.jiayantech.jyandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;


/**
 * Created by janseon on 2015/7/6.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_close;
    private LinearLayout btn_wechat_login;
    private TextView txt_other_login;
    private TextView txt_register;

    private SocialLoginBiz mSocialLoginBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        popBottomIn();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSwipeBackEnable(false);
        findViews();
        setViewsContent();
        setViewsListener();
        hideActionBar();
    }

    protected void findViews() {
        img_close = (ImageView) findViewById(R.id.img_close);
        btn_wechat_login = (LinearLayout) findViewById(R.id.btn_wechat_login);
        txt_other_login = (TextView) findViewById(R.id.txt_other_login);
        txt_register = (TextView) findViewById(R.id.txt_register);
    }

    protected void setViewsContent() {
        setTitle(R.string.login);
        setBackgroundResource(android.R.color.white);
        mSocialLoginBiz = new SocialLoginBiz(this);

//        UIUtil.setPaddingLeft(getResources().getDrawable(R.mipmap.icon_wechat_white),
//                btn_wechat_login);
    }

    protected void setViewsListener() {
        img_close.setOnClickListener(this);
        btn_wechat_login.setOnClickListener(this);

        txt_other_login.setOnClickListener(this);
        txt_register.setOnClickListener(this);
    }


    @Override
    public void finish() {
        super.finish();
        popBottomOut();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.btn_wechat_login:
//                UserBiz.wechatLogin(new UserBiz.LoginResponseListener(this).setRegisterRunnable(new UserBiz.RegisterRunnable() {
//                    @Override
//                    public void onRegister(String code) {
//                        Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
//                        intent.putExtra(UserBiz.SOCIAL_CODE_TYPE, UserBiz.KEY_SOCIAL_COED_WECHAT);
//                        intent.putExtra(UserBiz.SOCIAL_CODE, code);
//                        startActivityForResult(intent, ActivityResult.REQUEST_CODE_DEFAUTE);
//                        mActivityResultHelper.addActivityResult(new ActivityResult(ActivityResult.REQUEST_CODE_DEFAUTE) {
//                            @Override
//                            public void onActivityResult(Intent data) {
//                                ActivityResult.onFinishResult(LoginActivity.this);
//                            }
//                        });
//                    }
//                }));
                //startActivity(MessagesActivity.class);
                //finishToStartActivity(MainActivity.class);

//                UploadImageBiz.uploadImage("topic", "/storage/emulated/0/DCIM/Camera/IMG_20150706_164704.jpg",
//                        new ResponseListener<ImageUploadResp>() {
//                UserBiz.wechatLogin(new UserBiz.LoginResponseListener(this).setRegisterRunnable(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
//                        intent.putExtra(UserBiz.SOCIAL_CODE_TYPE, UserBiz.KEY_SOCIAL_COED_WECHAT);
//                        startActivityForResult(intent, ActivityResult.REQUEST_CODE_DEFAUTE);
//                        mActivityResultHelper.addActivityResult(new ActivityResult(ActivityResult.REQUEST_CODE_DEFAUTE) {
//                            @Override
//                            public void onActivityResult(Intent data) {
//                                ActivityResult.onFinishResult(LoginActivity.this);
//                            }
//                        });
//                    }
//                }));
//                SocialLoginBiz.wechatLogin(new SocialLoginBiz.GetCodeListener() {
//                    @Override
//                    public void onGetCode(final String code) {
//                        UserBiz.socialLogin(UserBiz.KEY_SOCIAL_COED_WECHAT, code, new UserBiz.LoginResponseListener(LoginActivity.this).setRegisterRunnable(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
//                                intent.putExtra(UserBiz.SOCIAL_CODE_TYPE, UserBiz.KEY_SOCIAL_COED_WECHAT);
//                                intent.putExtra(UserBiz.SOCIAL_CODE, code);
//                                startActivityForResult(intent, ActivityResult.REQUEST_CODE_DEFAUTE);
//                                mActivityResultHelper.addActivityResult(new ActivityResult(ActivityResult.REQUEST_CODE_DEFAUTE) {
//                                    @Override
//                                    public void onActivityResult(Intent data) {
//                                        ActivityResult.onFinishResult(LoginActivity.this);
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
                UserBiz.wechatLogin(new UserBiz.LoginResponseListener(this));
                break;
            case R.id.txt_other_login:
                startActivityForResult(new Intent(this, OtherLoginActivity.class), new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        ActivityResult.onFinishResult(LoginActivity.this);
                    }
                });
                break;
            case R.id.txt_register:
                startActivityForResult(new Intent(this, VerifyPhoneActivity.class), new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        ActivityResult.onFinishResult(LoginActivity.this);
                    }
                });
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSocialLoginBiz.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void checkLoginToRunnable(BaseActivity activity, final Runnable runnable) {
        if (AppInitManger.isRegister()) {
            runnable.run();
        } else {
            activity.startActivityForResult(new Intent(activity, LoginActivity.class), new ActivityResult() {
                @Override
                public void onActivityResult(Intent data) {
                    runnable.run();
                }
            });
        }
    }
}

