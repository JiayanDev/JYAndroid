package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class ResetPassActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout input_pass_0;
    private TextInputLayout input_pass_1;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        input_pass_0 = (TextInputLayout) findViewById(R.id.input_pass_0);
        input_pass_1 = (TextInputLayout) findViewById(R.id.input_pass_1);
        btn_reset = (Button) findViewById(R.id.btn_reset);
    }

    protected void setViewsContent() {
        setTitle(getString(R.string.login) + getString(R.string.app_name));
    }

    protected void setViewsListener() {
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                UserBiz.wechatLogin(new UserBiz.LoginResponseListener(this));
                break;
        }
    }
}

