package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.model.HomePageEvent;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by 健兴 on 2015/8/1.
 */
public class HomeEventAdapter extends BaseSimpleModelAdapter<HomePageEvent> {
    private Context mContext;

    public HomeEventAdapter(Context context, List<HomePageEvent> list) {
        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<HomePageEvent>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<HomePageEvent> adapter,
                                    int position, HomePageEvent item) {
                long id = 0;
                switch (item.type){
                    case HomePageEvent.TYPE_EVENT:
                        //id = item.eventId;
                        id = 927;
                        break;
                    case HomePageEvent.TYPE_TOPIC:
                        id = item.topicId;
                        break;
                }
                WebViewActivity.launchActivity(mContext, id, item.userId, item.userName,
                        item.type);
            }
        });
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_home_event, this);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<HomePageEvent> {
        public TextView txt_title;
        public TextView txt_content;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);

        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<HomePageEvent> adapter) {
            super(parent, layoutId, adapter);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
        }

        @Override
        public void onBind(HomePageEvent event, int position) {
            txt_title.setText(event.desc);
        }
    }
}

