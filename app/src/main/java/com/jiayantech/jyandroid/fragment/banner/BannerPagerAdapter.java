package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.HttpConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/6/29.
 */
class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<Banner> mImageList;
    private List<ImageView> mImageViewList;

    public BannerPagerAdapter(Context context, List<Banner> list) {
        mContext = context;
        mImageList = list;
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageList.size(); i++) {
            final int count = i;
            final ImageView imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageList.get(count).listener.onClick(mContext);
                }
            });
            mImageViewList.add(imageView);
        }
        containerWidth = context.getResources().getDisplayMetrics().widthPixels;
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

    private final int containerWidth;
    private int containerHeight = 0;

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
//        ImageLoader.getInstance().displayImage(mImageList.get(position).imageUrl,
//                mImageViewList.get(position));
        //BitmapBiz.display(mImageViewList.get(position), mImageList.get(position).imageUrl);
        BitmapBiz.display(mImageViewList.get(position), mImageList.get(position).imageUrl, new BitmapBiz.SimpleImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                    int width = response.getBitmap().getWidth();
                    int height = response.getBitmap().getHeight();
                    int imageHeight = containerWidth * height / width;
                    if (imageHeight > containerHeight) {
                        ViewGroup.LayoutParams lp = container.getLayoutParams();
                        lp.height = imageHeight;
                        container.setLayoutParams(lp);
                    }
                } else if (HttpConfig.DEFAULT_IMAGE_ID != 0) {
                    view.setImageResource(HttpConfig.DEFAULT_IMAGE_ID);
                }
            }
        });
        container.addView(mImageViewList.get(position));
        return mImageViewList.get(position);
    }
}
