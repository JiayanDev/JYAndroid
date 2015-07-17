package com.jiayantech.jyandroid.customwidget.banner;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by liangzili on 15/6/29.
 */
public class BannerViewPager extends AutoScrollViewPager {
    private BannerPagerAdapter mAdapter;
    private Context mContext;

    public BannerViewPager(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        this.setCycle(false);
    }

    public BannerViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
    }

    
}
