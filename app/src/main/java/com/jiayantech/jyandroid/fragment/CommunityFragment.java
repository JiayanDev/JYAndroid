package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.model.Topic;
import com.jiayantech.library.base.RefreshListFragment;
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
        //setParams(new PostAdapter(null), ActivityBiz.ACTION_TOPIC_LIST);
        setParams(new PostAdapter(null, getActivity()), TopicBiz.ACTION_TOPIC_LIST);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false);
        ultimateRecyclerView.setParallaxHeader(headerView);
    }

}