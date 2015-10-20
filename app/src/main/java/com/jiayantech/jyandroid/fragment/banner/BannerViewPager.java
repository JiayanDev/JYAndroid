package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean flag = super.dispatchTouchEvent(ev);
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            onTouchEvent(ev);
        }
        return flag;
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        boolean flag = super.onInterceptTouchEvent(ev);
////        return true;
//        return super.onInterceptTouchEvent(ev);
//    }
}
