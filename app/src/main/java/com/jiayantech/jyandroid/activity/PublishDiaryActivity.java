package com.jiayantech.jyandroid.activity;

import android.content.Intent;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.biz.DiaryBiz;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.ResponseListener;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishDiaryActivity extends PublishPostActivity {
    private ArrayList<String> categoryIds;
    private long operationTime;
    private String price;
    private float satisfyLevel;

    @Override
    protected void setViewsContent() {
        super.setViewsContent();
        Intent intent = getIntent();
        categoryIds = intent.getStringArrayListExtra(SelectProjectActivity.KEY_categoryIds);
        operationTime = intent.getLongExtra(NewDiaryInfoActivity.KEY_operationTime, 0);
        price = intent.getStringExtra(NewDiaryInfoActivity.KEY_price);
        satisfyLevel = intent.getFloatExtra(NewDiaryInfoActivity.KEY_satisfyLevel, 0);
    }

    @Override
    protected void onPost(String content) {
        showProgressDialog();
        DiaryBiz.create(categoryIds.toString(), operationTime, null, null, price, satisfyLevel, null, null, null, content, null, new ResponseListener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
                dismissProgressDialog();
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
