package com.jiayantech.jyandroid.widget.drawable;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

/**
 * Created by janseon on 2015/7/16.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class BottomLineDrawable extends ColorDrawable {
    private final Paint mPaint = new Paint();

    public BottomLineDrawable(int color) {
        super(color);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setColor(getColor());
        Rect rect = getBounds();
        rect.top = rect.bottom - 1;
        canvas.drawRect(rect, mPaint);
    }
}
