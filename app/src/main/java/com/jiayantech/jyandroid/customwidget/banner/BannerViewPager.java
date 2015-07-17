package com.jiayantech.jyandroid.customwidget.banner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jiayantech.library.utils.LogUtil;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by liangzili on 15/6/29.
 */
public class BannerViewPager extends AutoScrollViewPager {
    private BannerPagerAdapter mAdapter;
    private Context mContext;
    private ViewParent mParent;

    private float mDownX;
    private float mDownY;

    public BannerViewPager(Context paramContext) {
        super(paramContext);
        mContext = paramContext;
        this.setCycle(true);
        mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    }

    public BannerViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        setCycle(true);
        mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    }

    public void setNestedParent(ViewGroup parent) {
        mParent = null;
    }

    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
////        if(mParent != null){
////            mParent.requestDisallowInterceptTouchEvent(true);
////        }
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mDownX = ev.getX();
//                mDownY = ev.getY();
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                LogUtil.i("viewpager", "Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY))"
//                        + Math.abs(ev.getX() - mDownX) + " " +  Math.abs(ev.getY() - mDownY));
//                if(Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(true);");
//                }
//                else{
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(false);");
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                getParent().requestDisallowInterceptTouchEvent(false);
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
    private int mTouchSlop;
    private float mPrevX;

    private boolean isHorizontalScroll;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (mParent == null) {
//            mParent = findParent(this);
//        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(event).getX();
                isHorizontalScroll = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isHorizontalScroll) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    LogUtil.i("dispatchTouchEvent", "isHorizontalScroll");
//                    super.dispatchTouchEvent(event);
//                    return true;
                    //return false;
                    break;
                }
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                //   Logs.d("move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                if (xDiff > mTouchSlop) {
                    isHorizontalScroll = true;
                    LogUtil.i("dispatchTouchEvent", "isHorizontalScroll");
                    getParent().requestDisallowInterceptTouchEvent(true);
//                    super.dispatchTouchEvent(event);
//                    return true;
                    break;
                }
        }
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (mParent == null) {
//            mParent = findParent(this);
//        }
//        if (isHorizontalScroll) {
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isHorizontalScroll = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public boolean onInterceptTouchEvent0(MotionEvent ev) {
//        if (mParent == null) {
//            mParent = findParent(this);
//        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("viewpager", "Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY))"
                        + Math.abs(ev.getX() - mDownX) + " " + Math.abs(ev.getY() - mDownY));
                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(true);");
                    mParent.requestDisallowInterceptTouchEvent(true);
                    return true;
                } else {
                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(false);");
                    mParent.requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                //break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    private ViewParent findParent(ViewParent v) {
        if (v instanceof RecyclerView) {
            return v.getParent();
        }
        return findParent(v.getParent());
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
//        return super.onInterceptTouchEvent(ev);

}
