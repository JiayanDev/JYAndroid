package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

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

    public ProjectCategoryAdapter(Map<String, String> data, List<String> level) {
        super(level);
        mData = data;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_project_category, this);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener<String> l) {
        super.setOnItemClickListener(l);
        l.onItemClick(this, mSelectedPos, mList.get(mSelectedPos));
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<String> {
        private TextView txt_category;
        private final Map<String, String> mData;

        public ViewHolder(ViewGroup parent, int layoutId, ProjectCategoryAdapter adapter) {
            super(parent, layoutId, adapter);
            mAdapter = adapter;
            mData = adapter.mData;
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }

        @Override
        public void onBind(String id, int position) {
            txt_category.setSelected(mAdapter.mSelectedPos == position);
            txt_category.setText(mData.get(id));
        }
    }
}
