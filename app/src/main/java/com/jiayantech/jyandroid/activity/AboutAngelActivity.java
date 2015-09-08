package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;

/**
 * Created by 健兴 on 2015/9/7.
 */
public class AboutAngelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_angel);
    }

    public void onClick(View v) {
        startActivityForResult(BecomeAngelActivity.class, new ActivityResult() {
            @Override
            public void onActivityResult(Intent data) {
                finish();
                startActivity(MyEventsActivity.class);
            }
        });
    }
}

