package com.jiayantech.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jiayantech.library.R;

import java.lang.reflect.Type;

/**
 * Created by liangzili on 15/7/17.
 */
public class UnslidableViewPager extends ViewPager{
    private boolean slidable = false;

    public UnslidableViewPager(Context context) {
        super(context);
        slidable = false;
    }

    public UnslidableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnslidableViewPager);
        slidable = array.getBoolean(R.styleable.UnslidableViewPager_slidable, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(slidable) {
            return super.onTouchEvent(ev);
        }else{
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(slidable) {
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
}
