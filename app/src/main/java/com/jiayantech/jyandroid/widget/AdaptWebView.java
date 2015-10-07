package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;

/**
 * Created by 健兴 on 2015/10/7.
 */
public class AdaptWebView extends WebView {

    public AdaptWebView(Context context) {
        super(context);
    }

    public AdaptWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        if (measuredHeight > 0) {
            getScrollView(this);
            int top = getTop();
            int scrollBottom = mScrollView.getBottom();
            int height = scrollBottom - top;

            if (measuredHeight < height) {
                measuredHeight = height;
            }
            // 重设高度
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    private ScrollView mScrollView;

    private void getScrollView(View view) {
        if (mScrollView == null) {
            if (view.getParent() instanceof ScrollView) {
                mScrollView = (ScrollView) view.getParent();
            } else {
                getScrollView((View) view.getParent());
            }
        }
    }
}
