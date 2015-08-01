package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.BeautyWithsFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/30.
 */
public class BeautyWithsActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new BeautyWithsFragment();
    }
}
