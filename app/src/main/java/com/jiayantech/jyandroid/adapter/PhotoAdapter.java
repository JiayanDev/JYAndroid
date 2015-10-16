package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.http.BitmapBiz;

import java.util.ArrayList;

/**
 * Created by liangzili on 15/9/9.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mPhotoList;
    private int mItemSize;

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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
            holder.photo = (ImageView) convertView.findViewById(R.id.img_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BitmapBiz.display(holder.photo, mPhotoList.get(position));
//        holder.photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PhotosActivity.start(mContext, "", mPhotoList, position);
//            }
//        });

//        Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(final Subscriber<? super Integer> subscriber) {
//                holder.photo.getViewTreeObserver().addOnPreDrawListener(
//                        new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        int x;
//                        holder.photo.getViewTreeObserver().removeOnPreDrawListener(this);
//                        x = holder.photo.getMeasuredWidth();
//                        subscriber.onNext(x);
//                        return true;
//                    }
//                });
//            }
//        }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                BitmapBiz.display(holder.photo, mPhotoList.get(position), integer);
//            }
//        });

//        ViewTreeObserver vto = holder.photo.getViewTreeObserver();
//        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                int x;
//
//
//                return true;
//            }
//        });

        return convertView;
    }

    public static class ViewHolder {
        public ImageView photo;
    }
}
