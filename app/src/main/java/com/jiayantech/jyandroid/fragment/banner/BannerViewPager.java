package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;
import android.util.AttributeSet;


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
        setStopScrollWhenTouch(true);
    }
}
