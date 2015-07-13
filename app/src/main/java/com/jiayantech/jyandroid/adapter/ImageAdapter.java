package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.BaseListAdapter;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ImageAdapter extends BaseSimpleModelAdapter<Bitmap> {

    public static final int MAX_SIZE = 15;

    private int itemHeight;

    public ImageAdapter(ArrayList<Bitmap> list) {
        super(list);
        mList.add(null);
    }

    public void addImage(Bitmap bitmap) {
        add(bitmap, mList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_image, this);
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void resetViewHeight(View view, int spanCount) {
        int columns = spanCount;
        int rows;
        if (getItemCount() % columns > 0) {
            rows = getItemCount() / columns + 1;
        } else {
            rows = getItemCount() / columns;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = itemHeight * rows;//最后加上分割线总高度
        view.setLayoutParams(params);
    }


    private static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Bitmap> {
        private ImageView img_photo;

        public ViewHolder(ViewGroup parent, int layoutId, ImageAdapter adapter) {
            super(parent, layoutId, adapter);
            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(adapter.itemHeight, adapter.itemHeight));
        }

        private int getCount() {
            return mAdapter.getItemCount();
        }

        @Override
        public void onBind(Bitmap bitmap, int position) {
            if (position == getCount() - 1) {
                img_photo.setImageResource(R.mipmap.icon_add_photo);
                return;
            }
            img_photo.setImageBitmap(bitmap);
        }
    }
}

