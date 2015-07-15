package com.jiayantech.jyandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostActivity;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.adapter.TopicCategoryAdapter;
import com.jiayantech.jyandroid.biz.TopicBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.widget.DividerItemDecoration;
import com.jiayantech.jyandroid.widget.category.Category;
import com.jiayantech.jyandroid.widget.category.CategoryAdapter;
import com.jiayantech.jyandroid.widget.category.CategoryAdapter2;
import com.jiayantech.jyandroid.widget.category.CategoryGridView2;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;

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
        Drawable divider = getResources().getDrawable(R.drawable.shape_divider);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
//        ultimateRecyclerView.addItemDecoration(
//                new HorizontalDividerItemDecoration.Builder(getActivity())
//                        .color(getResources().getColor(R.color.bg_gray_color))
//                        .build());

        setParams(new PostAdapter(null, getActivity()), "post/list");

        View headerView = setHeader(R.layout.layout_topic_category);
        initHeaderView(headerView);
        registerReceivers();
    }

    private void initHeaderView(View headerView) {
//        RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.list_category);
//        recyclerView.setHasFixedSize(true);
//       // recyclerView.setAdapter(new TopicCategoryAdapter(getActivity()));
//        recyclerView.setAdapter(new CategoryAdapter(null));
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));


        final CategoryGridView2 gridView2 = (CategoryGridView2) headerView.findViewById(R.id.grid_category);
        gridView2.setAdapter(new CategoryAdapter2());
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra(PostListFragment.EXTRA_CATEGORY, gridView2.getAdapter().getItemId(position));
                getActivity().startActivity(intent);
            }
        });
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