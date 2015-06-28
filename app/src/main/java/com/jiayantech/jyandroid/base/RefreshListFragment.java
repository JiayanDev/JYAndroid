package com.jiayantech.jyandroid.base;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.http.HttpReq;
import com.jiayantech.jyandroid.model.AppResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class RefreshListFragment<T> extends BaseRefreshListFrament {

    private BaseModelAdapter<T> mAdapter;
    private String mAction;
    private Type mType;

    protected void initParams(BaseModelAdapter<T> adapter, String action, Type type) {
        mAdapter = adapter;
        mAction = action;
        mType = type;
    }


    @Override
    public void onInitView() {
        super.onInitView();
        ultimateRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onRefresh() {
        HttpReq.post(mAction, null, mType, new HttpReq.ResponseListener<AppResponse<List<T>>>() {
            @Override
            public void onResponse(AppResponse<List<T>> response) {
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
        HttpReq.post(mAction, null, mType, new HttpReq.ResponseListener<AppResponse<List<T>>>() {
            @Override
            public void onResponse(AppResponse<List<T>> response) {
                List<T> list = response.data;
                mAdapter.addMore(list);
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    public static class AppTypeToken<T> {

        public Type getType() {
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
    }
}
