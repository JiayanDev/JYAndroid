package com.jiayantech.jyandroid.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.DiaryBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.ResponseListener;
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

    public static final String KEY_categoryIds = "categoryIds";
    public static final String KEY_categoryName = "categoryName";
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
        Intent intent = getIntent();
        categoryId = intent.getStringArrayListExtra(NewDiaryInfoActivity.KEY_categoryIds);
        categoryName = intent.getStringArrayListExtra(NewDiaryInfoActivity.KEY_categoryName);
        txt_project.setText(categoryName.toString());
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
        txt_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_time:
                showDateDialog();
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
                intent.putStringArrayListExtra(KEY_categoryIds, categoryId);
                intent.putExtra(KEY_operationTime, operationTime);
                intent.putExtra(KEY_price, price);
                intent.putExtra(KEY_satisfyLevel, satisfyLevel);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Calendar mCalendar = Calendar.getInstance();
    private long operationTime = 0;

    public Dialog showDateDialog() {
        Dialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimeDialog();
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public Dialog showTimeDialog() {
        Dialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);
                mCalendar.set(Calendar.MINUTE, minute);
                txt_time.setText(TimeUtil.getTimeText(mCalendar.getTime()));
                operationTime = mCalendar.getTimeInMillis() / 1000;
            }
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }
}
