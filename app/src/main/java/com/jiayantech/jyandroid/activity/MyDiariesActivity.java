package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.MyDiariesFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by janseon on 2015/7/1.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyDiariesActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new MyDiariesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_my_diaries);
    }
}
