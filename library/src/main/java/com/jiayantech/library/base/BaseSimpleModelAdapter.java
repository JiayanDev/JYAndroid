package com.jiayantech.library.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: Base Model Adapter
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseSimpleModelAdapter<T> extends BaseModelAdapter<T> {
    public BaseSimpleModelAdapter(List<T> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= mList.size() : position < mList.size()) && (customHeaderView != null ? position > 0 : true))
            if (holder instanceof ViewHolder) {
                position = customHeaderView != null ? position - 1 : position;
                ((ViewHolder) holder).onBind(mList.get(position), position);
            }
    }

    public static abstract class ViewHolder<T> extends UltimateRecyclerviewViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        }

        public abstract void onBind(T item, int position);

    }
}
