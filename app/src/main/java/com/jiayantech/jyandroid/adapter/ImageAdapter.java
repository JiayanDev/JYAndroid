package com.jiayantech.jyandroid.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseGridAdapter;
import com.jiayantech.library.utils.UIUtil;
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

        //urlList.remove(position);
        super.remove(position);
    }

    public void removeAll(){
        int size = mList.size();
        for(int i = 0; i < size - 1; i++){
            mList.remove(0);
        }
        notifyDataSetChanged();
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(viewGroup, R.layout.item_image, this);
    }

    private static final int pad = UIUtil.dip2px(20);

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
                img_photo.setPadding(pad, pad, pad, pad);
                img_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                img_photo.setBackgroundResource(R.color.divider_gray_color);
                img_photo.setImageResource(R.mipmap.icon_add_photo);
                return;
            }
            img_photo.setPadding(0, 0, 0, 0);
            img_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img_delete.setVisibility(View.VISIBLE);
            img_photo.setBackgroundDrawable(null);
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

