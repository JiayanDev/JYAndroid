package com.jiayantech.jyandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by liangzili on 15/11/26.
 */
public class CustomPhotoView extends PhotoView {
    //默认单击间隔为1500ms，即单击一次1500ms后无后续动作，则返回
    private static final int DEFAULT_INTERVAL = 500;
    private int mInterval = DEFAULT_INTERVAL;

    private static final int MSG_FINISH_ACTIVITY = 1;

    private boolean isDuraingSingleClick = false;
    private FinishActivityHandler mHandler;

    static class FinishActivityHandler extends Handler {
        private WeakReference<Context> mContextWeakReference;

        public FinishActivityHandler(Context context) {
            mContextWeakReference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_FINISH_ACTIVITY) {
                Context context = mContextWeakReference.get();
                if (context != null && (context instanceof Activity)) {
                    ((Activity) context).finish();
                }
            }
            super.handleMessage(msg);
        }
    }

    public CustomPhotoView(Context context) {
        this(context, null);
    }

    public CustomPhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public CustomPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        mHandler = new FinishActivityHandler(context);
    }



    public int getInterval() {
        return mInterval;
    }

    public void setInterval(int interval) {
        mInterval = interval;
    }

    private void startCountDown() {
        isDuraingSingleClick = true;
        mHandler.sendMessageDelayed(Message.obtain(mHandler, MSG_FINISH_ACTIVITY), mInterval);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean flag = super.dispatchTouchEvent(event);
        checkEvent(event);
        return flag;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        checkEvent(event);
        return super.onTouchEvent(event);
    }

    private void checkEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isDuraingSingleClick = !isDuraingSingleClick;
            if (isDuraingSingleClick) {
                startCountDown();
            }else{
                mHandler.removeMessages(MSG_FINISH_ACTIVITY);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (isDuraingSingleClick) {
                mHandler.removeMessages(MSG_FINISH_ACTIVITY);
                isDuraingSingleClick = false;
            }
        }
    }
}
