package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.DiaryBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishDiaryActivity extends PublishPostActivity {
    private LinearLayout layout_container;
    private TextView txt_project;
    private TextView txt_time;

    private String doctorId;
    private String doctorName;
    private String hospitalId;
    private String hospitalName;
    private ArrayList<String> categoryIds;
    private ArrayList<String> categoryNames;
    private long operationTime;
    private String price;
    private float satisfyLevel;

    @Override
    protected void findViews() {
        super.findViews();
        layout_container = (LinearLayout) findViewById(R.id.layout_container);
        View header = getLayoutInflater().inflate(R.layout.layout_publish_diary_header, layout_container, false);
        layout_container.addView(header, 0);
        txt_project = (TextView) header.findViewById(R.id.txt_project);
        txt_time = (TextView) header.findViewById(R.id.txt_time);
        findViewById(R.id.layout_category).setVisibility(View.GONE);
    }

    @Override
    protected void setViewsContent() {
        super.setViewsContent();
        Intent intent = getIntent();
        doctorId = intent.getStringExtra(NewDiaryInfoActivity.KEY_doctorId);
        doctorName = intent.getStringExtra(NewDiaryInfoActivity.KEY_doctorName);
        hospitalId = intent.getStringExtra(NewDiaryInfoActivity.KEY_hospitalId);
        hospitalName = intent.getStringExtra(NewDiaryInfoActivity.KEY_hospitalName);

        setCategories(intent);
        setTime(intent);
        price = intent.getStringExtra(NewDiaryInfoActivity.KEY_price);
        satisfyLevel = intent.getFloatExtra(NewDiaryInfoActivity.KEY_satisfyLevel, 0);
    }

    @Override
    protected void setViewsListener() {
        super.setViewsListener();
        txt_project.setOnClickListener(this);
        txt_time.setOnClickListener(this);
    }

    private void setCategories(Intent intent) {
        categoryIds = intent.getStringArrayListExtra(SelectProjectActivity.KEY_categoryIds);
        categoryNames = intent.getStringArrayListExtra(SelectProjectActivity.KEY_categoryNames);
        txt_project.setText(categoryNames.toString());
    }

    private void setTime(Intent intent) {
        operationTime = intent.getLongExtra(NewDiaryInfoActivity.KEY_operationTime, 0);
        txt_time.setText(TimeUtil.getStrTime(operationTime * 1000));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.txt_project:
                Intent intent = new Intent(this, SelectProjectActivity.class);
                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
                startActivityForResult(intent, SelectProjectActivity.REQUEST_CODE_SELECT);
                mActivityResultHelper.addActivityResult(new ActivityResult(SelectProjectActivity.REQUEST_CODE_SELECT) {
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
                        txt_time.setText(TimeUtil.getStrTime(calendar.getTimeInMillis()));
                        operationTime = calendar.getTimeInMillis() / 1000;
                    }
                });
                break;
        }
    }

    @Override
    protected void onPost(String content) {
        showProgressDialog();
        String photoUrls = toString(urlList);
        DiaryBiz.create(categoryIds.toString(), operationTime, hospitalId, doctorId, doctorName, price, satisfyLevel, content, photoUrls, new ResponseListener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
                dismissProgressDialog();
                BroadcastHelper.send(Broadcasts.ACTION_PUBLISH_DIARY_BOOK);
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                dismissProgressDialog();
            }
        });
    }
}
