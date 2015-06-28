package com.jiayantech.jyandroid.fragment;

import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.adapter.ActivityAdapter;
import com.jiayantech.jyandroid.base.BaseModelAdapter;
import com.jiayantech.jyandroid.base.RefreshListFragment;
import com.jiayantech.jyandroid.http.HttpReq;
import com.jiayantech.jyandroid.model.AppResponse;
import com.jiayantech.jyandroid.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ActivityFragment0 extends RefreshListFragment<User> {
    @Override
    public void onInitView() {
        ActivityAdapter adapter = new ActivityAdapter(new ArrayList<User>());
        String action = "activity.json";
        Type type = new AppTypeToken<AppResponse<List<User>>>().getType();
        initParams(adapter, action, type);
        super.onInitView();

        HttpReq.post(action, null, new HttpReq.ResponseListener<AppResponse<List<User>>>() {
            @Override
            public void onResponse(AppResponse<List<User>> response) {
                List<User> list = response.data;
                Toast.makeText(getActivity(), list.get(0).firstName, Toast.LENGTH_LONG).show();
            }
        });
    }
}
