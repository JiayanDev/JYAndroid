package com.jiayantech.jyandroid.base;

import com.jiayantech.jyandroid.http.HttpReq;
import com.jiayantech.jyandroid.model.AppResponse;

import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class RefreshListFragment<T, LT extends List<T>> extends BaseRefreshListFrament {

    private BaseModelAdapter<T> mAdapter;

    protected abstract BaseModelAdapter<T> getAdapter();

    protected abstract String getAction();


    @Override
    public void onInitView() {
        super.onInitView();
        mAdapter = getAdapter();
        ultimateRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onRefresh() {
        HttpReq.post(getAction(), null, new HttpReq.ResponseListener<AppResponse<LT>>() {
            @Override
            public void onResponse(AppResponse<LT> response) {
                List<T> list = response.data;
                mAdapter.addNew(list);
                ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
            }
        });
    }

    @Override
    protected void onLoadMore() {
        HttpReq.post(getAction(), null, new HttpReq.ResponseListener<AppResponse<List<T>>>() {
            @Override
            public void onResponse(AppResponse<List<T>> response) {
                List<T> list = response.data;
                mAdapter.addMore(list);
            }
        });
    }
}
