package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.EventRankActivity;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.utils.TimeUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class BeautyWithAdapter extends BaseSimpleModelAdapter<Event> {
    public BeautyWithAdapter(final Context context, List<Event> list) {
        super(list);
        setOnItemClickListener(new OnItemClickListener<Event>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Event> adapter, int position, Event item) {
                Intent intent = new Intent(context, EventRankActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_event_company, this);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Event> {
        public TextView txt_title;
        public TextView txt_info;
        public TextView txt_time;
        public TextView txt_status;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Event> adapter) {
            super(parent, layoutId, adapter);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_info = (TextView) itemView.findViewById(R.id.txt_info);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
        }

        @Override
        public void onBind(Event event, int position) {
            txt_title.setText(event.desc);
            txt_info.setText(event.hospitalName + " " + event.doctorName);
            txt_time.setText(TimeUtil.getStrTime(event.beginTime));
            txt_status.setText(event.applyStatus);
        }
    }
}