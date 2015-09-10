package com.jiayantech.jyandroid.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseGridAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janseon on 2015/7/3.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class ImageAdapter extends BaseGridAdapter<Bitmap> {

    public static final int MAX_COUNT = 6;
    public List<String> urlList = new ArrayList<>();


    public ImageAdapter(ArrayList<Bitmap> list) {
        super(list);
        mList.add(null);
    }

    public int getCurMaxCount() {
        return MAX_COUNT - mList.size() + 1;
    }

    @Override
    public int getAdapterItemCount() {
        int count = super.getAdapterItemCount();
        return count > MAX_COUNT ? MAX_COUNT : count;
    }

    public void addImage(Bitmap bitmap) {
        add(bitmap, mList.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        urlList.remove(position);
        super.remove(position);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_image, this);
    }


    private static class ViewHolder extends BaseGridAdapter.ViewHolder<Bitmap> implements View.OnClickListener {
        private ImageView img_photo;
        private ImageView img_delete;

        public ViewHolder(ViewGroup parent, int layoutId, ImageAdapter adapter) {
            super(parent, layoutId, adapter);
            img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);

            img_delete.setOnClickListener(this);
        }

        private int getCount() {
            return mAdapter.getList().size();
        }

        @Override
        public void onBind(Bitmap bitmap, int position) {
            if (position == getCount() - 1) {
                img_delete.setVisibility(View.GONE);
                img_photo.setImageResource(R.mipmap.icon_add_photo);
                return;
            }
            img_delete.setVisibility(View.VISIBLE);
            img_photo.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_delete:
                    mAdapter.remove(getLayoutPosition());
                    break;
            }
        }
    }
}

