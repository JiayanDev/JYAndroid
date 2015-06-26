package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.adapter.ActivityAdapter;
import com.jiayantech.jyandroid.base.BaseModelAdapter;
import com.jiayantech.jyandroid.base.RefreshListFragment;
import com.jiayantech.jyandroid.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ActivityFragment0 extends RefreshListFragment<User, List<User>> {

    @Override
    protected BaseModelAdapter<User> getAdapter() {
        List<User> list = new ArrayList<>();
        return new ActivityAdapter(list);
    }

    @Override
    protected String getAction() {
        return "activity.json";
    }
}
