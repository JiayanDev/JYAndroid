package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ProjectCategoryAdapter0 extends RecyclerView.Adapter<ProjectCategoryAdapter0.ViewHolder> {
    protected final List<String> mList;
    private final Map<String, String> mData;
    private int mSelectedPos;

    public ProjectCategoryAdapter0(Map<String, String> data) {
        mList = new ArrayList(data.keySet());
        mData = data;
        mSelectedPos = 0;
    }

    @Override
    public ProjectCategoryAdapter0.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_project_category, this);
    }

    @Override
    public void onBindViewHolder(ProjectCategoryAdapter0.ViewHolder holder, int position) {
        holder.onBind(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
        mOnItemClickListener.onItemClick(mSelectedPos, mData.get(mList.get(mSelectedPos)));
    }


    public interface OnItemClickListener {
        void onItemClick(int position, String id);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ProjectCategoryAdapter0 mAdapter;
        private int mPosition;
        private String mId;
        private View view_selected;
        private TextView txt_category;

        public ViewHolder(ViewGroup parent, int layoutId, ProjectCategoryAdapter0 adapter) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
            mAdapter = adapter;
            view_selected = itemView.findViewById(R.id.view_selected);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
            itemView.setOnClickListener(this);
        }

        public void onBind(String id, int position) {
            mId = id;
            mPosition = position;
            view_selected.setVisibility(mAdapter.mSelectedPos == position ? View.VISIBLE : View.INVISIBLE);
            txt_category.setText(mAdapter.mData.get(id));
        }

        @Override
        public void onClick(View v) {
            if (mAdapter.mOnItemClickListener != null) {
                mAdapter.mOnItemClickListener.onItemClick(mPosition, mId);
            }
        }
    }
}
