package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.EventRankActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.utils.TimeUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class CompanyAdapter extends BaseSimpleModelAdapter<Event> {
    private Context mContext;
    public CompanyAdapter(final Context context, List<Event> list) {

        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<Event>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Event> adapter, int position, Event item) {
                Intent intent = WebViewActivity.createLaunchIntent(context, item.eventId,
                        WebConstans.Type.TYPE_EVENT);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_company, this);
    }


    public class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Event> implements View.OnClickListener{
        public TextView txt_title;
        public TextView txt_project;
        public TextView txt_time;
        public TextView txt_status;
        public TextView txt_status_comment;
        public ImageView img_avatar;

        private Event event;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Event> adapter) {
            super(parent, layoutId, adapter);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_project = (TextView) itemView.findViewById(R.id.txt_project);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_status = (TextView) itemView.findViewById(R.id.txt_status);
            txt_status_comment = (TextView) itemView.findViewById(R.id.txt_status_comment);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        }

        @Override
        public void onBind(Event event, int position) {
            txt_title.setText(event.userName);
            txt_project.setText(event.categoryName);
            txt_time.setText(TimeUtil.getStrTime(event.beginTime * 1000));
            if(event.coverImg != null){
                BitmapBiz.display(img_avatar, event.coverImg);
            }
            this.event = event;

            if (event.applyStatus.equals(Event.STATUS_NOT_COMMENTED)) {
                txt_status_comment.setVisibility(View.VISIBLE);
                txt_status.setVisibility(View.GONE);
                txt_status_comment.setText("去评价");
                txt_status_comment.setOnClickListener(this);

            } else {
                txt_status_comment.setVisibility(View.GONE);
                txt_status.setVisibility(View.VISIBLE);
                txt_status.setText(event.applyStatus);
                //txt_status.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, EventRankActivity.class);
            intent.putExtra(EventRankActivity.EXTRA_ID, event.eventId);
            intent.putExtra(EventRankActivity.EXTRA_TITLE, event.title);
            intent.putExtra(EventRankActivity.EXTRA_PROJECT, event.categoryName);
            intent.putExtra(EventRankActivity.EXTRA_DATE, event.beginTime);
            intent.putExtra(EventRankActivity.EXTRA_COVER_IMG, event.coverImg);
            mContext.startActivity(intent);
        }
    }

}
