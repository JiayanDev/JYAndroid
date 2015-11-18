package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.CreateEventSuccessFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/11/18.
 */
public class CreateEventSuccessActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CreateEventSuccessFragment();
    }
}
