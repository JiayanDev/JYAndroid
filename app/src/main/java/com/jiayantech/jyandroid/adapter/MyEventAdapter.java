package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.utils.TimeUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by 健兴 on 2015/7/30.
 */
public class MyEventAdapter extends EventAdapter {

    public MyEventAdapter(Context context, List<Event> list) {
        super(context, list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_my_event, this);
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
            if(event.title == null || event.title.equals("")){
                txt_title.setText(event.getCategoryNamesString());
            }else {
                txt_title.setText(event.title);
            }

            if(event.hospitalName == null && event.doctorName == null){
                txt_info.setVisibility(View.GONE);
            }else {
                txt_info.setVisibility(View.VISIBLE);
                txt_info.setText(event.hospitalName + " " + event.doctorName);
            }

            if(event.beginTime == 0) {
                txt_time.setText(TimeUtil.getStrDateBySecond((long)event.createTime));
            }else{
                txt_time.setText(TimeUtil.getStrDateBySecond(event.beginTime));
            }

            txt_status.setText(event.status);
        }
    }
}