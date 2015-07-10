package com.jiayantech.library.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: Base Recyler Adapter
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class BaseRecylerAdapter<T, HV extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<HV> {
    protected final List<T> mList;

    public BaseRecylerAdapter(List<T> list) {
        mList = list == null ? new ArrayList<T>() : list;
    }

    public void addNew(List<T> list) {
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void addMore(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public List<T> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}