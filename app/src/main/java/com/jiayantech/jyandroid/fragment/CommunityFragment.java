package com.jiayantech.jyandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostActivity;
import com.jiayantech.jyandroid.adapter.CategoryAdapter;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;

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

    private final int spanCount = 4;

    @Override
    public void onInitView() {
        super.onInitView();
        //Drawable divider = getResources().getDrawable(R.drawable.shape_divider);
        //ultimateRecyclerView.addItemDecoration(new DividerItemDecoration(divider));
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration.Builder(getActivity())
                .showFirstEnable(false)
                .color(getResources().getColor(R.color.bg_gray_color))
                .size((int) UIUtil.getDimension(R.dimen.normal_margin))
                .build());

        setParams(new PostAdapter(null, getActivity()), PostBiz.ACTION_LIST);
//        final View header = setHeader(R.layout.layout_topic);
//        final ImageView topicImage = (ImageView)header.findViewById(R.id.recommend_topic);
//        PostBiz.getOneTopic(new ResponseListener<AppResponse<Topic>>() {
//            @Override
//            public void onResponse(final AppResponse<Topic> postAppResponse) {
//                BitmapBiz.display(topicImage, postAppResponse.data.coverImg);
//                header.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        WebViewActivity.launchActivity(getActivity(), postAppResponse.data.topicId,
//                                WebConstans.Type.TYPE_TOPIC);
//                    }
//                });
//            }
//        });

        //View headerView = setHeader(R.layout.layout_topic_category);
        //initHeaderView(headerView);
        setHeader(R.layout.layout_topic);
        registerReceivers();
    }

    private void initHeaderView(View headerView) {
        final RecyclerView recyclerView = (RecyclerView) headerView.findViewById(R.id.grid_category);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setAdapter(new TopicCategoryAdapter(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.resetGridHeight(recyclerView, spanCount);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener<AppInit.Category>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<AppInit.Category> adapter, int position, AppInit.Category item) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                intent.putExtra(PostListFragment.EXTRA_CATEGORY, item);
                getActivity().startActivity(intent);
            }
        });

//        final CategoryGridView2 gridView2 = (CategoryGridView2) headerView.findViewById(R.id.grid_category);
//        gridView2.setAdapter(new CategoryAdapter2());
//        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), PostActivity.class);
//                intent.putExtra(PostListFragment.EXTRA_CATEGORY, gridView2.getAdapter().getItemId(position));
//                getActivity().startActivity(intent);
//            }
//        });
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