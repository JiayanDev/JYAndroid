package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditUserInfoActivity extends BaseActivity {
    private EditText mEditName;
    private ImageView mImageDelete;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        mUsername = getIntent().getStringExtra(UserInfoActivity.EXTRA_DATA);
        setTitle("昵称");
        setViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditName.setSelection(mUsername.length());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UIUtil.showSoftKeyBoard(EditUserInfoActivity.this, mEditName);
            }
        }, 500);
    }

    private void setViews() {

        mEditName = (EditText) findViewById(R.id.edit_nickname);
        mEditName.setText(mUsername);
        mImageDelete = (ImageView)findViewById(R.id.img_delete);
        mImageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditName.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (mUsername.equals(mEditName.getText().toString())) {
                    ToastUtil.showMessage("未作修改，无需保存");
                } else {
                    showProgressDialog();
                    Map<String, String> params = new ArrayMap<>();
                    params.put("name", mEditName.getText().toString());
                    UserBiz.update(params, new ResponseListener<AppResponse>() {

                        @Override
                        public void onResponse(AppResponse appResponse) {
                            dismissProgressDialog();
                            EditFinishEvent event = new EditFinishEvent();
                            event.action = EditFinishEvent.ACTION_EDIT_NAME;
                            event.name = mEditName.getText().toString();
                            EventBus.getDefault().post(event);
                            finish();
                        }
                    });
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
