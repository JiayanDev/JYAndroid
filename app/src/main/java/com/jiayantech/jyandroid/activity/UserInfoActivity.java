package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.AssertsUtil;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 健兴 on 2015/8/3.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout input_pass_0;
    protected TextInputLayout input_pass_1;
    private Button btn_reset;

    private String phoneNum;
    private String phoneCodeConfirmResponse;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
//        findViews();
//        setViewsContent();
//        setViewsListener();
        final long currentTimeMillis = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.i("time", "timeMillis start = " + (System.currentTimeMillis() - currentTimeMillis));
                String str = AssertsUtil.getAssertsFileString("area.json");
                LogUtil.i("time", "timeMillis read = " + (System.currentTimeMillis() - currentTimeMillis));
                try {
                    Object object = new Gson().fromJson(str, Object.class);
                    LogUtil.i("time", "timeMillis Gson parse = " + (System.currentTimeMillis() - currentTimeMillis));
                    JSONArray jsonArray = new JSONArray(str);
                    LogUtil.i("time", "timeMillis json parse = " + (System.currentTimeMillis() - currentTimeMillis));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                        ActivityResult.onFinishResult(UserInfoActivity.this);
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
