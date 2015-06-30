package com.jiayantech.jyandroid.customwidget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/6/29.
 */
class BannerPagerAdapter extends PagerAdapter{

    private Context mContext;
    private List<Banner> mImageList;
    private List<ImageView> mImageViewList;

    public BannerPagerAdapter(Context context, List<Banner> list){
        mContext = context;
        mImageList = list;
        mImageViewList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewList.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(mImageViewList.size() <= position + 1){
            ImageView imageView = new ImageView(mContext);
            mImageViewList.add(position, imageView);
        }
        ImageLoader.getInstance().displayImage(mImageList.get(position).imageUrl,
                mImageViewList.get(position));
        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }
}
