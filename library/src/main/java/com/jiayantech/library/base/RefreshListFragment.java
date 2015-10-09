package com.jiayantech.library.base;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.jiayantech.library.R;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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
public class RefreshListFragment<T extends BaseModel, ResponseT extends AppResponse<List<T>>> extends BaseFragment {

    private static final String REFRESH_ID = "sinceId";
    private static final String MORE_ID = "maxId";

    private BaseSimpleModelAdapter<T> mAdapter;
    private String mAction;
    private Type mType;
    private Map<String, String> mParams;
    private boolean enablePaging = true;  //是否分页

    public void setEnablePaging(boolean enable) {
        enablePaging = enable;
        if (!enablePaging) setNoMore();
    }

    protected void setParams(BaseSimpleModelAdapter<T> adapter, String action) {
        setParams(adapter, action, null);
    }

    protected void setParams(BaseSimpleModelAdapter<T> adapter, String action, Map<String, String> params) {
        mAdapter = adapter;
        mAction = action;
        mParams = params;
        mType = HttpReq.getClassType(this, 1);
        ultimateRecyclerView.setAdapter(mAdapter);
        mAdapter.setCustomLoadMoreView(LayoutInflater.from(getActivity())
                .inflate(R.layout.custom_bottom_progressbar, null));
        onRefresh();
        //refreshingString();
    }

    protected void setHeader(View header) {
        ultimateRecyclerView.setParallaxHeader(header);
    }

    protected View setHeader(int layoutId) {
        View header = getActivity().getLayoutInflater().inflate(layoutId, ultimateRecyclerView.mRecyclerView, false);
        setHeader(header);
        return header;
    }

    protected void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        ultimateRecyclerView.addItemDecoration(itemDecoration);
    }

    private boolean mIsLoading = false;

    protected void onRefresh() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;
        Map<String, String> page = null;
        final int size = mAdapter.getList().size();
//        if (0 != size && enablePaging) {
//            String sinceId = String.valueOf(mAdapter.getList().get(0).id);
//            page = new ArrayMap<>();
//            page.put(REFRESH_ID, sinceId);
//        }
        HttpReq.get(mAction, mParams, page, size == 0, true, mType, new ResponseListener<ResponseT>() {
            private List<T> mCacheList;

            @Override
            public void onLoadResponse(ResponseT response) {
                mCacheList = response.data;
                //mAdapter.clear();
                //mAdapter.addNew(list);
            }

            @Override
            public void onResponse(ResponseT response) {
                //if (!enablePaging) {
                mAdapter.clear();
                //}
                mAdapter.addNew(response.data);
                onFinal();
                onRefreshResponse(mAdapter);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                //if (mCacheList != null && enablePaging) mAdapter.addNew(mCacheList);
                if (mCacheList != null) {
                    mAdapter.addNew(mCacheList);
                    mCacheList = null;
                }
                onFinal();
            }

            private void onFinal() {
                ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                mIsLoading = false;
                //ultimateRecyclerView.requestLayout();
            }
        });
    }

    protected void onRefreshResponse(BaseSimpleModelAdapter<T> adapter) {
    }

    /**
     * 读取历史数据，传入HTTP请求的参数为maxId，向服务器申请id小于maxId的n条数据，取到后
     * 添加到list的尾部
     */
    protected void onLoadMore() {
        //如果不开启分页，直接返回
        if (mIsLoading || (!enablePaging)) {
            return;
        }
        if (0 == mAdapter.getList().size()) {
            return;
        }
        mIsLoading = true;
        Map<String, String> page = new ArrayMap<>();
        String maxId = String.valueOf(mAdapter.getList().get(mAdapter.getList().size() - 1).id);
        page.put(MORE_ID, maxId);
        HttpReq.get(mAction, mParams, page, false, false, mType, new ResponseListener<ResponseT>() {
            @Override
            public void onResponse(ResponseT response) {
                List<T> list = response.data;
                if (list.size() > 0) {
                    mAdapter.addMore(list);
                } else {
//                    if (mAdapter.getCustomLoadMoreView() != null) {
//                        mAdapter.getCustomLoadMoreView().setVisibility(View.GONE);
//                    }
                    setNoMore();
                }
                onFinal();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                onFinal();
            }

            private void onFinal() {
                mIsLoading = false;
                //mAdapter.notifyDataSetChanged();
            }
        });
    }


    protected void setNoMore() {
        if (mAdapter != null && mAdapter.getCustomLoadMoreView() != null) {
//            ValueAnimator animator = new ValueAnimator().ofFloat(1);
//            animator.setDuration(1000);
//            animator.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    float value = (float) animation.getAnimatedValue();
//                    int offset = mAdapter.getCustomLoadMoreView().getTop() - ultimateRecyclerView.mRecyclerView.getBottom();
//                    int scrollBy = (int) ((1 - value) * offset);
//                    ultimateRecyclerView.mRecyclerView.scrollBy(0, -scrollBy);
//                }
//            });
//            animator.start();
            ultimateRecyclerView.mRecyclerView.scrollBy(0, -mAdapter.getCustomLoadMoreView().getHeight());
            //ultimateRecyclerView.mRecyclerView.smoothScrollBy(0, -mAdapter.getCustomLoadMoreView().getHeight());
            ultimateRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setCustomLoadMoreView(null);
                    ultimateRecyclerView.disableLoadmore();
                    mAdapter.notifyDataSetChanged();
                }
            }, 100);
        }
    }


    //////////////////////////
    protected CustomUltimateRecyclerview ultimateRecyclerView;
    protected LinearLayoutManager linearLayoutManager;
    private StoreHouseHeader storeHouseHeader;


    @Override
    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        ultimateRecyclerView = (CustomUltimateRecyclerview) inflater.inflate(R.layout.ultimate_recycler_view, container, false);
        //ultimateRecyclerView = new CustomUltimateRecyclerview(container.getContext());
        return ultimateRecyclerView;
    }

    @Override
    public void onInitView() {
        ultimateRecyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.enableLoadmore();
        if (!enablePaging) setNoMore();

//        ultimateRecyclerView.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
//        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
//            @Override
//            public void onParallaxScroll(float percentage, float offset, View parallax) {
//                //Drawable c = toolbar.getBackground();
//                //c.setAlpha(Math.round(127 + percentage * 128));
//                //toolbar.setBackgroundDrawable(c);
//            }
//        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                onLoadMore();
            }
        });
        ultimateRecyclerView.displayCustomFloatingActionView(false);

        ultimateRecyclerView.setCustomSwipeToRefresh();
        refreshingString();
    }

    private void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(getActivity());
        storeHouseHeader.initWithString("Loading");
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canBePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canBePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                onRefresh();
            }
        });
    }

    public void setRefreshing(boolean refreshing) {
        ultimateRecyclerView.setRefreshing(refreshing);
    }

}
