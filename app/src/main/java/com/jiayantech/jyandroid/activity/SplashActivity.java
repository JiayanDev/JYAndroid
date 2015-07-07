package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import org.json.JSONObject;

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
                //startActivity(new Intent(SplashActivity.this, SearchActivity.class));
                SplashActivity.this.finish();
            }
        }));
//        HttpReq.uploadImage("/storage/emulated/0/Pictures/Screenshots/Screenshot_2015-03-02-10-43-55.png",
//                new ResponseListener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        ToastUtil.showMessage("Upload Complete");
//                    }
//
//                });
//        startActivity(new Intent(SplashActivity.this, MyDiaryActivity.class));
//        finish();
    }
}
