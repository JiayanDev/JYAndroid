package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.SearchActivity;
import com.jiayantech.jyandroid.activity.SelectProjectActivity;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.comm.ActivityResult;
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
    private TextView txt_nickname;
    private TextView txt_phone;
    private TextView txt_hospital;
    private TextView txt_doctor;
    private TextView txt_project;
    private TextView txt_time;
    private TextView btn_ok;

    private long time;
    private long doctorId;
    private String doctorName;
    private long hospitalId;
    private String hospitalName;
    //private ArrayList<String> categoryIds;
    ArrayList<AppInit.Category> categoryList;

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_create_event_project;
    }

    @Override
    protected void onInitView() {
        txt_nickname = (TextView) findViewById(R.id.txt_nickname);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_hospital = (TextView) findViewById(R.id.txt_hospital);
        txt_doctor = (TextView) findViewById(R.id.txt_doctor);
        txt_project = (TextView) findViewById(R.id.txt_project);
        txt_time = (TextView) findViewById(R.id.txt_time);
        btn_ok = (TextView) findViewById(R.id.btn_ok);

        txt_hospital.setOnClickListener(this);
        txt_doctor.setOnClickListener(this);
        txt_project.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

        txt_nickname.setText(getString(R.string.nickname) + AppInitManger.getUserName());
        txt_phone.setText(getString(R.string.phone) + AppInitManger.getPhoneNum());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_hospital:
                search(getString(R.string.title_hospital_info), CommBiz.ACTION_HOSPITAL_OPTION, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        hospitalId = data.getLongExtra(SearchActivity.KEY_ID, 0);
                        hospitalName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_hospital.setText(getString(R.string.hospital) + ": " + hospitalName);
                    }
                });
                break;
            case R.id.txt_doctor:
                search(getString(R.string.title_doctor_info), CommBiz.ACTION_DOCTOR_OPTION, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        doctorId = data.getLongExtra(SearchActivity.KEY_ID, 0);
                        doctorName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_doctor.setText(getString(R.string.doctor) + ": " + doctorName);
                        if (doctorId != 0) {
                            hospitalId = data.getLongExtra(SearchActivity.KEY_HOSPITAL_ID, 0);
                            hospitalName = data.getStringExtra(SearchActivity.KEY_HOSPITAL_NAME);
                            txt_hospital.setText(getString(R.string.hospital) + ": " + hospitalName);
                        }
                    }
                });
                break;
            case R.id.txt_project:
                Intent intent = new Intent(getActivity(), SelectProjectActivity.class);
                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
                startActivityForResult(intent, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        categoryList = data.getParcelableArrayListExtra(SelectProjectActivity.KEY_categories);
                        txt_project.setText(getString(R.string.project) + ": " + AppInit.Category.toNamesString(categoryList));
//                        categoryIds = data.getStringArrayListExtra(SelectProjectActivity.KEY_categories);
//                        ArrayList<String> categoryNames = data.getStringArrayListExtra(SelectProjectActivity.KEY_categoryNames);
//                        txt_project.setText(categoryNames.toString());
//                        ToastUtil.showMessage("categoryNames: " + categoryNames);
                    }
                });
                break;
            case R.id.txt_time:
                new DateTimeHelper(getActivity()).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(Calendar calendar) {
                        txt_time.setText(getString(R.string.operation_time) + ": " + TimeUtil.getStrTime(calendar.getTimeInMillis() / 1000 / 60 * 1000 * 60));
                        time = calendar.getTimeInMillis() / 1000;
                    }
                });
                break;
            case R.id.btn_ok:
                String nickname = AppInitManger.getUserName();
                String phone = AppInitManger.getPhoneNum();

                //hospitalId = "1";
                //doctorId = "1";

                if (hospitalId == 0 && TextUtils.isEmpty(hospitalName)) {
                    ToastUtil.showMessage("hospitalId==null");
                    return;
                }
                if (doctorId == 0 && TextUtils.isEmpty(doctorName)) {
                    ToastUtil.showMessage("doctorId==null");
                    return;
                }
                if (categoryList == null || categoryList.size() == 0) {
                    ToastUtil.showMessage("categoryIds==null");
                    return;
                }
                if (time == 0) {
                    ToastUtil.showMessage("time==0");
                    return;
                }
                onNext(nickname, phone, hospitalId, hospitalName, doctorId, doctorName, categoryList.toString(), time);
                break;
        }
    }

    private void search(String title, String action, ActivityResult activityResult) {
        SearchActivity.start(this, title, action, activityResult);
    }

    protected abstract void onNext(String nickname, String phone, long hospitalId, String hospitalName, long doctorId, String doctorName, String project, long time);
}
