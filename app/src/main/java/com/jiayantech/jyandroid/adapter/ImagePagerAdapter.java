package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.jiayantech.library.base.BasePagerAdapter;
import com.jiayantech.library.http.BitmapBiz;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * @author 健兴
 * @version 1.0
 * @Description
 * @date 2014-4-19
 * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc. All
 * rights reserved.
 */
public class ImagePagerAdapter<T> extends BasePagerAdapter<T> {

    public ImagePagerAdapter(Context context, ArrayList<T> list) {
        super(context, list);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        BitmapBiz.display(photoView, getItem(position).toString());
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return photoView;
    }
}