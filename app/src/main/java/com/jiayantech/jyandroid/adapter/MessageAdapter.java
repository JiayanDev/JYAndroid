package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Message;
import com.jiayantech.library.base.BaseModelAdapter;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class MessageAdapter extends BaseSimpleModelAdapter<Message>{


    public MessageAdapter(List<Message> list) {
        super(list);

    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_message);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Message>{


        public ViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Message> adapter) {
            super(parent, layoutId, adapter);

        }

        @Override
        public void onBind(Message item, int position) {
            super.onBind(item, position);
        }
    }
}
