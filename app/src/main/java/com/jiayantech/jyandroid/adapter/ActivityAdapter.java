package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.jiayantech.jyandroid.base.BaseModelAdapter;
import com.jiayantech.jyandroid.model.User;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ActivityAdapter extends BaseModelAdapter<User> {

    public ActivityAdapter(List<User> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        ViewHolder holder = (ViewHolder) rHolder;
        holder.mTextView.setText(mlist.get(position).firstName);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),
                android.R.layout.simple_list_item_1, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}

