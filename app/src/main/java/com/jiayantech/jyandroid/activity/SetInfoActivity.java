package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class SetInfoActivity extends ResetPassActivity {

    private EditText input_nickname;
    private RadioGroup radio_group_gender;
    private Button btn_register;

    private final int[] ids = new int[]{R.id.radio_female, R.id.radio_male};
    private String social_type;
    //private String social_code;
    private String social_response;
    private String phoneNum;
    private String phoneCodeConfirmResponse;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_set_info0;
    }

    protected void findViews() {
        input_nickname = (EditText) findViewById(R.id.input_nickname);
        radio_group_gender = (RadioGroup) findViewById(R.id.radio_group_gender);
        input_pass_0 = (EditText) findViewById(R.id.input_pass_0);
        input_pass_1 = (EditText) findViewById(R.id.input_pass_1);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_set_info);
        Intent intent = getIntent();
        social_type = intent.getStringExtra(UserBiz.SOCIAL_TYPE);
        //social_code = intent.getStringExtra(UserBiz.SOCIAL_CODE);
        social_response = intent.getStringExtra(UserBiz.KEY_SOCIAL_RESPONSE);
        phoneNum = intent.getStringExtra(UserBiz.KEY_PHONE);
        phoneCodeConfirmResponse = intent.getStringExtra(UserBiz.KEY_RESPONSE);

        int visibility = TextUtils.isEmpty(social_type) ? View.VISIBLE : View.GONE;
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
                if (TextUtils.isEmpty(social_type)) {
                    String nickname = input_nickname.getText().toString();
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
                    UserBiz.register(phoneCodeConfirmResponse, phoneNum, nickname, gender, pass, new UserBiz.RegisterResponseListener(this));
                } else {
                    String pass = checkPass();
                    if (TextUtils.isEmpty(pass)) {
                        return;
                    }
                   // UserBiz.register(phoneCodeConfirmResponse, social_type, social_response, phoneNum, pass, new UserBiz.RegisterResponseListener(this));
                }
                break;
        }
    }
}

