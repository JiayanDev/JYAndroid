package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;

/**
 * Created by liangzili on 15/6/24.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UserBiz.quickLogin(new UserBiz.LoginResponseListener().setRunnable(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                //startActivity(new Intent(SplashActivity.this, MyDiaryActivity.class));
                SplashActivity.this.finish();
            }
        }));
//        startActivity(new Intent(SplashActivity.this, MyDiaryActivity.class));
//        finish();
    }
}
