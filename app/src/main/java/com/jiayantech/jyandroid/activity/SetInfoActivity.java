package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class SetInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout input_nickname;
    private RadioGroup radio_group_gender;
    private TextInputLayout input_pass_0;
    private TextInputLayout input_pass_1;
    private Button btn_register;

    private final int[] ids = new int[]{R.id.radio_female, R.id.radio_male};
    private String social_code_type;
    private String social_code;
    private String phoneCodeConfirmResponse;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_info);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        input_nickname = (TextInputLayout) findViewById(R.id.input_nickname);
        radio_group_gender = (RadioGroup) findViewById(R.id.radio_group_gender);
        input_pass_0 = (TextInputLayout) findViewById(R.id.input_pass_0);
        input_pass_1 = (TextInputLayout) findViewById(R.id.input_pass_1);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_set_info);
        Intent intent = getIntent();
        social_code_type = intent.getStringExtra(UserBiz.SOCIAL_CODE_TYPE);
        social_code = intent.getStringExtra(UserBiz.SOCIAL_CODE);
        phoneCodeConfirmResponse = intent.getStringExtra(UserBiz.KEY_PHONE_CODE_CONFIRM_RESPONSE);
        phoneNum = intent.getStringExtra(UserBiz.KEY_PHONE);

        int visibility = TextUtils.isEmpty(social_code_type) ? View.VISIBLE : View.GONE;
        input_nickname.setVisibility(visibility);
        radio_group_gender.setVisibility(visibility);
    }

    protected void setViewsListener() {
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (TextUtils.isEmpty(social_code_type)) {
                    String nickname = input_nickname.getEditText().getText().toString();
                    if (TextUtils.isEmpty(nickname)) {
                        ToastUtil.showMessage(R.string.hint_input_nickname);
                        return;
                    }
                    String pass = checkPass();
                    if (TextUtils.isEmpty(pass)) {
                        return;
                    }
                    int gender = 0;
                    for (int index = 0; index < ids.length; index++) {
                        if (ids[index] == radio_group_gender.getCheckedRadioButtonId()) {
                            gender = index;
                            break;
                        }
                    }
                    UserBiz.register(phoneCodeConfirmResponse, phoneNum, nickname, gender, pass, new UserBiz.LoginResponseListener(this));
                } else {
                    String pass = checkPass();
                    if (TextUtils.isEmpty(pass)) {
                        return;
                    }
                    UserBiz.register(phoneCodeConfirmResponse, phoneNum, social_code_type, social_code, pass, new UserBiz.LoginResponseListener(this));
                }
                break;
        }
    }

    private String checkPass() {
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

