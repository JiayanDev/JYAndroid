/*
 * Copyright (C) 2015 James Pekarek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.jiayantech.jyandroid.R;

public class RatingBar extends android.widget.RatingBar {

    private float dp = getResources().getDisplayMetrics().density;

    private Bitmap on = ((BitmapDrawable) getResources().getDrawable(R.mipmap.icon_rating)).getBitmap();
    private Bitmap off = ((BitmapDrawable) getResources().getDrawable(R.mipmap.icon_rating_blank)).getBitmap();

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingBar(Context context) {
        super(context);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (50 * dp * getNumStars());
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(heightSize, width / getNumStars());
        } else {
            //Be whatever you want
            height = width / getNumStars();
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        float colWidth = getWidth() / (float) getNumStars();
        float starSize = Math.min(getHeight(), colWidth);
        int leftOffset = (int) ((colWidth - starSize) / 2);
        float rating = getRating();
        Rect src = new Rect(0, 0, on.getWidth(), on.getHeight());
        for (int i = 0; i < getNumStars(); ++i) {
            if (i + 1 <= rating) {
                float left = leftOffset + colWidth * i;
                RectF dst = new RectF(left, 0, left + starSize, starSize);
                canvas.drawBitmap(on, src, dst, null);
            } else if (i >= rating) {
                float left = leftOffset + colWidth * i;
                RectF dst = new RectF(left, 0, left + starSize, starSize);
                canvas.drawBitmap(off, src, dst, null);
            } else {
                float offset0 = rating - i;
                int src0Right = (int) (on.getWidth() * offset0);
                int dst0Right = (int) (starSize * offset0);
                Rect src0 = new Rect(0, 0, src0Right, on.getHeight());
                float left = leftOffset + colWidth * i;
                RectF dst0 = new RectF(left, 0, left + dst0Right, starSize);
                canvas.drawBitmap(on, src0, dst0, null);

                float offset1 = i + 1 - rating;
                int src1Left = (int) (on.getWidth() * offset1);
                int dst1Left = (int) (starSize * offset1);
                Rect src1 = new Rect(src1Left, 0, on.getWidth(), on.getHeight());
                left = left + dst0Right;
                RectF dst1 = new RectF(left, 0, leftOffset + colWidth * i + starSize, starSize);
                canvas.drawBitmap(off, src1, dst1, null);
            }
        }
    }
}
