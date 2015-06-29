package com.jiayantech.library.base;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.lang.reflect.Type;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: Refresh List Fragment
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class RefreshListFragment<T, ResponseT extends AppResponse<List<T>>> extends BaseFragment {

    private BaseSimpleModelAdapter<T> mAdapter;
    private String mAction;
    private Type mType;

    protected void setParams(BaseSimpleModelAdapter<T> adapter, String action) {
        mAdapter = adapter;
        mAction = action;
        mType = HttpReq.getClassType(this, 1);
        ultimateRecyclerView.setAdapter(mAdapter);
        onRefresh();
    }

    protected void onRefresh() {
        HttpReq.post(mAction, null, mType, new ResponseListener<ResponseT>() {
            @Override
            public void onResponse(ResponseT response) {
                List<T> list = response.data;
                mAdapter.addNew(list);
                ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            }
        });
    }

    protected void onLoadMore() {
        HttpReq.post(mAction, null, mType, new ResponseListener<ResponseT>() {
            @Override
            public void onResponse(ResponseT response) {
                List<T> list = response.data;
                mAdapter.addMore(list);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }


    //////////////////////////
    protected CustomUltimateRecyclerview ultimateRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StoreHouseHeader storeHouseHeader;


    @Override
    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        ultimateRecyclerView = new CustomUltimateRecyclerview(container.getContext());
        return ultimateRecyclerView;
    }

    @Override
    public void onInitView() {
        ultimateRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();
        //ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                //Drawable c = toolbar.getBackground();
                //c.setAlpha(Math.round(127 + percentage * 128));
                //toolbar.setBackgroundDrawable(c);
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                onLoadMore();
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        //simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        //simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        //simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
//                        // linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition,-1);
//                        //   linearLayoutManager.scrollToPosition(maxLastVisiblePosition);
//
//                    }
//                }, 1000);
            }
        });
        // ultimateRecyclerView.hideDefaultFloatingActionButton();
        // ultimateRecyclerView.hideFloatingActionMenu();
        ultimateRecyclerView.displayCustomFloatingActionView(false);


        ultimateRecyclerView.setCustomSwipeToRefresh();
        refreshingString();
    }


    private void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(getActivity());
        //   header.setPadding(0, 15, 0, 0);
        storeHouseHeader.initWithString("Marshal Chen");
        //  header.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                onRefresh();
//                ptrFrameLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //simpleRecyclerViewAdapter.insert("Refresh things", 0);
//                        //ultimateRecyclerView.scrollBy(0, -50);
//                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
//                        //changeHeaderHandler.sendEmptyMessageDelayed(0, 500);
//
//                    }
//                }, 1800);
            }
        });
    }

}
