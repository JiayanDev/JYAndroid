package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.adapter.EventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class MyEventsFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new EventAdapter(null), EventBiz.ACTION_LIST);
    }
}
