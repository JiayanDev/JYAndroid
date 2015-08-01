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
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.widget.drawable.BottomLineDrawable;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
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

    public static final String KEY_doctorId = "doctorId";
    public static final String KEY_doctorName = "doctorName";
    public static final String KEY_hospitalId = "hospitalId";
    public static final String KEY_hospitalName = "hospitalName";
    public static final String KEY_operationTime = "operationTime";
    public static final String KEY_price = "price";
    public static final String KEY_satisfyLevel = "satisfyLevel";

    private TextView txt_project;
    private TextView txt_time;
    private TextView txt_doctor;
    private TextView txt_hospital;
    private EditText edit_price;
    private RatingBar rating_bar;

    private String doctorId;
    private String doctorName;
    private String hospitalId;
    private String hospitalName;
    private ArrayList<AppInit.Category> categoryList;
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
        txt_hospital = (TextView) findViewById(R.id.txt_hospital);
        edit_price = (EditText) findViewById(R.id.edit_price);
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
    }

    protected void setViewsContent() {
        setTitle(R.string.title_surgery_info);
        txt_project.setBackgroundDrawable(new BottomLineDrawable(getResources().getColor(R.color.text_normal_color)));
        txt_time.setBackgroundDrawable(new BottomLineDrawable(getResources().getColor(R.color.text_normal_color)));
        txt_doctor.setBackgroundDrawable(new BottomLineDrawable(getResources().getColor(R.color.text_normal_color)));
        txt_hospital.setBackgroundDrawable(new BottomLineDrawable(getResources().getColor(R.color.text_normal_color)));
        edit_price.setBackgroundDrawable(new BottomLineDrawable(getResources().getColor(R.color.text_normal_color)));
        setCategories(getIntent());
    }

    protected void setViewsListener() {
        txt_project.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        txt_doctor.setOnClickListener(this);
        txt_hospital.setOnClickListener(this);
    }

    private void setCategories(Intent intent) {
        categoryList = intent.getParcelableArrayListExtra(SelectProjectActivity.KEY_categories);
        txt_project.setText(AppInit.Category.toNamesString(categoryList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_project:
                Intent intent = new Intent(this, SelectProjectActivity.class);
                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
                startActivityForResult(intent, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        setCategories(data);
                    }
                });
                break;
            case R.id.txt_time:
                new DateTimeHelper(this).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(Calendar calendar) {
                        txt_time.setText(TimeUtil.getStrTime(calendar.getTimeInMillis() / 1000 / 60 * 1000 * 60));
                        operationTime = calendar.getTimeInMillis() / 1000;
                    }
                });
                break;
            case R.id.txt_doctor:
                SearchActivity.start(this, getString(R.string.title_doctor_info), CommBiz.ACTION_DOCTOR_OPTION, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        doctorId = data.getStringExtra(SearchActivity.KEY_ID);
                        doctorName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_doctor.setText(doctorName);
                    }
                });
                break;
            case R.id.txt_hospital:
                SearchActivity.start(this, getString(R.string.title_hospital_info), CommBiz.ACTION_HOSPITAL_OPTION, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        hospitalId = data.getStringExtra(SearchActivity.KEY_ID);
                        hospitalName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_hospital.setText(hospitalName);
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
                intent.putParcelableArrayListExtra(SelectProjectActivity.KEY_categories, categoryList);
                intent.putExtra(KEY_doctorId, doctorId);
                intent.putExtra(KEY_doctorName, doctorName);
                intent.putExtra(KEY_hospitalId, hospitalId);
                intent.putExtra(KEY_hospitalName, hospitalName);
                intent.putExtra(KEY_operationTime, operationTime);
                intent.putExtra(KEY_price, price);
                intent.putExtra(KEY_satisfyLevel, satisfyLevel);
                finishToStartActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
