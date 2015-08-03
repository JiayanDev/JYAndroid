package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.HomeEventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by 健兴 on 2015/8/1.
 */
public class HomeEventFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new HomeEventAdapter(getActivity(), null), EventBiz.ACTION_LIST);
        setHeader(R.layout.fragment_beauty_with);
    }
}