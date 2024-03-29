package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.ToastUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class VerifyPhoneActivity extends BaseActivity implements View.OnClickListener {
    public static final String KEY_TYPE = "type";
    public static final int TYPE_REGISTER = 0;
    public static final int TYPE_FORGET_PASS = 1;
    public static final int TYPE_UPDATE_PHONE = 2;
    //private static final int TYPE_UPDATE_PHONE
    private int type;

    private EditText input_phone;
    private EditText input_code;
    private Button txt_send_verify_code;
    private Button btn_verify;

    private String social_type;
    private String social_code;
    private String social_response;
    private String phoneCodeResponse;
    private String phoneNum;
    private String phoneCodeConfirmResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    protected void findViews() {
        input_phone = (EditText) findViewById(R.id.input_phone);
        input_code = (EditText) findViewById(R.id.input_code);
        txt_send_verify_code = (Button) findViewById(R.id.btn_send_verify_code);
        btn_verify = (Button) findViewById(R.id.btn_submit);

        btn_verify.setText(R.string.verify);

        mSendCodeTimer = new VerifyPhoneActivity.SendCodeTimer(txt_send_verify_code);
    }

    protected void setViewsContent() {
        setTitle(R.string.verify_phone);
        Intent intent = getIntent();
        type = intent.getIntExtra(KEY_TYPE, 0);
        social_type = intent.getStringExtra(UserBiz.SOCIAL_TYPE);
        social_code = intent.getStringExtra(UserBiz.SOCIAL_CODE);
        social_response = intent.getStringExtra(UserBiz.KEY_SOCIAL_RESPONSE);
    }

    protected void setViewsListener() {
        txt_send_verify_code.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendCodeTimer != null) mSendCodeTimer.cancel();
    }

    private VerifyPhoneActivity.SendCodeTimer mSendCodeTimer;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verify_code:
                phoneNum = input_phone.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showMessage(R.string.hint_input_phone);
                    return;
                }
                txt_send_verify_code.setEnabled(false);
                boolean requireNotExist = type == TYPE_UPDATE_PHONE || type == TYPE_REGISTER;
                UserBiz.sendPhoneCode(phoneNum, requireNotExist, new SimpleResponseListener<AppResponse<HashMap<String, String>>>(_this) {
                    @Override
                    public void onResponse(AppResponse<HashMap<String, String>> response) {
                        super.onResponse(response);
                        ToastUtil.showMessage(R.string.msg_sent_phone_code);
                        phoneCodeResponse = response.data.get(UserBiz.KEY_PHONE_CODE_RESPONSE);
                        mSendCodeTimer.schedule();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                        txt_send_verify_code.setEnabled(true);
                    }
                });
                break;
            case R.id.btn_submit:
                if (TextUtils.isEmpty(phoneCodeResponse)) {
                    ToastUtil.showMessage(R.string.hint_waring_send_phone_code);
                    return;
                }
                String code = input_code.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showMessage(R.string.hint_input_phone_code);
                    return;
                }
                if (type == TYPE_UPDATE_PHONE) {
                    UserBiz.updatePhone(phoneCodeResponse, phoneNum, new SimpleResponseListener<HashMap<String, String>>(_this) {
                        @Override
                        public void onResponse(HashMap<String, String> response) {
                            super.onResponse(response);
                            Intent result = new Intent();
                            result.putExtra("phone", phoneNum);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            super.onErrorResponse(error);
                            ToastUtil.showMessage("更新手机出错");
                        }
                    });
                } else {
                    UserBiz.confirmPhoneCode(phoneCodeResponse, code, new SimpleResponseListener<AppResponse<HashMap<String, String>>>(_this) {
                        @Override
                        public void onResponse(AppResponse<HashMap<String, String>> response) {
                            super.onResponse(response);
                            switch (type) {
                                case TYPE_REGISTER:
                                    phoneCodeConfirmResponse = response.data.get(UserBiz.KEY_RESPONSE);
                                    if (TextUtils.isEmpty(social_type)) {
                                        Intent setInfoIntent = new Intent(VerifyPhoneActivity.this, SetInfoActivity.class);
                                        setInfoIntent.putExtra(UserBiz.SOCIAL_TYPE, social_type);
                                        setInfoIntent.putExtra(UserBiz.SOCIAL_CODE, social_code);
                                        setInfoIntent.putExtra(UserBiz.KEY_SOCIAL_RESPONSE, social_response);
                                        setInfoIntent.putExtra(UserBiz.KEY_RESPONSE, phoneCodeConfirmResponse);
                                        setInfoIntent.putExtra(UserBiz.KEY_PHONE, phoneNum);
                                        startActivityForFinishResult(setInfoIntent);
                                    } else {
//                                    Intent setInfoIntent = new Intent(VerifyPhoneActivity.this, SetInfoActivity.class);
//                                    setInfoIntent.putExtra(UserBiz.SOCIAL_TYPE, social_type);
//                                    setInfoIntent.putExtra(UserBiz.SOCIAL_CODE, social_code);
//                                    setInfoIntent.putExtra(UserBiz.KEY_SOCIAL_RESPONSE, social_response);
//                                    setInfoIntent.putExtra(UserBiz.KEY_RESPONSE, phoneCodeConfirmResponse);
//                                    setInfoIntent.putExtra(UserBiz.KEY_PHONE, phoneNum);
//                                    startActivityForFinishResult(setInfoIntent);
//                                        UserBiz.register(phoneCodeConfirmResponse, social_type, social_response, phoneNum,
//                                                new UserBiz.RegisterResponseListener(VerifyPhoneActivity.this));
                                        UserBiz.register(phoneCodeConfirmResponse, social_type, social_response, phoneNum,
                                                new UserBiz.LoginResponseListener(VerifyPhoneActivity.this, phoneNum));
                                    }
                                    break;
                                case TYPE_FORGET_PASS:
                                    phoneCodeConfirmResponse = response.data.get(UserBiz.KEY_RESPONSE);
                                    Intent intent = new Intent(VerifyPhoneActivity.this, ResetPassActivity.class);
                                    intent.putExtra(UserBiz.KEY_RESPONSE, phoneCodeConfirmResponse);
                                    intent.putExtra(UserBiz.KEY_PHONE, phoneNum);
                                    startActivityForFinishResult(intent);
                                    break;
                            }
                        }
                    });
                }
                break;
        }
    }

    public static class SendCodeTimer {
        private int second;
        private TextView mTextView;
        private Timer mTimer;
        private TimerTask task;

        public SendCodeTimer(TextView textView) {
            mTextView = textView;
        }

        public void schedule() {
            cancel();
            mTimer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    mTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            second--;
                            if (second > 0) {
                                mTextView.setText(mTextView.getContext().getString(R.string.send_verify_code) + "(" + second + ")");
                            } else {
                                mTextView.setText(R.string.send_verify_code);
                                mTextView.setEnabled(true);
                                cancel();
                            }
                        }
                    });
                }
            };
            second = 60;
            mTextView.setEnabled(false);
            mTextView.setText(mTextView.getContext().getString(R.string.send_verify_code) + "(" + second + ")");
            mTimer.schedule(task, 1000, 1000);
        }

        public void cancel() {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
                task = null;
            }
        }
    }
}