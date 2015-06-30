package com.jiayantech.jyandroid.fragment;

import android.view.View;
import android.widget.Toast;

import com.jiayantech.jyandroid.adapter.ActivityAdapter;
import com.jiayantech.jyandroid.biz.ActivityBiz;
import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;

import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ActivityFragment extends RefreshListFragment<User, AppResponse<List<User>>> {

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new ActivityAdapter(null), ActivityBiz.ACTION_ACTIVITY_LIST);
        HttpReq.post(ActivityBiz.ACTION_ACTIVITY_LIST, null, new ResponseListener<AppResponse<List<User>>>() {
            @Override
            public void onResponse(AppResponse<List<User>> response) {
                List<User> list = response.data;
                Toast.makeText(getActivity(), list.get(0).firstName, Toast.LENGTH_LONG).show();
            }
        });
    }
}