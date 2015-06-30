package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.model.PostHeader;
import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseModelAdapter;
import com.jiayantech.library.base.BaseSimpleModelAdapter;

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
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public BaseSimpleModelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new HeaderHolder(parent, android.R.layout.simple_list_item_1);
            default:
                return new ViewHolder(parent, android.R.layout.simple_list_item_1);
        }
    }

    public static class HeaderHolder extends BaseSimpleModelAdapter.ViewHolder<User> {
        public TextView mTextView;

        public HeaderHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(User user, int position) {
            mTextView.setText(user.firstName);
        }
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<User> {
        public TextView mTextView;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(User user, int position) {
            mTextView.setText(user.firstName);
        }
    }
}
