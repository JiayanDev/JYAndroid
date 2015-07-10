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
    public int mSelectedPos;

    public BaseSimpleModelAdapter(List<T> list) {
        super(list);
        mSelectedPos = 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getItemCount() && (customHeaderView != null ? position <= mList.size() : position < mList.size()) && (customHeaderView != null ? position > 0 : true))
            if (holder instanceof ViewHolder) {
                position = customHeaderView != null ? position - 1 : position;
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.mPosition = position;
                viewHolder.onBind(mList.get(position), position);
            }
    }

    public static abstract class ViewHolder<T> extends UltimateRecyclerviewViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<T> adapter) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            itemView.setOnClickListener(mItemClickListener);
            mAdapter = adapter;
        }

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            itemView.setOnClickListener(mItemClickListener);
        }

        public void onBind(T item, int position) {
        }

        protected BaseSimpleModelAdapter<T> mAdapter;
        protected int mPosition;

        private View.OnClickListener mItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter == null) {
                    return;
                }
                if (mAdapter.mOnItemClickListener != null) {
                    mAdapter.mOnItemClickListener.onItemClick(mAdapter, mPosition, mAdapter.mList.get(mPosition));
                }
                int oldSelectedPos = mAdapter.mSelectedPos;
                mAdapter.mSelectedPos = mPosition;
                mAdapter.notifyItemChanged(oldSelectedPos);
                mAdapter.notifyItemChanged(mPosition);
            }
        };
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> l) {
        mOnItemClickListener = l;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(BaseSimpleModelAdapter<T> adapter, int position, T item);
    }
}
