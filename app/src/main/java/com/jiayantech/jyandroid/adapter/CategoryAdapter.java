package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class CategoryAdapter extends BaseSimpleModelAdapter<String> {
    private final Map<String, String> mData;
    private final List<Boolean> selectedList;

    public CategoryAdapter(Map<String, String> data, List<String> level) {
        super(level);
        mData = data;
        selectedList = new ArrayList<>(level.size());
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
        return new ViewHolder(viewGroup, R.layout.item_category, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).onBind(mList.get(position), selectedList.get(position), position);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<String> {
        private ImageView img_category;
        private TextView txt_category;
        private final Map<String, String> mData;

        public ViewHolder(ViewGroup parent, int layoutId, CategoryAdapter adapter) {
            super(parent, layoutId, adapter);
            mAdapter = adapter;
            mData = adapter.mData;
            img_category = (ImageView) itemView.findViewById(R.id.img_category);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }

        public void onBind(String item, boolean selected, int position) {
            mPosition = position;
            itemView.setBackgroundResource(position % 2 == 0 ? R.drawable.bg_category_white_selector :
                    R.drawable.bg_category_gray_selector);
            itemView.setSelected(selected);
            img_category.setImageResource(R.drawable.icon_nose);
            txt_category.setText(mData.get(item));
        }
    }
}