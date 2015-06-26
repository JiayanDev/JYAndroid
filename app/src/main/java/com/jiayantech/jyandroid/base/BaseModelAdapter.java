package com.jiayantech.jyandroid.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: Base Model Adapter
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseModelAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<T> mlist;

    public BaseModelAdapter(List<T> list) {
        if (list == null) {
            mlist = new ArrayList<>();
        } else {
            mlist = list;
        }
    }

    public void addNew(List<T> list) {
        mlist.addAll(0, list);
        notifyDataSetChanged();
    }

    public void addMore(List<T> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
