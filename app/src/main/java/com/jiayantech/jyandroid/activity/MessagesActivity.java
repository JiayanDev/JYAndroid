package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.MessagesFragment;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by liangzili on 15/7/27.
 */
public class MessagesActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new MessagesFragment();
    }
}
