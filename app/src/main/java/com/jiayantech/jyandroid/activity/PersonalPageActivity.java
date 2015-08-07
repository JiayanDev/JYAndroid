package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.PersonalPageFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/8/6.
 * 个人主页
 */

public class PersonalPageActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new PersonalPageFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人主页");
    }
}
