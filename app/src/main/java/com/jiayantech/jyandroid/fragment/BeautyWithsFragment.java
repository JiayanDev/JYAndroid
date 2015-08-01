package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.BeautyWithAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class BeautyWithsFragment extends RefreshListFragment<Event, AppResponse<List<Event>>>{
    @Override
    public void onInitView() {
        super.onInitView();
        getActivity().setTitle(R.string.title_my_beauty_with);
        setParams(new BeautyWithAdapter(getActivity(), null), EventBiz.ACTION_LIST);
    }
}
