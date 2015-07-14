package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.SearchActivity;
import com.jiayantech.jyandroid.activity.SelectProjectActivity;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.helper.ActivityResultHelper;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class CreateEventProjectFragment extends BaseFragment implements View.OnClickListener {
    private EditText edit_nickname;
    private EditText edit_phone;
    private TextView txt_hospital;
    private TextView txt_doctor;
    private TextView txt_project;
    private TextView txt_time;
    private TextView btn_next;

    private double time;
    private String doctorId;
    private String hospitalId;
    private ArrayList<String> categoryIds;

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_create_event_project;
    }

    @Override
    protected void onInitView() {
        edit_nickname = (EditText) findViewById(R.id.edit_nickname);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        txt_hospital = (TextView) findViewById(R.id.txt_hospital);
        txt_doctor = (TextView) findViewById(R.id.txt_doctor);
        txt_project = (TextView) findViewById(R.id.txt_project);
        txt_time = (TextView) findViewById(R.id.txt_time);
        btn_next = (TextView) findViewById(R.id.btn_next);

        txt_hospital.setOnClickListener(this);
        txt_doctor.setOnClickListener(this);
        txt_project.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_hospital:
                search(getString(R.string.title_hospital_info), CommBiz.ACTION_HOSPITAL_OPTION, new ActivityResult(SearchActivity.REQUEST_CODE_SELECT) {
                    @Override
                    public void onActivityResult(Intent data) {
                        hospitalId = data.getStringExtra(SearchActivity.KEY_ID);
                        String hospitalName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_hospital.setText(hospitalName);
                        ToastUtil.showMessage("hospitalName: " + hospitalName);
                    }
                });
                break;
            case R.id.txt_doctor:
                search(getString(R.string.title_doctor_info), CommBiz.ACTION_DOCTOR_OPTION, new ActivityResult(SearchActivity.REQUEST_CODE_SELECT) {
                    @Override
                    public void onActivityResult(Intent data) {
                        doctorId = data.getStringExtra(SearchActivity.KEY_ID);
                        String doctorName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_doctor.setText(doctorName);
                        ToastUtil.showMessage("doctorName: " + doctorName);
                    }
                });
                break;
            case R.id.txt_project:
                Intent intent = new Intent(getActivity(), SelectProjectActivity.class);
                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
                startActivityForResult(intent, SelectProjectActivity.REQUEST_CODE_SELECT);
                mActivityResultHelper.addActivityResult(new ActivityResult(SelectProjectActivity.REQUEST_CODE_SELECT) {
                    @Override
                    public void onActivityResult(Intent data) {
                        categoryIds = data.getStringArrayListExtra(SelectProjectActivity.KEY_categoryIds);
                        ArrayList<String> categoryNames = data.getStringArrayListExtra(SelectProjectActivity.KEY_categoryNames);
                        txt_project.setText(categoryNames.toString());
                        ToastUtil.showMessage("categoryNames: " + categoryNames);
                    }
                });
                break;
            case R.id.txt_time:
                new DateTimeHelper(getActivity()).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(Calendar calendar) {
                        txt_time.setText(TimeUtil.getStrTime(calendar.getTimeInMillis()));
                        time = calendar.getTimeInMillis() / 1000d;
                    }
                });
                break;
            case R.id.btn_next:
                String nickname = edit_nickname.getText().toString();
                String phone = edit_phone.getText().toString();

                hospitalId = "1";
                doctorId = "1";

                if (hospitalId == null) {
                    ToastUtil.showMessage("hospitalId==null");
                    return;
                }
                if (doctorId == null) {
                    ToastUtil.showMessage("doctorId==null");
                    return;
                }
                if (categoryIds == null) {
                    ToastUtil.showMessage("categoryIds==null");
                    return;
                }
                if (time == 0) {
                    ToastUtil.showMessage("time==0");
                    return;
                }
                onNext(nickname, phone, hospitalId, doctorId, categoryIds.toString(), time);
                break;
        }
    }

    private void search(String title, String action, ActivityResult activityResult) {
        SearchActivity.start(this, title, action);
        mActivityResultHelper.addActivityResult(activityResult);
    }

    //////////////////////
    private ActivityResultHelper mActivityResultHelper = new ActivityResultHelper();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mActivityResultHelper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract void onNext(String nickname, String phone, String hospital, String doctor, String project, double time);
}
