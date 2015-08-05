package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/8/4.
 */
public class EditUserInfoActivity extends BaseActivity{
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);

        mUsername = getIntent().getStringExtra(UserInfoActivity.EXTRA_DATA);

        setViews();
    }

    private void setViews(){
        mTextInputLayout = (TextInputLayout)findViewById(R.id.layout_edit_name);
        mEditText = (EditText)findViewById(R.id.edit_name);
        mEditText.setText(mUsername);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                if(mUsername.equals(mEditText.getText().toString())){
                    ToastUtil.showMessage("未作修改，无需保存");
                }else {
                    showProgressDialog();
                    Map<String, String> params = new ArrayMap<>();
                    params.put("name", mEditText.getText().toString());
                    UserBiz.update(params, new ResponseListener<AppResponse>() {

                        @Override
                        public void onResponse(AppResponse appResponse) {
                            dismissProgressDialog();
                            EditFinishEvent event = new EditFinishEvent();
                            event.action = EditFinishEvent.ACTION_EDIT_NAME;
                            event.name = mEditText.getText().toString();
                            EventBus.getDefault().post(event);
                            finish();
                        }
                    });
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
