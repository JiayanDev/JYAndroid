package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.utils.UIUtil;

import java.util.ArrayList;

/**
 * Created by liangzili on 15/9/9.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mPhotoList;

    public PhotoAdapter(Context context) {
        mContext = context;
        mPhotoList = new ArrayList<>();
    }

    public void setPhotoList(ArrayList<String> list) {
        mPhotoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
            holder.photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BitmapBiz.display(holder.photo, mPhotoList.get(position), UIUtil.dip2px(100));
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotosActivity.start(mContext, "", mPhotoList, position);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public ImageView photo;
    }
}
