package com.jiayantech.jyandroid.activity;

import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.customwidget.BaseActivity;

/**
 * Created by liangzili on 15/6/24.
 */
public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}
