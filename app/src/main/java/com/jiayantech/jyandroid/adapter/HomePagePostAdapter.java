package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.model.HomePagePost;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by 健兴 on 2015/8/1.
 */
public class HomePagePostAdapter extends BaseSimpleModelAdapter<HomePagePost> {
    private static final int TYPE_TOPIC = 0;
    private static final int TYPE_EVENT = 4;
    private Context mContext;

    public HomePagePostAdapter(Context context, List<HomePagePost> list) {
        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<HomePagePost>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<HomePagePost> adapter,
                                    int position, HomePagePost item) {
                long id = HomePagePost.TYPE_EVENT.equals(item.type) ? item.eventId : item.topicId;
                WebViewActivity.launchActivity(mContext, id, item.type);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        HomePagePost homePagePost = getItem(position);
        if (homePagePost != null) {
            if (HomePagePost.TYPE_TOPIC.equals(homePagePost.type)) {
                return TYPE_TOPIC;
            } else if (HomePagePost.TYPE_EVENT.equals(homePagePost.type)) {
                return TYPE_EVENT;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EVENT) {
            return new EventHolder(parent, this);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new TopicHolder(viewGroup, R.layout.item_home_page_topic, this);
    }

    public static class TopicHolder extends BaseSimpleModelAdapter.ViewHolder<HomePagePost> {
        public ImageView img_cover;

        public TopicHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<HomePagePost> adapter) {
            super(parent, layoutId, adapter);
            img_cover = (ImageView) itemView.findViewById(R.id.img_cover);
        }

        @Override
        public void onBind(HomePagePost item, int position) {
            BitmapBiz.display(img_cover, item.coverImg);
        }
    }

    public static class EventHolder extends TopicHolder {
        public TextView txt_status;
        public ImageView img_doctor;
        public TextView txt_doctor;
        public TextView txt_doctorDesc;

        public EventHolder(ViewGroup parent, BaseSimpleModelAdapter<HomePagePost> adapter) {
            super(parent, R.layout.item_home_page_event, adapter);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            img_doctor = (ImageView) itemView.findViewById(R.id.img_doctor);
            txt_doctor = (TextView) itemView.findViewById(R.id.txt_doctor);
            txt_doctorDesc = (TextView) itemView.findViewById(R.id.txt_doctorDesc);
        }

        @Override
        public void onBind(HomePagePost item, int position) {
            super.onBind(item, position);
            if (item.status.equals(txt_status.getContext().getString(R.string.publish))) {
                txt_status.setBackgroundResource(R.drawable.bg_event_status_going);
                txt_status.setText(R.string.event_recruit);
            } else {
                txt_status.setBackgroundResource(R.drawable.bg_event_status_finish);
                txt_status.setText(R.string.event_finish);
            }
            BitmapBiz.display(img_doctor, item.doctorAvatar);
            txt_doctor.setText(item.doctorName + " " + item.doctorTitle);
            txt_doctorDesc.setText(item.doctorDesc);
        }
    }
}

