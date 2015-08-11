package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class EventAdapter extends BaseSimpleModelAdapter<Event> {
    private Context mContext;
    public EventAdapter(Context context, List<Event> list) {
        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<Event>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Event> adapter,
                                    int position, Event item) {
//                Intent intent = new Intent(mContext, EventDetailActivity.class);
//                intent.putExtra(WebViewFragment.EXTRA_ID, item.id);
//                intent.putExtra(WebViewFragment.EXTRA_TYPE, item.id);
//                intent.putExtra(WebViewFragment.EXTRA_USER_ID, item.userId);
//                intent.putExtra(WebViewFragment.EXTRA_USERNAME, item.userName);
//                mContext.startActivity(intent);
                WebViewActivity.lauchActivity(mContext, item.id, item.userId, item.userName,
                        WebConstans.Type.TYPE_EVENT);
            }
        });
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_card, this);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Event> {
        public TextView txt_title;
        public TextView txt_content;

        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);

        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Event> adapter) {
            super(parent, layoutId, adapter);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
        }

        @Override
        public void onBind(Event event, int position) {
            txt_title.setText(event.desc);
        }
    }
}

