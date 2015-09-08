package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.CompanyAdapter;
import com.jiayantech.jyandroid.biz.CompanyBiz;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class CompanyEventFragment extends RefreshListFragment<Event, AppResponse<List<Event>>> {
    @Override
    public void onInitView() {
        super.onInitView();
        getActivity().setTitle(R.string.title_my_company);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration.Builder(getActivity())
                .showFirstEnable(true)
                .color(getResources().getColor(R.color.bg_gray_color))
                .size((int) UIUtil.getDimension(R.dimen.normal_margin))
                .build());
        setParams(new CompanyAdapter(getActivity(), null), CompanyBiz.ACTION_EVENT_COMPANY_LIST);
        setEnablePaging(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengPushManager.getInstance().setUnreadCompanyCount(false);
    }
}
