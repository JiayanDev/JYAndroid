package com.jiayantech.jyandroid.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostActivity;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.adapter.TopicCategoryAdapter;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.widget.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by liangzili on 15/6/25.
 */
public class PostListFragment extends RefreshListFragment<Post, AppResponse<List<Post>>>{

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_SHOW_HEADER = "show_header";
    public static final String EXTRA_CATEGORY = "category";
    public static final String TYPE_TOPIC = "topic";
    public static final String TYPE_DIARY = "diary";
    public static final String TYPE_POST = "post";

    private String mType;
    private int mCategoryId;
    private String mAction = TopicBiz.ACTION_TOPIC_LIST;

    public static PostListFragment newInstance(String type, int categoryId, boolean showHeader) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);
        args.putBoolean(EXTRA_SHOW_HEADER, showHeader);
        args.putInt(EXTRA_CATEGORY, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mType = getArguments().getString(EXTRA_TYPE, null);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_topic, container, false);
//        return view;
//    }

    @Override
    public void onInitView() {
        super.onInitView();
        mType = getArguments().getString(EXTRA_TYPE);
        mCategoryId = getArguments().getInt(EXTRA_CATEGORY);
        Map<String, String> params = new ArrayMap<>();
        params.put("type", mType);
        params.put("categoryId", String.valueOf(mCategoryId));

        if(mType != null){
            mAction = "post/list";
        }

        Drawable divider = getResources().getDrawable(R.drawable.shape_divider);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
        setParams(new PostAdapter(null, getActivity()), mAction, params);
        boolean flag = getArguments().getBoolean(EXTRA_SHOW_HEADER);
        if(flag) {
            View headerView = setHeader(R.layout.layout_topic_category);
            initHeaderView(headerView);
        }
    }

    public void initHeaderView(View headerView) {
        RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.grid_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new TopicCategoryAdapter(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }
}
