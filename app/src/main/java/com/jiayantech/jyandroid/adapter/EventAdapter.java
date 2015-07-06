package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class EventAdapter extends BaseSimpleModelAdapter<User> {
    public EventAdapter(List<User> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, android.R.layout.simple_list_item_1, this);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<User> {
        public TextView mTextView;

        public ViewHolder(ViewGroup parent, int layoutId, EventAdapter aadapter) {
            super(parent, layoutId, aadapter);
            mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(User user, int position) {
            mTextView.setText(user.firstName);
        }
    }
}

