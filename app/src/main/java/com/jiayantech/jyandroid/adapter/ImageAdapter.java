package com.jiayantech.jyandroid.adapter;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseGridAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ImageAdapter extends BaseGridAdapter<Bitmap> {

    public static final int MAX_SIZE = 15;


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


    private static class ViewHolder extends BaseGridAdapter.ViewHolder<Bitmap> {
        private ImageView img_photo;

        public ViewHolder(ViewGroup parent, int layoutId, ImageAdapter adapter) {
            super(parent, layoutId, adapter);
            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
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

