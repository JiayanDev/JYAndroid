package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Search;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by janseon on 2015/7/14.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class SearchAdapter extends BaseSimpleModelAdapter<Search> {
    public SearchAdapter(List<Search> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_search, this);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Search> {
        public TextView txt_content;
        public TextView txt_hint;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);

        }

        public ViewHolder(ViewGroup parent, int layoutId, SearchAdapter adapter) {
            super(parent, layoutId, adapter);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            txt_hint = (TextView) itemView.findViewById(R.id.txt_hint);
        }

        @Override
        public void onBind(Search search, int position) {
            txt_content.setText(search.name);
        }
    }
}
