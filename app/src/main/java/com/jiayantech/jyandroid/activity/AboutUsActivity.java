package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.Property;
import com.jiayantech.library.utils.Utils;

/**
 * Created by 健兴 on 2015/9/7.
 */
public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setTitle(R.string.about_us);

        final TextView txtPhone = (TextView) findViewById(R.id.txt_phone);
        txtPhone.setText(Property.getProperty("phone"));
        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.takePhoneCall(AboutUsActivity.this, txtPhone.getText().toString());
            }
        });
    }
}

