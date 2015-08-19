package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class ResetPassActivity extends BaseActivity implements View.OnClickListener {

    protected EditText input_pass_0;
    protected EditText input_pass_1;
    private EditText input_phone;
    private EditText input_code;
    private Button btn_send_verify_code;
    private Button btn_reset;


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        findViews();
        setViewsContent();
        setViewsListener();
    }

    protected int getLayoutResId() {
        return R.layout.activity_reset_pass;
    }

    protected void findViews() {
        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        stub.inflate();

        input_pass_0 = (EditText) findViewById(R.id.input_pass_0);
        input_pass_1 = (EditText) findViewById(R.id.input_pass_1);
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_code = (EditText) findViewById(R.id.input_code);
        btn_reset = (Button) findViewById(R.id.btn_submit);
        btn_send_verify_code = (Button) findViewById(R.id.btn_send_verify_code);
        btn_send_verify_code.setOnClickListener(this);

        btn_reset.setText(R.string.confirm_reset);

    }

    protected void setViewsContent() {
        setTitle(R.string.title_reset_pass);
        Intent intent = getIntent();
        //phoneNum = intent.getStringExtra(UserBiz.KEY_PHONE);
        //phoneCodeConfirmResponse = intent.getStringExtra(UserBiz.KEY_RESPONSE);
    }

    protected void setViewsListener() {
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                String pass = checkPass();
                String phoneCodeConfirmResponse = input_code.getText().toString();
                String phoneNum = input_phone.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    return;
                }

                UserBiz.update(phoneCodeConfirmResponse, phoneNum, pass,
                        new ResponseListener<BaseAppResponse>() {
                    @Override
                    public void onResponse(BaseAppResponse baseAppResponse) {
                        ActivityResult.onFinishResult(ResetPassActivity.this);
                    }
                });
                break;
            case R.id.btn_send_verify_code:
                phoneNum = input_phone.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showMessage(R.string.hint_input_phone);
                    return;
                }
                UserBiz.sendPhoneCode(phoneNum,
                        new SimpleResponseListener<AppResponse<HashMap<String, String>>>(_this) {
                    @Override
                    public void onResponse(AppResponse<HashMap<String, String>> response) {
                        super.onResponse(response);
                        ToastUtil.showMessage(R.string.msg_sent_phone_code);
                        //phoneCodeResponse = response.data.get(UserBiz.KEY_PHONE_CODE_RESPONSE);
                    }
                });
                break;

        }
    }

    protected String checkPass() {
        String pass_0 = input_pass_0.getText().toString();
        if (TextUtils.isEmpty(pass_0)) {
            ToastUtil.showMessage(R.string.hint_input_set_pass);
            return null;
        }
        String pass_1 = input_pass_1.getText().toString();
        if (TextUtils.isEmpty(pass_1)) {
            ToastUtil.showMessage(R.string.hint_input_set_pass_again);
            return null;
        }
        if (!pass_0.equals(pass_1)) {
            ToastUtil.showMessage(R.string.waring_pass_is_not_same);
            return null;
        }
        return pass_0;
    }
}

