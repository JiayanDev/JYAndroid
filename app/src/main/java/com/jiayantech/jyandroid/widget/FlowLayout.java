package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Login;
import com.jiayantech.library.utils.UIUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description: 标签、浮动、自适应换行布局
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class FlowLayout extends ViewGroup implements View.OnClickListener {

    HashMap<Integer, Integer> mLineViewMap;
    HashMap<Integer, Integer> mLineHeightMap;
    int mLineCount;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setViews(HashSet<Integer> idSelected, List<Login.Category> list) {
        removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            Login.Category category = list.get(i);
            addView(category, idSelected.contains(category.id));
        }
    }

    public void addView(Login.Category category, boolean selected) {
        TextView button = new TextView(getContext());
        button.setTag(category);
        button.setBackgroundResource(R.drawable.bg_project_child_category_gray_selector);
        button.setSelected(selected);

        int space = UIUtil.dip2px(2);
        button.setPadding(space, space, space, space);
        button.setId(category.id);
        button.setText(category.name);
        button.setOnClickListener(this);
        MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(space, space, space, space);
        addView(button, lp);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        //累加
        int lineWidth = 0;
        int lineHeight = 0;


        int lineNum = 0;
        int cCount = getChildCount();
        mLineViewMap = new HashMap<Integer, Integer>();
        mLineHeightMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);

            if (view.getVisibility() == View.GONE) {
                continue;
            }

            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int viewHeight = lp.topMargin + lp.bottomMargin + view.getMeasuredHeight();  //measureChild过该child后才能child.getMeasuredHeight
            int viewWidth = lp.leftMargin + lp.rightMargin + view.getMeasuredWidth();

            if (lineWidth + viewWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {    //要换行了
                mLineHeightMap.put(lineNum, lineHeight);

                height += lineHeight;                   //行高累加
                width = Math.max(width, lineWidth);   //最大的行寛作为viewgroup的寛
                lineNum++;
                lineWidth = 0;
                lineHeight = 0;
            }
            lineWidth += viewWidth;
            lineHeight = Math.max(lineHeight, viewHeight);  //将最高的childHeight作为viewgroup的height


            if (i == cCount - 1) {   //最后一个，此时也要计算一遍
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
            mLineViewMap.put(i, lineNum);   //都是从0开始给算
        }


        //EXACTLY :  match_parent  或者具体值
        //AT_MOST :  wrap_content(需要自己计算寛高)

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width + getPaddingLeft() + getPaddingRight(), (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height + getPaddingTop() + getPaddingBottom());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int nowLineNum = 0;

        int nowLineWidth = getPaddingLeft();
        int nowLineHeight = getPaddingTop();

        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int viewWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int viewHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int lineNum = mLineViewMap.get(i);   //取出该view所在行

            if (nowLineNum != lineNum) {      //换行
                nowLineWidth = getPaddingLeft();  //初始化 累加寛值
                nowLineHeight += mLineHeightMap.get(lineNum - 1);
                nowLineNum++;
            }

            left = nowLineWidth + lp.leftMargin;
            right = left + childWidth;
            top = nowLineHeight + lp.topMargin;
            bottom = top + childHeight;

            nowLineWidth += viewWidth;

            child.layout(left, top, right, bottom);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, v.getId());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }
}

