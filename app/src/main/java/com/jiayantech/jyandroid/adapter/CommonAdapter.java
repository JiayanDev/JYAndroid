package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.model.User;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.HttpReq;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: show activities list
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CommonAdapter<T, VH> extends BaseSimpleModelAdapter<T> {
    private Type mClassType;

    public CommonAdapter(List<T> list) {
        super(list);
        mClassType = HttpReq.getClassType(this, 1);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, android.R.layout.simple_list_item_1);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<User> {
        public TextView mTextView;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            mTextView = (TextView) itemView;
        }

        @Override
        public void onBind(User user, int position) {
            mTextView.setText(user.name);
        }
    }
}

