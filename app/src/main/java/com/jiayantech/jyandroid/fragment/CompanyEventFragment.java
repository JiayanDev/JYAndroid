package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.CompanyAdapter;
import com.jiayantech.jyandroid.biz.CompanyBiz;
import com.jiayantech.jyandroid.eventbus.EventRankFinishEvent;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;
import com.jiayantech.umeng_push.UmengPushManager;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/30.
 */
public class CompanyEventFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    @Override
    public void onInitView() {
        super.onInitView();
        getActivity().setTitle(R.string.title_my_company);
        setParams(new CompanyAdapter(getActivity(), null), CompanyBiz.ACTION_EVENT_COMPANY_LIST);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration.Builder(getActivity())
                .showFirstEnable(true)
                .color(getResources().getColor(R.color.bg_gray))
                .size((int) UIUtil.getDimension(R.dimen.normal_margin))
                .showLastDivider()
                .build());

        //setEnablePaging(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengPushManager.getInstance().setUnreadCompanyCount(false);
        EventBus.getDefault().register(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventRankFinishEvent event){
        List<Event> list = getList();
        for(Event e: list){
            if(e.eventId == event.eventId){
                e.applyStatus = Event.STATUS_COMMENTED;
            }
        }
        refreshItem();
    }
}
