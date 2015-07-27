package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ProjectCategoryAdapter extends BaseSimpleModelAdapter<AppInit.Category> {

    public ProjectCategoryAdapter(List<AppInit.Category> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_project_category, this);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener<AppInit.Category> l) {
        super.setOnItemClickListener(l);
        l.onItemClick(this, mSelectedPos, mList.get(mSelectedPos));
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<AppInit.Category> {
        private TextView txt_category;

        public ViewHolder(ViewGroup parent, int layoutId, ProjectCategoryAdapter adapter) {
            super(parent, layoutId, adapter);
            mAdapter = adapter;
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }

        @Override
        public void onBind(AppInit.Category category, int position) {
            txt_category.setSelected(mAdapter.mSelectedPos == position);
            txt_category.setText(category.name);
        }
    }
}
