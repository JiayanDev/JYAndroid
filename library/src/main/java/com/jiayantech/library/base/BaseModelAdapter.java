package com.jiayantech.library.base;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.security.SecureRandom;
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
public abstract class BaseModelAdapter<T> extends UltimateViewAdapter {
    protected final List<T> mList;

    public BaseModelAdapter(List<T> list) {
        mList = list == null ? new ArrayList<T>() : list;
    }


    @Override
    public int getAdapterItemCount() {
        return mList.size();
    }

    public void addNew(List<T> list) {
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void addMore(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void insert(T item, int position) {
        insert(mList, item, position);
    }

    public void remove(int position) {
        remove(mList, position);
    }

    public void clear() {
        clear(mList);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(mList, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }


    public T getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < mList.size())
            return mList.get(position);
        else return null;
    }

    public List<T> getList(){
        return mList;
    }
}