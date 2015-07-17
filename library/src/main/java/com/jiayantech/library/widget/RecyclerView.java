package com.jiayantech.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jiayantech.library.utils.LogUtil;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;

/**
 * Created by janseon on 2015/7/17.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class RecyclerView extends CustomUltimateRecyclerview {

    public RecyclerView(Context context) {
        super(context);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownX;
    private float mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if(mParent != null){
//            mParent.requestDisallowInterceptTouchEvent(true);
//        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                LogUtil.i("RecyclerView", "mDownX, mDownY = " + mDownX + ", " + mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.i("RecyclerView", "Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY))"
                        + Math.abs(ev.getX() - mDownX) + " " + Math.abs(ev.getY() - mDownY));
                if (Math.abs(ev.getX() - mDownX) < Math.abs(ev.getY() - mDownY)) {
                    LogUtil.i("RecyclerView", "Math.abs(ev.getX() - mDownX) < Math.abs(ev.getY() - mDownY");
                    //return true;
                    break;
                } else {
                    LogUtil.i("RecyclerView", "Math.abs(ev.getX() - mDownX) >= Math.abs(ev.getY() - mDownY");
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
}
