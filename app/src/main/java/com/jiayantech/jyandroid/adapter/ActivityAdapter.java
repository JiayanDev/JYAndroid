package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.jiayantech.jyandroid.base.BaseModelAdapter;
import com.jiayantech.jyandroid.base.BaseSimpleModelAdapter;
import com.jiayantech.jyandroid.model.User;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ActivityAdapter extends BaseSimpleModelAdapter<User> {
    public ActivityAdapter(List<User> list) {
        super(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, android.R.layout.simple_list_item_1);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<User> {
        public TextView mTextView;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(User user, int position) {
            mTextView.setText(user.firstName);
        }
    }
}

