package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseListAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ImageAdapter extends BaseListAdapter<Bitmap> {

    public static final int MAX_SIZE = 15;

    private int itemHeight;

    public ImageAdapter(Context context, ArrayList<Bitmap> list) {
        super(context, list);
    }

    public int getSize() {
        return mList.size();
    }

    @Override
    public int getCount() {
        int count = getSize();
        return count < MAX_SIZE ? count + 1 : count;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_image, null);
            holder.img_photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setLayoutParams(new GridView.LayoutParams(itemHeight, itemHeight));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == getCount() - 1) {
            if (getSize() < getCount()) {
                holder.img_photo.setBackgroundResource(R.color.bg_gray);
                holder.img_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.img_photo.setImageResource(R.drawable.icon_photo_add);
                return convertView;
            }
        }
        Bitmap bitmap = getItem(position);
        holder.img_photo.setBackgroundDrawable(null);
        holder.img_photo.setScaleType(ImageView.ScaleType.CENTER);
        holder.img_photo.setImageBitmap(bitmap);
        return convertView;
    }

    public void resetGridViewHeight(GridView gridView) {
        int columns = 4;
        int rows;
        if (getCount() % columns > 0) {
            rows = getCount() / columns + 1;
        } else {
            rows = getCount() / columns;
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = itemHeight * rows;//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    private class ViewHolder {
        private ImageView img_photo;
    }
}

