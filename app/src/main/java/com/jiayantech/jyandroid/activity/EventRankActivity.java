package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.eventbus.EventRankFinishEvent;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/30.
 */
public class EventRankActivity extends BaseActivity {
    private static final String TAG = "EventRankActivity";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_PROJECT = "project";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_COVER_IMG = "cover_img";

    private ScrollView mScrollView;
    private TextView mTxtTitle;
    private TextView mTxtProject;
    private TextView mTxtDate;
    private RatingBar mRatingAngelSatisfaction;
    private RatingBar mRatingDoctorSatisfaction;
    private TextView mTxtWordCount;
    private ImageView mImageCover;
    private Button mBtnSend;


    private EditText mEditComment;
    private Handler mHandler;

    private long mEventId;
    private String mTitle;
    private String mProject;
    private String mDate;
    private String mCoverImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rank0);

        mHandler = new Handler();
        mEventId = getIntent().getLongExtra(EXTRA_ID, -1);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mProject = getIntent().getStringExtra(EXTRA_PROJECT);
        mDate = TimeUtil.stamp2SimpleDate((long) getIntent().getLongExtra(EXTRA_DATE, 0) * 1000);
        mCoverImg = getIntent().getStringExtra(EXTRA_COVER_IMG);
        initView();

        mEditComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    transitLayout();
                }
            }
        });
        mEditComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitLayout();
            }
        });
        mEditComment.clearFocus();
        //ToastUtil.showMessage("eventId is " + mEventId);
        setTitle(R.string.title_comment_company);
    }

    private void transitLayout(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] x = new int[2];
                mBtnSend.getLocationInWindow(x);
                LogUtil.i(TAG, String.format("scroll coordinate: x is %d, y is %d",
                        x[0], x[1] - getSupportActionBar().getHeight()));
                mScrollView.smoothScrollTo(x[0], x[1]);
            }
        }, 500);
    }

    private void initView(){
        mScrollView = (ScrollView)findViewById(R.id.layout_scroll);
        mImageCover = (ImageView) findViewById(R.id.img_cover);
        mEditComment = (EditText) findViewById(R.id.edit_content);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mTxtProject = (TextView) findViewById(R.id.txt_project);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mRatingAngelSatisfaction = (RatingBar) findViewById(R.id.rating_angel_satisfaction);
        mRatingDoctorSatisfaction = (RatingBar) findViewById(R.id.rating_doctor_satisfaction);
        mTxtWordCount = (TextView) findViewById(R.id.txt_word_count);
        mBtnSend = (Button) findViewById(R.id.btn_publish_comment);

        BitmapBiz.display(mImageCover, mCoverImg);
        mTxtTitle.setText(mTitle);
        mTxtProject.setText(mProject);
        mTxtDate.setText(mDate);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EventBiz.comment();
                showProgressDialog();
                int angelSatisfaction = mRatingAngelSatisfaction.getNumStars();
                int doctorSatisfaction = mRatingDoctorSatisfaction.getNumStars();
                if (mEditComment.getText().toString().length() > 10) {
                    EventBiz.comment(mEventId, mEditComment.getText().toString(), angelSatisfaction, doctorSatisfaction, new ResponseListener<AppResponse>() {
                        @Override
                        public void onResponse(AppResponse response) {
                            finish();
                            SuccessActivity.launchActivity(EventRankActivity.this, "评价伴美",
                                    R.mipmap.icon_rank_success);
                            EventRankFinishEvent event = new EventRankFinishEvent();
                            event.eventId = mEventId;
                            EventBus.getDefault().post(event);

                        }
                    });
                } else {
                    dismissProgressDialog();
                    ToastUtil.showMessage("字数不足");
                }
            }
        });

        mEditComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 10) {
                    mTxtWordCount.setVisibility(View.INVISIBLE);
                } else {
                    mTxtWordCount.setVisibility(View.VISIBLE);
                    mTxtWordCount.setText(getString(R.string.txt_word_count, 10 - count));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
