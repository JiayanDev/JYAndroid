package com.jiayantech.jyandroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.VerifyPhoneActivity;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by liangzili on 15/7/9.
 */
public class ApplyEventFragment extends BaseFragment{
    public static final String EVENT_ID = "eventId";
    public static final String ANGEL_NAME = "angel_name";
    public static final String ANGEL_AVATAR = "angel_avatar";
    public static final String HOSPITAL_AND_DOCTOR = "hospital_and_doctor";
    public static final String PROJECT_NAME = "project_name";
    public static final String EVENT_TIME = "event_time";


    public static final String USERID = "userId";
    public static final String PHONE = "phone";
    public static final String USERNAME = "username";

    private long mEventId;
    private String mAngelName;
    private String mAngelAvatar;
    private String mHospitalAndDoctor;
    private String mProjectName;
    private String mEventTime;

    private TextView mTextUsername;
    private TextView mTextPhone;
    private TextView mTextProject;
    private TextView mTextDoctor;
    private TextView mTextTime;
    private TextView mTextAngelName;
    private RoundedImageView mImageAngelAvatar;

    private Button mButtonApply;

    private LinearLayout mPhoneLayout;

    public static ApplyEventFragment newInstance(long eventId, String angelAvatar, String angelName,
                                                 String project, String hospitalAndDoctor,
                                                 String time){
        ApplyEventFragment fragment = new ApplyEventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
        args.putString(ANGEL_AVATAR, angelAvatar);
        args.putString(ANGEL_NAME, angelName);
        args.putString(PROJECT_NAME, project);
        args.putString(HOSPITAL_AND_DOCTOR, hospitalAndDoctor);
        args.putString(EVENT_TIME, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_apply_event0;
    }

    @Override
    protected void onInitView() {
        Bundle args = getArguments();
        mEventId = args.getLong(EVENT_ID);
        mAngelAvatar = args.getString(ANGEL_AVATAR);
        mAngelName = args.getString(ANGEL_NAME);
        mProjectName = args.getString(PROJECT_NAME);
        mHospitalAndDoctor = args.getString(HOSPITAL_AND_DOCTOR);
        mEventTime = args.getString(EVENT_TIME);

        mImageAngelAvatar = (RoundedImageView)findViewById(R.id.img_avatar);
        mTextAngelName = (TextView)findViewById(R.id.txt_angel_name);
        mTextProject = (TextView)findViewById(R.id.txt_project);
        mTextDoctor = (TextView)findViewById(R.id.txt_doctor);
        mTextTime = (TextView)findViewById(R.id.txt_time);
        mTextUsername = (TextView)findViewById(R.id.txt_username);
        mTextPhone = (TextView)findViewById(R.id.txt_phone);

        BitmapBiz.display(mImageAngelAvatar, mAngelAvatar);
        mTextAngelName.setText(mAngelName);
        mTextProject.setText(mProjectName);
        mTextDoctor.setText(mHospitalAndDoctor);
        mTextTime.setText(mEventTime);
        mTextUsername.setText(AppInitManger.getUserName());
        mTextPhone.setText(AppInitManger.getPhoneNum());

        mPhoneLayout = (LinearLayout)findViewById(R.id.layout_phone);
        mPhoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerifyPhoneActivity.class);
                intent.putExtra(VerifyPhoneActivity.KEY_TYPE, VerifyPhoneActivity.TYPE_UPDATE_PHONE);
                getActivity().startActivityForResult(intent, 0);
            }
        });

        mButtonApply = (Button)findViewById(R.id.btn_apply);
        mButtonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gender = AppInitManger.getUserGender();
                String userName = AppInitManger.getUserName();
                String phoneNum = AppInitManger.getPhoneNum();
                EventBiz.apply(String.valueOf(getArguments().getLong(EVENT_ID)),
                        phoneNum, userName,
                        String.valueOf(gender), new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                ToastUtil.showMessage("报名参加成功 " + appResponse);
                                getActivity().finish();
                            }
                        });
            }
        });
    }

    @Override
    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        return super.onInflateView(inflater, container);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            String phoneNum = data.getStringExtra("phone");
            mTextPhone.setText(phoneNum);
        }
    }
}
