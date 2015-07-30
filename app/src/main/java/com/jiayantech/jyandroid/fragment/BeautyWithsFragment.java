package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class BeautyWithsFragment extends RefreshListFragment<Event, AppResponse<List<Event>>>{
    @Override
    protected void setParams(BaseSimpleModelAdapter<Event> adapter, String action) {

    }
}
