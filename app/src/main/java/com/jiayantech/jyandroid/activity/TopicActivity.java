package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiayantech.library.base.SingleFragmentActivity;
import com.jiayantech.jyandroid.fragment.TopicFragment;

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
