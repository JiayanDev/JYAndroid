package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by liangzili on 15/6/24.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        UserBiz.quickLogin("0", new UserBiz.LoginResponseListener() {
//            @Override
//            public void onResponse(AppResponse<Login> response) {
//                super.onResponse(response);
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            }
//        });
        startActivity(new Intent(SplashActivity.this, PublishPostActivity.class));
    }
}
