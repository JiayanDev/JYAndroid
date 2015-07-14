package com.jiayantech.jyandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;

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

//        ultimateRecyclerView.addItemDecoration(
//                new com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration(getActivity(),
//                        LinearLayoutManager.VERTICAL));
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundColor(getResources().getColor(R.color.bg_light_black_color));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                100);
        imageView.setLayoutParams(params);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(imageView));

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

    class DividerItemDecoration extends RecyclerView.ItemDecoration{
        private ImageView dividerView;
        public DividerItemDecoration(ImageView dividerView){
            this.dividerView = dividerView;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(null == dividerView){
                return;
            }

            if(parent.getChildLayoutPosition(view) < 1){
                return;
            }

            //给要绘制的item边界预留位置
            int layoutOrientation = getOrientation(parent);
            if(layoutOrientation == LinearLayoutManager.VERTICAL){
                outRect.top = dividerView.getHeight();
            }else if(layoutOrientation == LinearLayoutManager.HORIZONTAL){
                outRect.left = dividerView.getWidth();
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            LinearLayoutManager layoutManager = (LinearLayoutManager)parent.getLayoutManager();
            if(layoutManager == null){
                return;
            }

            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int orientation = layoutManager.getOrientation();
            int childCount = parent.getChildCount();
            if(orientation == LinearLayoutManager.VERTICAL){
                int right = parent.getWidth();
                for(int i=0; i<childCount; i++){
                    if(i == 0 && firstVisiblePosition == 0){
                        continue;
                    }
                    View childView = parent.getChildAt(i);
                    RecyclerView.LayoutParams params =
                            (RecyclerView.LayoutParams)childView.getLayoutParams();
                    int left = parent.getPaddingLeft() + childView.getPaddingLeft();
                    int bottom = childView.getTop() - params.topMargin;
                    int top = bottom - dividerView.getHeight();
                    dividerView.draw(c);
                }
            }

        }

        private int getOrientation(RecyclerView parent){
            if(parent.getLayoutManager() instanceof LinearLayoutManager){
                LinearLayoutManager layoutManager = (LinearLayoutManager)parent.getLayoutManager();
                return layoutManager.getOrientation();
            }else{
                throw new IllegalStateException("DividerItemDecoration can only be used with a " +
                        "LinearLayoutManager");
            }
        }
    }
}