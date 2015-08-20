package com.jiayantech.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by liangzili on 15/8/17.
 */
public class UnreadRadioButton extends RadioButton{
    private static int DEFAULT_UNREAD_COUNT = 0;

    private int mUnreadCount = DEFAULT_UNREAD_COUNT;

    private Paint mUnreadPaint = new Paint();
    private RectF mUnreadRect = new RectF();

    public int getUnreadCount() {
        return mUnreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        mUnreadCount = unreadCount;
        invalidate();
    }

    public UnreadRadioButton(Context context) {
        super(context);
    }

    public UnreadRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnreadRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mUnreadCount != 0){
            String hello = "Hello world";
            int height = getHeight();
            int width = getWidth();
            mUnreadPaint.setAntiAlias(true);

            //mUnreadRect = new RectF((float)2.0 * width / 3, 0, width, (float)1.0 * width / 3);
            mUnreadRect.set((float)2.0 * width / 3, 0, width, (float)1.0 * width / 3);
            mUnreadPaint.setColor(Color.RED);
            canvas.drawOval(mUnreadRect, mUnreadPaint);

            int strX = (int)(getWidth() - hello.getBytes().length * 5.5)/2;

            canvas.drawText("Hello", strX, getHeight() + 20, mUnreadPaint);

            canvas.drawLine(0, 0, getWidth(), getHeight(), mUnreadPaint);
        }
    }
}
