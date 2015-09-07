package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.utils.ToastUtil;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 健兴 on 2015/7/22.
 */
public class UpdatePassActivity extends BaseActivity {
    private final String KEY_HAS_PSW = "hasPSW";

    private TextView txt_nickname;
    private View layout_pass_cur;
    private EditText input_pass_cur;
    private EditText input_pass_0;
    private EditText input_pass_1;

    private boolean hasPSW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        findViews();
        setViewsContent();
    }

    protected void findViews() {
        txt_nickname = (TextView) findViewById(R.id.txt_nickname);
        layout_pass_cur = findViewById(R.id.layout_pass_cur);
        input_pass_cur = (EditText) findViewById(R.id.input_pass_cur);
        input_pass_0 = (EditText) findViewById(R.id.input_pass_0);
        input_pass_1 = (EditText) findViewById(R.id.input_pass_1);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_update_pass);
        AppInit appInit = AppInitManger.getAppInit();
        txt_nickname.setText(appInit.name);
        UserBiz.hasPsw(new SimpleResponseListener<AppResponse>(this) {
            @Override
            public void onResponse(AppResponse var1) {
                super.onResponse(var1);
                if (var1.data instanceof Map) {
                    Map data = (Map) var1.data;
                    if (data.containsKey(KEY_HAS_PSW)) {
                        hasPSW = (boolean) data.get(KEY_HAS_PSW);
                    }
                }
                if (hasPSW) {
                    layout_pass_cur.setVisibility(View.VISIBLE);
                    input_pass_cur.requestFocus();
                } else {
                    layout_pass_cur.setVisibility(View.GONE);
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String pass_cur = null;
                if (hasPSW) {
                    pass_cur = input_pass_cur.getText().toString();
                    if (TextUtils.isEmpty(pass_cur)) {
                        ToastUtil.showMessage(R.string.hint_input_cur_pass);
                        return;
                    }
                }
                String pass = ResetPassActivity.checkPass(input_pass_0, input_pass_1);
                if (TextUtils.isEmpty(pass)) {
                    return;
                }
                UserBiz.update(pass_cur, pass, new SimpleResponseListener<BaseAppResponse>(this) {
                    @Override
                    public void onResponse(BaseAppResponse var1) {
                        super.onResponse(var1);
                        finish();
                    }
                });
                break;
        }
    }
}

