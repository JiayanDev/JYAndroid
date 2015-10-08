package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义ScrollView，滚动时通过监听器发出通知
 * Created by liangzili on 15/9/14.
 */
public class NotifyingScrollView extends ScrollView{
    public interface OnScrollChangeListener{
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener mOnScrollChangeListener) {
        this.mOnScrollChangeListener = mOnScrollChangeListener;
    }

    private OnScrollChangeListener mOnScrollChangeListener;

    public NotifyingScrollView(Context context) {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(mOnScrollChangeListener != null && Math.abs(t - oldt) < 20){
            super.onScrollChanged(l, t, oldl, oldt);
            mOnScrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }


    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
//        return super.computeScrollDeltaToGetChildRectOnScreen(rect);
        return 0;
    }
}
