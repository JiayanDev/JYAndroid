package com.jiayantech.jyandroid.activity;

import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.webview.PediaFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/11/27.
 */
public class PediaActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        String url = getIntent().getStringExtra(PediaFragment.EXTRA_URL);
        return PediaFragment.newInstance(url);
    }
}
