package com.jiayantech.jyandroid.app;

import android.content.Context;
import android.content.Intent;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.LoginActivity;
import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.ShareBiz;
import com.jiayantech.jyandroid.clickaction.ClickToActivityAction;
import com.jiayantech.jyandroid.clickaction.ClickToMyAngelAction;
import com.jiayantech.jyandroid.clickaction.ClickToMyCompanyAction;
import com.jiayantech.jyandroid.clickaction.ClickToWebActivityAction;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.umeng_push.UmengPushConfig;
import com.jiayantech.umeng_push.UmengPushManager;

/**
 * Created by liangzili on 15/6/24.
 */
public class JYApplication extends BaseApplication {
    @Override
    public void onCreate() {

        super.onCreate();

        ShareBiz.registerToWx(getApplicationContext());
        //UmengPushBiz.init(getApplicationContext());

        UmengPushConfig config = new UmengPushConfig();
        config.setMainActivityClass(MainActivity.class);
        UmengPushManager.getInstance().init(this, config);

        UmengPushManager.getInstance().addClickAction(
                new ClickToWebActivityAction(ClickToActivityAction.TYPE_DIARY));
        UmengPushManager.getInstance().addClickAction(
                new ClickToWebActivityAction(ClickToActivityAction.TYPE_TOPIC));
        UmengPushManager.getInstance().addClickAction(
                new ClickToMyAngelAction());
        UmengPushManager.getInstance().addClickAction(new ClickToMyCompanyAction());

        LogUtil.i("LifeCycle", String.format("%s is onCreate()", this.getClass().getSimpleName()));
    }


    @Override
    public void onOverdue(final HttpReq httpReq) {
        if (AppInitManger.isRegister()) {
            ToastUtil.showMessage(R.string.msg_overdue_to_login);
            LoginActivity.start(this);
            mBroadcastHelper.registerReceiver(new BroadcastHelper.OnceBroadcastReceiver() {
                @Override
                public void onOnceReceive(Context context, Intent intent) {
                    HttpReq.request(httpReq);
                }
            }, Broadcasts.ACTION_LOGINED);
        } else {
            CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
                @Override
                public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                    AppInit appInit = appInitAppResponse.data;
                    AppInitManger.save(appInit);
                    HttpReq.request(httpReq);
                }
            });
        }
    }


}
