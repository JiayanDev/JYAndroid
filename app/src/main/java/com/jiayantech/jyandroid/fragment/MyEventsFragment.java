package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.MyEventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;

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
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.bg_gray_color))
                .size((int) UIUtil.getDimension(R.dimen.normal_margin))
                .build());
        //ultimateRecyclerView.setRecylerViewBackgroundColor(getResources().getColor(R.color.bg_gray));
        setParams(new MyEventAdapter(getActivity(), null), EventBiz.ACTION_LIST);
    }
}
