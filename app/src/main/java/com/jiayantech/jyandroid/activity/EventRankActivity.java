package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/7/30.
 */
public class EventRankActivity extends BaseActivity {
    private static final String TAG = "EventRankActivity";
    public static final String EXTRA_ID = "id";

    private EditText mComment;
    private ScrollView mScrollView;
    private Handler mHandler;
    private LinearLayout mRank;

    private long mEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rank);
        mComment = (EditText) findViewById(R.id.edit_content);
        mScrollView = (ScrollView) findViewById(R.id.scroll);
        mRank = (LinearLayout) findViewById(R.id.rank);
        mHandler = new Handler();

        mEventId = getIntent().getLongExtra(EXTRA_ID, -1);
        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mScrollView.fullScroll(View.FOCUS_DOWN);
                        int[] x = new int[2];
                        mComment.getLocationInWindow(x);
                        LogUtil.i(TAG, String.format("scroll coordinate: x is %d, y is %d",
                                x[0], x[1] - getSupportActionBar().getHeight()));
                        mScrollView.smoothScrollTo(x[0], x[1]);
                    }
                }, 1000);
            }
        });
        ToastUtil.showMessage("eventId is " + mEventId);

//        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                int x = mScrollView.getScrollX();
//                int y = mScrollView.getScrollY();
//                LogUtil.i(TAG, String.format("scroll change: x is %d, y is %d", x, y));
//            }
//        });
    }
}
