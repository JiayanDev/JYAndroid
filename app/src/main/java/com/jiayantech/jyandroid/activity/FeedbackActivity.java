package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;

/**
 * Created by janseon on 15/9/8.
 */
public class FeedbackActivity extends BaseActivity {
    protected EditText edit_content;
    protected EditText edit_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setViews();
    }


    private void setViews() {
        edit_content = (EditText) findViewById(R.id.edit_content);
        edit_contact = (EditText) findViewById(R.id.edit_contact);
        setTitle(R.string.feedback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UIUtil.showSoftKeyBoard(FeedbackActivity.this, edit_content);
            }
        }, 500);
    }

    public void onClick(View v) {
        String content = edit_content.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showMessage(R.string.hint_input_null);
            return;
        }
        String contact = edit_contact.getText().toString();
        UserBiz.feedback(content, contact, new SimpleResponseListener<BaseAppResponse>(this) {
            @Override
            public void onResponse(BaseAppResponse baseAppResponse) {
                super.onResponse(baseAppResponse);
                finish();
            }
        });
    }
}
