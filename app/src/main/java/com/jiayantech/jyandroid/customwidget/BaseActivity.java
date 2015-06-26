package com.jiayantech.jyandroid.customwidget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;

/**
 * Created by liangzili on 15/6/24.
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *重写setContentView将方法传进来的layoutID，嵌套到一个带有toolbar的layout里，再调用super.SetContentView
     *然后将该toolbar设置成activity的默认actionbar
     */
    @Override
    public void setContentView(int layoutResID) {
        View activityView = getLayoutInflater().inflate(R.layout.activity_base, null);
        View view = getLayoutInflater().inflate(layoutResID, (ViewGroup)activityView, false);
        ((ViewGroup) activityView).addView(view);
        Toolbar toolbar = (Toolbar) activityView.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        super.setContentView(activityView);
    }

}
