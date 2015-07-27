package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class VerifyPhoneActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout input_phone;
    private TextInputLayout input_code;
    private TextView txt_send_verify_code;
    private Button btn_verify;

    private String social_code_type;
    private String social_code;
    private String phoneCodeResponse;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    protected void findViews() {
        input_phone = (TextInputLayout) findViewById(R.id.input_phone);
        input_code = (TextInputLayout) findViewById(R.id.input_code);
        txt_send_verify_code = (TextView) findViewById(R.id.txt_send_verify_code);
        btn_verify = (Button) findViewById(R.id.btn_verify);
    }

    protected void setViewsContent() {
        setTitle(R.string.verify_phone);
        Intent intent = getIntent();
        social_code_type = intent.getStringExtra(UserBiz.SOCIAL_CODE_TYPE);
        social_code = intent.getStringExtra(UserBiz.SOCIAL_CODE);
    }

    protected void setViewsListener() {
        txt_send_verify_code.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_send_verify_code:
                phoneNum = input_phone.getEditText().getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showMessage(R.string.hint_input_phone);
                    return;
                }
                UserBiz.sendPhoneCode(phoneNum, new SimpleResponseListener<AppResponse<HashMap<String, String>>>() {
                    @Override
                    public void onResponse(AppResponse<HashMap<String, String>> response) {
                        super.onResponse(response);
                        phoneCodeResponse = response.data.get(UserBiz.KEY_PHONE_CODE_RESPONSE);
                    }
                });
                break;
            case R.id.btn_verify:
                if (TextUtils.isEmpty(phoneCodeResponse)) {
                    ToastUtil.showMessage(R.string.hint_waring_send_phone_code);
                    return;
                }
                String code = input_code.getEditText().getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showMessage(R.string.hint_input_phone_code);
                    return;
                }
                UserBiz.confirmPhoneCode(social_code_type, social_code, phoneCodeResponse, code, new SimpleResponseListener<AppResponse<HashMap<String, String>>>() {
                    @Override
                    public void onResponse(AppResponse<HashMap<String, String>> response) {
                        super.onResponse(response);
                        String phoneCodeConfirmResponse = response.data.get(UserBiz.KEY_PHONE_CODE_CONFIRM_RESPONSE);
                        Intent intent = new Intent(VerifyPhoneActivity.this, SetInfoActivity.class);
                        intent.putExtra(UserBiz.SOCIAL_CODE_TYPE, social_code_type);
                        intent.putExtra(UserBiz.SOCIAL_CODE, social_code);
                        intent.putExtra(UserBiz.KEY_PHONE_CODE_CONFIRM_RESPONSE, phoneCodeConfirmResponse);
                        intent.putExtra(UserBiz.KEY_PHONE, phoneNum);
                        finishToStartActivity(intent);
                    }
                });
                break;
        }
    }
}