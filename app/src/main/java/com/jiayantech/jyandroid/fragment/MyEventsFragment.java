package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.MyEventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.eventbus.ApplyAngelFinishEvent;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.umeng_push.UmengPushManager;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyEventsFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    WebView mEmptyWebView;
    @Override
    public void onInitView() {
        super.onInitView();
        addItemDecoration(new DividerItemDecoration.Builder(getActivity()).build());
        setParams(new MyEventAdapter(getActivity(), null), EventBiz.ACTION_LIST);
        //setParams(new MyEventAdapter(getActivity(), null), "");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UmengPushManager.getInstance().setUnreadAngelCount(false);
        EventBus.getDefault().register(this);
    }

    @Override
    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        ultimateRecyclerView = (CustomUltimateRecyclerview) inflater.inflate(R.layout.angel_recycler_view, container, false);
        //ultimateRecyclerView = new CustomUltimateRecyclerview(container.getContext());
        return ultimateRecyclerView;
    }

    @Override
    protected void onRefreshResponse(BaseSimpleModelAdapter<Event> adapter) {
        if (mOnRefreshResponseListener != null)
            mOnRefreshResponseListener.onRefreshResponse(adapter);
    }

    private OnRefreshResponseListener mOnRefreshResponseListener;

    public void setOnRefreshResponseListener(OnRefreshResponseListener l) {
        this.mOnRefreshResponseListener = l;
    }

    public interface OnRefreshResponseListener {
        void onRefreshResponse(BaseSimpleModelAdapter<Event> adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(mEmptyWebView != null){
            mEmptyWebView.destroy();
            mEmptyWebView = null;
        }
    }

    public void onEvent(ApplyAngelFinishEvent event){
        List<Event> list = getList();
        Event event1 = new Event();
        event1.categoryName = event.category;
        event1.beginTime = event.time;

        list.add(0, event1);
        refreshItem();
    }


}
