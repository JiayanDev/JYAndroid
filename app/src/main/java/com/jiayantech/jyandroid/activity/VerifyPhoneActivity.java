package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class VerifyPhoneActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_send_verify_code;
    private Button btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        btn_send_verify_code = (Button) findViewById(R.id.btn_send_verify_code);
        btn_verify = (Button) findViewById(R.id.btn_verify);
    }

    protected void setViewsContent() {
        setTitle(getString(R.string.login) + getString(R.string.app_name));
    }

    protected void setViewsListener() {
        btn_send_verify_code.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verify_code:

                break;
            case R.id.btn_verify:

                break;
        }
    }
}


