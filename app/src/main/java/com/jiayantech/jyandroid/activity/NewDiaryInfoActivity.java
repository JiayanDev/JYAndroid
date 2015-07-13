package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class NewDiaryInfoActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_operationTime = "operationTime";
    public static final String KEY_price = "price";
    public static final String KEY_satisfyLevel = "satisfyLevel";

    private TextView txt_project;
    private TextView txt_time;
    private TextView txt_doctor;
    private EditText edit_price;
    private RatingBar rating_bar;

    private ArrayList<String> categoryId;
    private ArrayList<String> categoryName;
    private long operationTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary_info);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
        txt_project = (TextView) findViewById(R.id.txt_project);
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_doctor = (TextView) findViewById(R.id.txt_doctor);
        edit_price = (EditText) findViewById(R.id.edit_price);
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_surgery_info);
        Intent intent = getIntent();
        categoryId = intent.getStringArrayListExtra(SelectProjectActivity.KEY_categoryIds);
        categoryName = intent.getStringArrayListExtra(SelectProjectActivity.KEY_categoryNames);
        txt_project.setText(categoryName.toString());
    }

    protected void setViewsListener() {
        txt_time.setOnClickListener(this);
        txt_doctor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_time:
                new DateTimeHelper(this).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(Calendar calendar) {
                        txt_time.setText(TimeUtil.getStrTime(calendar.getTimeInMillis()));
                        operationTime = calendar.getTimeInMillis() / 1000;
                    }
                });
                break;
            case R.id.txt_doctor:
                SearchActivity.start(this, getString(R.string.title_doctor_info), CommBiz.ACTION_DOCTOR_OPTION);
                mActivityResultHelper.addActivityResult(new ActivityResult(SearchActivity.REQUEST_CODE_SELECT) {
                    @Override
                    public void onActivityResult(Intent data) {
                        //String doctorId = data.getStringExtra(SearchActivity.KEY_ID);
                        String doctorName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_doctor.setText(doctorName);
                        ToastUtil.showMessage("doctorName: " + doctorName);
                    }
                });
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_next:
                if (operationTime == 0) {
                    ToastUtil.showMessage("select the time");
                    return true;
                }
                String price = edit_price.getText().toString();
                if (TextUtils.isEmpty(price)) {
                    ToastUtil.showMessage("input the price");
                    return true;
                }
                float satisfyLevel = rating_bar.getRating();

                Intent intent = new Intent(this, PublishDiaryActivity.class);
                intent.putStringArrayListExtra(SelectProjectActivity.KEY_categoryIds, categoryId);
                intent.putExtra(KEY_operationTime, operationTime);
                intent.putExtra(KEY_price, price);
                intent.putExtra(KEY_satisfyLevel, satisfyLevel);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //////////////////////
    private ActivityResultHelper mActivityResultHelper = new ActivityResultHelper();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityResultHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
