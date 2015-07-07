package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class EventAdapter extends BaseSimpleModelAdapter<Event> {
    public EventAdapter(List<Event> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_card, this);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Event> {
        public TextView txt_title;

        public ViewHolder(ViewGroup parent, int layoutId, EventAdapter aadapter) {
            super(parent, layoutId, aadapter);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
        }

        @Override
        public void onBind(Event event, int position) {
            txt_title.setText(event.categoryName);
        }
    }
}

