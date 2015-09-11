package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiayantech.library.http.BitmapBiz;

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
        for(int i = 0; i < mImageList.size(); i++){
            final int count = i;
            final ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageList.get(count).listener.onClick(mContext);
                }
            });
            mImageViewList.add(imageView);
        }
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
//        ImageLoader.getInstance().displayImage(mImageList.get(position).imageUrl,
//                mImageViewList.get(position));
        BitmapBiz.display(mImageViewList.get(position), mImageList.get(position).imageUrl);
        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }
}
