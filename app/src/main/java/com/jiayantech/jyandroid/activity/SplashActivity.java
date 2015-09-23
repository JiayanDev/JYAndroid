package com.jiayantech.jyandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.Data;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by liangzili on 15/6/24.
 */
public class SplashActivity extends BaseActivity {
    public static final String EXTRA_BUNDLE = "launchParams";

    private final long delayMillis = 1000;
    final long currentTimeMillis = System.currentTimeMillis();

    private final String DATA_URL = "http://admintest.jiayantech.com/data.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSwipeBackEnable(false);
        hideActionBar();


        //Debug.waitForDebugger();
        //Intent intent = getIntent();

        if (AppInitManger.isRegister()) {
            quickLogin();
        } else {
            CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
                @Override
                public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                    AppInit appInit = appInitAppResponse.data;
                    AppInitManger.save(appInit);
                    if (appInit.register) {
                        quickLogin();
                    } else {
                        gotoMainActivity();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (!(error instanceof HttpReq.MsgError)) {
                        if (AppInitManger.getProjectCategoryData() != null) {
                            gotoMainActivity();
                        }
                    }
                }
            });
        }

        HttpReq._request(Request.Method.GET, DATA_URL, null, null, false, false, null, new ResponseListener<AppResponse<Data>>() {
            @Override
            public void onResponse(AppResponse<Data> dataAppResponse) {
                final Data data = dataAppResponse.data;
                if (getVersionValue(data.version) > getVersionValue(BaseApplication.getContext().getVersionName())) {
                    AlertDialog dialog = new AlertDialog.Builder(BaseApplication.getContext(), AlertDialog.THEME_HOLO_LIGHT).
                            setTitle("版本更新: v" + dataAppResponse.data.version).
                            setNegativeButton("取消", null).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.url));
                                    startActivity(intent);
                                }
                            }).
                            create();
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//指定会全局,可以在后台弹出
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            }
        });
    }

    public long getVersionValue(String version) {
        String[] splits = version.split("\\.");
        long value = 0;
        for (int i = 0; i < splits.length; i++) {
            value = value * 1024 + Integer.parseInt(splits[i]);
        }
        return value;
    }

    private void quickLogin() {
        UserBiz.quickLogin(new ResponseListener<AppResponse<AppInit>>() {
            @Override
            public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                AppInitManger.save(appInitAppResponse.data);
                gotoMainActivity();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (!(error instanceof HttpReq.MsgError)) {
                    if (AppInitManger.getProjectCategoryData() != null) {
                        gotoMainActivity();
                    }
                }
            }
        });
    }

    private void gotoMainActivity() {
        long dTimeMillis = System.currentTimeMillis() - currentTimeMillis;
        final Intent intent = new Intent(this, MainActivity.class);
        if (getIntent().getBundleExtra(EXTRA_BUNDLE) != null) {
            intent.putExtras(getIntent().getBundleExtra(EXTRA_BUNDLE));
        }

        if (dTimeMillis < delayMillis) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishToStartActivity(intent);
                }
            }, delayMillis - dTimeMillis);
            return;
        }

        finishToStartActivity(intent);
    }
}
