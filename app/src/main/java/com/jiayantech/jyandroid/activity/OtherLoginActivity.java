package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.comm.MD5;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.utils.ToastUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class OtherLoginActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout input_phone;
    private TextInputLayout input_pass;
    private TextView txt_forget_pass;
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
        input_phone = (TextInputLayout) findViewById(R.id.input_phone);
        input_pass = (TextInputLayout) findViewById(R.id.input_pass);
        txt_forget_pass = (TextView) findViewById(R.id.txt_forget_pass);
        btn_login = (Button) findViewById(R.id.btn_login);
        img_wechat_login = (ImageView) findViewById(R.id.img_wechat_login);
        img_qq_login = (ImageView) findViewById(R.id.img_qq_login);
        img_sina_login = (ImageView) findViewById(R.id.img_sina_login);
    }

    protected void setViewsContent() {
        setTitle(getString(R.string.login) + getString(R.string.app_name));
        mSocialLoginBiz = new SocialLoginBiz(this);
        input_phone.getEditText().setText(ConfigManager.getConfig(UserBiz.KEY_PHONE));
    }

    protected void setViewsListener() {
        txt_forget_pass.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        img_wechat_login.setOnClickListener(this);
        img_qq_login.setOnClickListener(this);
        img_sina_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_forget_pass:
                Intent intent = new Intent(this, VerifyPhoneActivity.class);
                intent.putExtra(VerifyPhoneActivity.KEY_TYPE, VerifyPhoneActivity.TYPE_FORGET_PASS);
                startActivity(intent);
                break;
            case R.id.btn_login:
                String phone = input_phone.getEditText().getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showMessage(R.string.hint_input_phone);
                    return;
                }
                String pass = input_pass.getEditText().getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    ToastUtil.showMessage(R.string.hint_input_pass);
                    return;
                }
                UserBiz.login(phone, pass, new UserBiz.LoginResponseListener(this));
                break;
            case R.id.img_wechat_login:
                UserBiz.wechatLogin(new UserBiz.LoginResponseListener(this));
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSocialLoginBiz.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}

