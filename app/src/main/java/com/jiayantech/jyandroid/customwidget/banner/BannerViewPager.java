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

    private float mDX;
    private float mDY;
    private float mLX;
    private float mLY;
    private boolean mIntercept;
    private int mLastAct;

    public BannerViewPager(Context paramContext) {
        this(paramContext, null);

    }

    public BannerViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mContext = paramContext;
        setCycle(true);

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
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
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }

////        switch (ev.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                mDownX = ev.getX();
////                mDownY = ev.getY();
////                break;
////            case MotionEvent.ACTION_MOVE:
////                LogUtil.i("viewpager", "Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY))"
////                        + Math.abs(ev.getX() - mDownX) + " " + Math.abs(ev.getY() - mDownY));
////                if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
////                    //getParent().requestDisallowInterceptTouchEvent(true);
////                    //LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(true);");
////                    return false;
////                } else {
////                    //getParent().requestDisallowInterceptTouchEvent(false);
////                    return true;
////                    //LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(false);");
////                }
//////                // break;
//////                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(true);");
//////                    return true;
//////                } else {
//////                    LogUtil.i("viewpager", "requestDisallowInterceptTouchEvent(false);");
//////                    return false;
//////                }
////                //break;
////            case MotionEvent.ACTION_UP:
////            case MotionEvent.ACTION_CANCEL:
////                //getParent().requestDisallowInterceptTouchEvent(false);
////                break;
////        }
////        //return super.dispatchTouchEvent(ev);
//        return super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                mDX = mDY = 0f;
                mLX = ev.getX();
                mLY = ev.getY();

                break;

            case MotionEvent.ACTION_MOVE :
                final float X = ev.getX();
                final float Y = ev.getY();
                mDX += Math.abs(X - mLX);
                mDY += Math.abs(Y - mLY);
                mLX = X;
                mLY = Y;

                if (mIntercept && mLastAct == MotionEvent.ACTION_MOVE) {
                    return false;
                }

                if (mDX > mDY) {

                    mIntercept = true;
                    mLastAct = MotionEvent.ACTION_MOVE;
                    return false;
                }

        }
        mLastAct = ev.getAction();
        mIntercept = false;
        return super.onInterceptTouchEvent(ev);
    }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }
//        return super.onInterceptTouchEvent(ev);

}
