package com.jiayantech.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jiayantech.library.R;

/**
 * Created by liangzili on 15/8/20.
 * ColorFilterButton 实现点击时用 x% 透明度的任意颜色遮罩的效果
 */
public class ColorFilterImageView extends ImageView {

    /**
     * 默认的覆盖颜色为黑色
     */
    private static int DEFAULT_COLOR = 0x4D000000;

    private int mCoverColor = DEFAULT_COLOR;


    public ColorFilterImageView(Context context) {
        this(context, null);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = null;
        try {
            array = context.obtainStyledAttributes(attrs,
                    R.styleable.ColorFilterImageView);
            mCoverColor = array.getColor(R.styleable.ColorFilterImageView_coverColor,
                    DEFAULT_COLOR);
        } finally {
            if (array != null) {
                array.recycle();
            }
        }

        setCover();

    }

    /**
     * 设置onTouchListener, 设置点击时ImageView的行为，在其上遮盖颜色为{@link #mCoverColor}的遮盖物
     */
    private void setCover() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView image = (ImageView)v;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        image.getDrawable().setColorFilter(mCoverColor, PorterDuff.Mode.SRC_ATOP);
                        image.invalidate();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        image.getDrawable().clearColorFilter();
                        image.invalidate();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * @return
     */
    public int getCoverColor() {
        return mCoverColor;
    }

    /**
     * @param coverColor
     */
    public void setCoverColor(int coverColor) {
        mCoverColor = coverColor;
    }

}
