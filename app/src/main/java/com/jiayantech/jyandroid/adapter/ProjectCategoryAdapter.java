package com.jiayantech.jyandroid.adapter;

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
public class ProjectCategoryAdapter extends BaseSimpleModelAdapter<String> {
    private final Map<String, String> mData;
    private int mSelectedPos;

    public ProjectCategoryAdapter(Map<String, String> data, List<String> level) {
        super(level);
        mData = data;
        mSelectedPos = 0;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_project_category, this);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
        mOnItemClickListener.onItemClick(mSelectedPos, mList.get(mSelectedPos));
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String id);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<String> implements View.OnClickListener {
        private final ProjectCategoryAdapter mAdapter;
        private int mPosition;
        private String mId;
        private View view_selected;
        private TextView txt_category;

        public ViewHolder(ViewGroup parent, int layoutId, ProjectCategoryAdapter adapter) {
            super(parent, layoutId);
            mAdapter = adapter;
            view_selected = itemView.findViewById(R.id.view_selected);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
            itemView.setOnClickListener(this);
        }

        @Override
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
            int oldSelectedPos = mAdapter.mSelectedPos;
            mAdapter.mSelectedPos = mPosition;
            mAdapter.notifyItemChanged(oldSelectedPos);
            mAdapter.notifyItemChanged(mPosition);
        }
    }
}
