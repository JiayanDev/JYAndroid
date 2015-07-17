package com.jiayantech.jyandroid.customwidget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.jiayantech.library.utils.LogUtil;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by liangzili on 15/6/29.
 */
public class BannerViewPager extends AutoScrollViewPager {
    private BannerPagerAdapter mAdapter;
    private Context mContext;
    private ViewGroup mParent;

    private float mDownX;
    private float mDownY;

    public BannerViewPager(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        this.setCycle(true);
    }

    public BannerViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        setCycle(true);
    }

    public void setNestedParent(ViewGroup parent){
        mParent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("viewpager", "Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY))"
                        + Math.abs(ev.getX() - mDownX) + " " +  Math.abs(ev.getY() - mDownY));
                if(Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(true);");
                }
                else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(false);");
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        if(mParent != null){
////            mParent.requestDisallowInterceptTouchEvent(true);
////        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
//        return super.onInterceptTouchEvent(ev);

}
