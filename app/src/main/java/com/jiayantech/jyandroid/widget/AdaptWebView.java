package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.jiayantech.library.utils.ToastUtil;

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
        getScrollView(this);
        int measuredHeight = getMeasuredHeight();
        if(measuredHeight > 0) {
            int top = getTop();
            int height = mScrollView.getBottom() - getTop();

            if (measuredHeight < height) {
                measuredHeight = height;
            }
            // 重设高度
            setMeasuredDimension(getMeasuredWidth(), measuredHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        ToastUtil.showMessage("h="+h);
    }

    private ScrollView mScrollView;
    private FrameLayout mFrameLayout;

    private void getFrameLayout(ViewGroup view){
        if(!(view.getChildAt(0) instanceof FrameLayout)){
            getFrameLayout((ViewGroup)view.getChildAt(0));
        }else{

        }
    }

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
