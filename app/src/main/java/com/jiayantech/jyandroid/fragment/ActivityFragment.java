package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.ActivityAdapter;
import com.jiayantech.jyandroid.base.BaseModelAdapter;
import com.jiayantech.jyandroid.base.BaseRefreshListFrament;
import com.jiayantech.jyandroid.customwidget.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liangzili on 15/6/25.
 */
public class ActivityFragment extends BaseRefreshListFrament {
    private ActivityAdapter mAdapter;

    public static ActivityFragment newInstance(Bundle args) {
        ActivityFragment fragment = new ActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView() {
        super.onInitView();
//        List<String> list = new ArrayList<>();
//        mAdapter = new ActivityAdapter(list);
//        ultimateRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                List<String> list = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}));
//                mAdapter.addNew(list);
                ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            }
        }, 1000);
    }

    @Override
    protected void onLoadMore() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                List<String> list = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}));
               // mAdapter.addNew(list);
            }
        }, 1000);
    }
}