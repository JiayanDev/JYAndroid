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
import com.jiayantech.library.base.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by liangzili on 15/7/9.
 */
public class ApplyEventFragment extends BaseFragment{
    public static final String EVENT_ID = "eventId";
    public static final String PHONE = "phone";
    public static final String ANGEL_NAME = "angel_name";
    public static final String GENDER = "gender";
    public static final String DESC = "desc";
    public static final String USERID = "userId";
    public static final String USERNAME = "username";

    private TextView mTextUsername;
    private TextView mTextPhone;
    private TextView mTextProject;
    private TextView mTextDoctor;
    private TextView mTextTime;
    private TextView mTextAngelName;
    private RoundedImageView mTextAngelAvatar;

    private Button mButtonApply;

    private LinearLayout mPhoneLayout;

    public static ApplyEventFragment newInstance(long id){
        ApplyEventFragment fragment = new ApplyEventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_apply_event0;
    }

    @Override
    protected void onInitView() {
//        mEditName = (EditText)findViewById(R.id.edit_nickname);
//        mEditPhone = (EditText)findViewById(R.id.edit_phone);
//        mEditDesc = (EditText)findViewById(R.id.edit_desc);
//        mGender = (RadioGroup)findViewById(R.id.radiogroup_gender);
//        mMaleRadioBtn = (RadioButton)findViewById(R.id.radio_male);
//        mFemaleRadioBtn = (RadioButton)findViewById(R.id.radio_female);
//
//        mEditName.setText(AppInitManger.getUserName());
//        mEditPhone.setText(AppInitManger.getPhoneNum());
//        if(AppInitManger.getUserGender() == 1){
//            mMaleRadioBtn.setChecked(true);
//        }else{
//            mFemaleRadioBtn.setChecked(true);
//        }

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
//        mButtonApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int gender;
//                if(mGender.getCheckedRadioButtonId() == R.id.radio_male) {
//                    gender = 0;
//                }else{
//                    gender = 1;
//                }
//                EventBiz.apply(String.valueOf(getArguments().getLong(EVENT_ID)),
//                        mEditPhone.getText().toString(), mEditName.getText().toString(),
//                        String.valueOf(gender), mEditDesc.getText().toString(), new ResponseListener<AppResponse>() {
//                            @Override
//                            public void onResponse(AppResponse appResponse) {
//                                ToastUtil.showMessage("报名参加成功 " + appResponse);
//                                getActivity().finish();
//                            }
//                        });
//            }
//        });
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
