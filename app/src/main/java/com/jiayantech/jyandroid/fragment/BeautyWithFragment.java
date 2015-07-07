package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by liangzili on 15/6/25.
 */
public class BeautyWithFragment extends RefreshListFragment<Post, AppResponse<List<Post>>> {

    public static BeautyWithFragment newInstance(Bundle args) {
        BeautyWithFragment fragment = new BeautyWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new PostAdapter(null, getActivity()), TopicBiz.ACTION_TOPIC_LIST);
        setHeader(R.layout.fragment_beauty_with);
    }
}
