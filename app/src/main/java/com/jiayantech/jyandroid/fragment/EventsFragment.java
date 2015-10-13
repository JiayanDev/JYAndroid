package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.adapter.EventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.eventbus.ApplyAngelFinishEvent;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class EventsFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new EventAdapter(getActivity(), null), EventBiz.ACTION_LIST);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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