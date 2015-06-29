package com.jiayantech.jyandroid.base;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.http.HttpReq;
import com.jiayantech.jyandroid.http.ResponseListener;
import com.jiayantech.jyandroid.http.AppResponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: Refresh List Fragment
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class RefreshListFragment<T, ResponseT extends AppResponse<List<T>>> extends BaseRefreshListFragment {
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

    @Override
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

    @Override
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
}
