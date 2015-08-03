package com.jiayantech.jyandroid.customwidget.banner;

import android.content.Context;
import android.util.AttributeSet;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by liangzili on 15/6/29.
 */
public class BannerViewPager extends AutoScrollViewPager {

    public BannerViewPager(Context paramContext) {
        this(paramContext, null);
    }

    public BannerViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setCycle(true);
    }
}
