package com.jiayantech.jyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by liangzili on 15/10/16.
 * 不消费onTouchEvent ACTION_DOWN事件，使其父View可以接收该事件
 */
public class CustomImageView extends ImageView{
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//            flag = false;
//        }

        return flag;
    }
}
