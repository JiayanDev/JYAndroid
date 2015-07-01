package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.PostHeader;
import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by janseon on 2015/6/29.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PostAdapter extends BaseSimpleModelAdapter<PostHeader> {
    public PostAdapter(List<PostHeader> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(parent, R.layout.item_post);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<PostHeader> {
        //public TextView mTextView;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            //mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(PostHeader postHeader, int position) {
            //mTextView.setText(user.firstName);
        }
    }
}
