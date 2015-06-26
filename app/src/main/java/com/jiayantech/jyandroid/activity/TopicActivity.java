package com.jiayantech.jyandroid.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.jiayantech.jyandroid.customwidget.SingleFragmentActivity;
import com.jiayantech.jyandroid.customwidget.TopicFragment;

/**
 * Created by liangzili on 15/6/25.
 */
public class TopicActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new TopicFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(true);
    }
}
