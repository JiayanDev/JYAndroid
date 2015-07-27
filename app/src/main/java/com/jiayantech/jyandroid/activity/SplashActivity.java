package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by liangzili on 15/6/24.
 */
public class SplashActivity extends BaseActivity {
    private final long delayMillis = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSwipeBackEnable(false);
        hideActionBar();
        final long currentTimeMillis = System.currentTimeMillis();
        CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
            @Override
            public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                AppInit appInit = appInitAppResponse.data;
                AppInitManger.save(appInit);
                if (appInit.register) {
                    UserBiz.quickLogin(new ResponseListener<AppResponse<AppInit>>() {
                        @Override
                        public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                            AppInitManger.save(appInitAppResponse.data);
                            gotoMainActivity(currentTimeMillis);
                        }
                    });
                } else {
                    gotoMainActivity(currentTimeMillis);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (!(error instanceof HttpReq.MsgError)) {
                    if (AppInitManger.getProjectCategoryData() != null) {
                        gotoMainActivity(currentTimeMillis);
                    }
                }
            }
        });
    }

    private void gotoMainActivity(long currentTimeMillis) {
        long dTimeMillis = System.currentTimeMillis() - currentTimeMillis;
        if (dTimeMillis < delayMillis) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishToStartActivity(MainActivity.class);
                }
            }, delayMillis - dTimeMillis);
            return;
        }
        finishToStartActivity(MainActivity.class);
    }
}
