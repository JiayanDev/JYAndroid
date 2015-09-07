package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by 健兴 on 2015/8/3.
 */
public class SettingActivity extends BaseActivity {
    private TextView txt_service_phone;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
        setViewsContent();
    }

    protected void findViews() {
        txt_service_phone = (TextView) findViewById(R.id.txt_service_phone);
    }

    protected void setViewsContent() {
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_password:
                startActivity(UpdatePassActivity.class);
                break;
            case R.id.txt_help:

                break;
            case R.id.txt_about:

                break;
            case R.id.txt_feedback:
                break;
            case R.id.layout_service:

                break;
            case R.id.btn_exit:
                showProgressDialog();
//                UserBiz.logout(new ResponseListener<BaseAppResponse>() {
//                    @Override
//                    public void onResponse(BaseAppResponse baseAppResponse) {
//                        ((BaseActivity) getActivity()).dismissProgressDialog();
//                        ToastUtil.showMessage("logout");
//                    }
//                });
                ConfigManager.putToken("");
                CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
                    @Override
                    public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                        AppInit appInit = appInitAppResponse.data;
                        AppInitManger.save(appInit);
                        dismissProgressDialog();
                        //((MainActivity) getActivity()).onCheckedChanged(null, R.id.radio_activity);
                    }
                });
                break;
        }
    }
}
