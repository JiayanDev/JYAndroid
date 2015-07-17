package com.jiayantech.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by janseon on 2015/7/17.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class VerticalSwipeRefreshLayout extends PtrFrameLayout {

    private int mTouchSlop;
    private float mPrevX;
    private float mPrevY;

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private static final int STATE_NONE = 0;
    private static final int STATE_VER = 1;
    private static final int STATE_HOR = 2;
    private int mState = STATE_NONE;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MotionEvent e = MotionEvent.obtain(event);
                mPrevX = e.getX();
                mPrevY = e.getY();
                mState = STATE_NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mState == STATE_VER) {
                    break;
                }
                if (mState == STATE_HOR) {
                    return dispatchTouchEventSupper(event);
                }
                float xDiff = Math.abs(event.getX() - mPrevX);
                float yDiff = Math.abs(event.getY() - mPrevY);
                if (xDiff > yDiff) {
                    if (xDiff > mTouchSlop) {
                        mState = STATE_HOR;
                        return dispatchTouchEventSupper(event);
                    }
                } else if (yDiff > mTouchSlop) {
                    mState = STATE_VER;
                    break;
                }
        }
        return super.dispatchTouchEvent(event);
    }
}