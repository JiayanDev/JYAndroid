package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import de.greenrobot.event.EventBus;

/**
 * Created by 健兴 on 2015/8/3.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

//    protected TextInputLayout input_pass_0;
//    protected TextInputLayout input_pass_1;
//    private Button btn_reset;
//
//    private String phoneNum;
//    private String phoneCodeConfirmResponse;
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_GENDER = "gender";
    public static final String EXTRA_PROVINCE = "province";
    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_BIRTHDAY = "birthday";

    public static final int ACTION_EDIT_NAME = 0;

    private TextView mNameText;
    private TextView mGenderText;
    private TextView mProvinceText;
    private TextView mCityText;
    private TextView mBirthdayText;
    private TextView mPhoneText;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findViews();
        setViewsContent();
        EventBus.getDefault().register(this);
//        findViews();
//        setViewsContent();
//        setViewsListener();
//        final long currentTimeMillis = System.currentTimeMillis();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.i("time", "timeMillis start = " + (System.currentTimeMillis() - currentTimeMillis));
//                String str = AssertsUtil.getAssertsFileString("area.json");
//                LogUtil.i("time", "timeMillis read = " + (System.currentTimeMillis() - currentTimeMillis));
//                try {
//                    Object object = new Gson().fromJson(str, Object.class);
//                    LogUtil.i("time", "timeMillis Gson parse = " + (System.currentTimeMillis() - currentTimeMillis));
//                    JSONArray jsonArray = new JSONArray(str);
//                    LogUtil.i("time", "timeMillis json parse = " + (System.currentTimeMillis() - currentTimeMillis));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void findViews() {
//        input_pass_0 = (TextInputLayout) findViewById(R.id.input_pass_0);
//        input_pass_1 = (TextInputLayout) findViewById(R.id.input_pass_1);
//        btn_reset = (Button) findViewById(R.id.btn_reset);
        mNameText = (TextView)findViewById(R.id.txt_name);
        mGenderText = (TextView)findViewById(R.id.txt_gender);
        mProvinceText = (TextView)findViewById(R.id.txt_province);
        mCityText = (TextView)findViewById(R.id.txt_city);
        mBirthdayText = (TextView)findViewById(R.id.txt_birthday);
        mPhoneText = (TextView)findViewById(R.id.txt_phone);
    }

    protected void setViewsContent() {
//        setTitle(R.string.title_reset_pass);
//        Intent intent = getIntent();
//        phoneNum = intent.getStringExtra(UserBiz.KEY_PHONE);
//        phoneCodeConfirmResponse = intent.getStringExtra(UserBiz.KEY_RESPONSE);
        mNameText.setText(AppInitManger.getUserName());
        mGenderText.setText(AppInitManger.getUserGender() == 1? "男": "女");
        mProvinceText.setText(AppInitManger.getProvince());
        mCityText.setText(AppInitManger.getCity());
        mBirthdayText.setText(String.valueOf(AppInitManger.getBirthday()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_name:
                startEditActivity(ACTION_EDIT_NAME, AppInitManger.getUserName());
        }
    }

    private void startEditActivity(int action, String data){
        Intent intent = new Intent(this, EditUserInfoActivity.class);
        intent.putExtra(EXTRA_ACTION, action);
        intent.putExtra(EXTRA_DATA, data);
        startActivity(intent);
    }

//    @Override
//    public void onClick(View v) {
//
//    }

//    protected void setViewsListener() {
//        btn_reset.setOnClickListener(this);
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_reset:
//                String pass = checkPass();
//                if (TextUtils.isEmpty(pass)) {
//                    return;
//                }
//                UserBiz.update(phoneCodeConfirmResponse, phoneNum, pass, new ResponseListener<BaseAppResponse>() {
//                    @Override
//                    public void onResponse(BaseAppResponse baseAppResponse) {
//                        ActivityResult.onFinishResult(UserInfoActivity.this);
//                    }
//                });
//                break;
//        }
//    }

//    protected String checkPass() {
//        String pass_0 = input_pass_0.getEditText().getText().toString();
//        if (TextUtils.isEmpty(pass_0)) {
//            ToastUtil.showMessage(R.string.hint_input_set_pass);
//            return null;
//        }
//        String pass_1 = input_pass_1.getEditText().getText().toString();
//        if (TextUtils.isEmpty(pass_1)) {
//            ToastUtil.showMessage(R.string.hint_input_set_pass_again);
//            return null;
//        }
//        if (!pass_0.equals(pass_1)) {
//            ToastUtil.showMessage(R.string.waring_pass_is_not_same);
//            return null;
//        }
//        return pass_0;
//    }

    public void onEvent(EditFinishEvent event){
        switch (event.action){
            case EditFinishEvent.ACTION_EDIT_NAME:
                mNameText.setText(event.name);
                break;
            case EditFinishEvent.ACTION_EDIT_GENDER:
                mGenderText.setText(event.gender == 1? "男": "女");
                break;
            case EditFinishEvent.ACTION_EDIT_PROVINCE:
                mProvinceText.setText(event.province);
                break;
            case EditFinishEvent.ACTION_EDIT_CITY:
                mCityText.setText(event.city);
                break;
            case EditFinishEvent.ACTION_EDIT_BIRTHDAY:
                mBirthdayText.setText(String.valueOf(event.birthday));
                break;
            case EditFinishEvent.ACTION_EDIT_PHONE:
                mPhoneText.setText(event.phone);
                break;
        }
    }
}
