package com.jiayantech.jyandroid.adapter;

import android.view.ViewGroup;

import com.jiayantech.jyandroid.model.Topic;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/2.
 */
public class TopicAdapter extends BaseSimpleModelAdapter<Topic>{
    public TopicAdapter(List<Topic> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return null;
    }
}
