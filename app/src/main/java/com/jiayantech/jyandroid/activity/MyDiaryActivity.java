package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.fragment.MyDiaryFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyDiaryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MyDiaryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
