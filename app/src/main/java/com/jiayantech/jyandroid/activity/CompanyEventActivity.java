package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.CompanyEventFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/30.
 */
public class CompanyEventActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CompanyEventFragment();
    }
}
