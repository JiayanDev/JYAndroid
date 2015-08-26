package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.ApplyEventFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/9.
 */
public class ApplyEventActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return ApplyEventFragment.
                newInstance(getIntent().getLongExtra(ApplyEventFragment.EVENT_ID, -1));
    }

}
