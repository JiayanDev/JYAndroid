package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by liangzili on 15/9/9.
 * 根据Item行数调整高度的GridView，可以显示GridView中所有item
 */
public class AdaptiveGridView extends GridView{
    public AdaptiveGridView(Context context) {
        super(context);
    }

    public AdaptiveGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return super.onTouchEvent(ev);

        return false;
    }
}
