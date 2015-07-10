package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/7/9.
 */
public class ApplyEventFragment extends BaseFragment{
    public static final String EVENT_ID = "eventId";
    public static final String PHONE = "phone";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String DESC = "desc";
    public static final String USERID = "_userId";

    private EditText mEditName;
    private EditText mEditPhone;
    private EditText mEditDesc;
    private RadioGroup mGender;

    private Button mButtonApply;

    public static ApplyEventFragment newInstance(long id){
        ApplyEventFragment fragment = new ApplyEventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_apply_event;
    }

    @Override
    protected void onInitView() {
        mEditName = (EditText)findViewById(R.id.edit_nickname);
        mEditPhone = (EditText)findViewById(R.id.edit_phone);
        mEditDesc = (EditText)findViewById(R.id.edit_desc);
        mGender = (RadioGroup)findViewById(R.id.radiogroup_gender);

        mButtonApply = (Button)findViewById(R.id.btn_apply);
        mButtonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gender;
                if(mGender.getCheckedRadioButtonId() == R.id.radio_male) {
                    gender = 0;
                }else{
                    gender = 1;
                }
                EventBiz.apply(String.valueOf(getArguments().getLong(EVENT_ID)),
                        mEditPhone.getText().toString(), mEditName.getText().toString(),
                        String.valueOf(gender), mEditDesc.getText().toString(), new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                ToastUtil.showMessage("报名参加成功 " + appResponse);
                            }
                        });
            }
        });
    }

    @Override
    protected View onInflateView(LayoutInflater inflater, ViewGroup container) {
        return super.onInflateView(inflater, container);
    }
}
