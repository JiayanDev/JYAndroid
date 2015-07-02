package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class NewDiaryInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary_info);
        findViews();
        setViewsContent();
        setViewsListener();
    }


    protected void findViews() {
    }

    protected void setViewsContent() {
    }

    protected void setViewsListener() {
        setDisplayHomeAsUpEnabled();
    }
}
