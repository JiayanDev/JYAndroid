package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseGridAdapter;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.utils.UIUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CategoryAdapter extends BaseGridAdapter<AppInit.Category> {
    private final List<Boolean> selectedList;
    private boolean mShowSelect;

    public CategoryAdapter() {
        this(false);
    }

    public CategoryAdapter(boolean showSelect) {
        super(AppInitManger.getProjectCategoryTopList());
        mShowSelect = showSelect;
        selectedList = new ArrayList<>(mList.size());
        for (int i = 0; i < mList.size(); i++) {
            selectedList.add(false);
        }
    }

    public boolean select(int position) {
        boolean selected = selectedList.get(position);
        selectedList.set(position, !selected);
        notifyItemChanged(position);
        return !selected;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder(viewGroup, R.layout.item_category, this);
        if (mShowSelect) {
            int pad = (int) UIUtil.getDimension(R.dimen.normal_padding);
            viewHolder.txt_category.setPadding(pad, pad, pad, pad);
        }
        return viewHolder;
    }

    @Override
    protected void onBind(BaseSimpleModelAdapter.ViewHolder viewHolder, AppInit.Category category, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (mShowSelect) {
            boolean select = selectedList.get(position);
            holder.itemView.setSelected(select);
            holder.img_category.setSelected(select);
            holder.txt_category.setSelected(select);
        }
        if (category.resId == null) {
            holder.img_category.setImageDrawable(null);
        } else {
            holder.img_category.setImageResource(category.resId);
        }
        holder.txt_category.setText(category.name);
    }

    public static class ViewHolder extends BaseGridAdapter.ViewHolder<AppInit.Category> {
        private ImageView img_category;
        private TextView txt_category;

        public ViewHolder(ViewGroup parent, int layoutId, CategoryAdapter adapter) {
            super(parent, layoutId, adapter);
            mAdapter = adapter;
            img_category = (ImageView) itemView.findViewById(R.id.img_category);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }
    }
}
