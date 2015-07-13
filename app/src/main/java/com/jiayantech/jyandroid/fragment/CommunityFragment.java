package com.jiayantech.jyandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.adapter.TopicCategoryAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.model.Topic;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by liangzili on 15/6/25.
 * *
 * Update by janseon on 15/6/30
 */
public class CommunityFragment extends RefreshListFragment<Post, AppResponse<List<Post>>> {
    public static CommunityFragment newInstance(Bundle args) {
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new PostAdapter(null, getActivity()), TopicBiz.ACTION_TOPIC_LIST);
        View headerView = setHeader(R.layout.layout_topic_category);
        initHeaderView(headerView);
        registerReceivers();
    }

    private void initHeaderView(View headerView) {
        RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.list_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TopicCategoryAdapter(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }

    private void registerReceivers() {
        mBroadcastHelper.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onRefresh();
            }
        }, Broadcasts.ACTION_PUBLISH_TOPIC);
    }

    private BroadcastHelper mBroadcastHelper = new BroadcastHelper();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBroadcastHelper.onDestroy();
    }
}