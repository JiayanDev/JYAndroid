package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class ResetPassActivity extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout input_pass_0;
    protected TextInputLayout input_pass_1;
    private Button btn_reset;

    private String phoneNum;
    private String phoneCodeConfirmResponse;

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
        input_pass_0 = (TextInputLayout) findViewById(R.id.input_pass_0);
        input_pass_1 = (TextInputLayout) findViewById(R.id.input_pass_1);
        btn_reset = (Button) findViewById(R.id.btn_reset);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_reset_pass);
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra(UserBiz.KEY_PHONE);
        phoneCodeConfirmResponse = intent.getStringExtra(UserBiz.KEY_RESPONSE);
    }

    protected void setViewsListener() {
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                String pass = checkPass();
                if (TextUtils.isEmpty(pass)) {
                    return;
                }
                UserBiz.update(phoneCodeConfirmResponse, phoneNum, pass, new ResponseListener<BaseAppResponse>() {
                    @Override
                    public void onResponse(BaseAppResponse baseAppResponse) {
                        ActivityResult.onFinishResult(ResetPassActivity.this);
                    }
                });
                break;
        }
    }

    protected String checkPass() {
        String pass_0 = input_pass_0.getEditText().getText().toString();
        if (TextUtils.isEmpty(pass_0)) {
            ToastUtil.showMessage(R.string.hint_input_set_pass);
            return null;
        }
        String pass_1 = input_pass_1.getEditText().getText().toString();
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

