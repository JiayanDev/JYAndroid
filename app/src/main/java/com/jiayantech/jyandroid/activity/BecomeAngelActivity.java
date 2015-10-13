package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.eventbus.ApplyAngelFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class BecomeAngelActivity extends BaseActivity {
    private TextView txt_nickname;
    private TextView txt_phone;
    private TextView txt_hospital;
    private TextView txt_project;
    private TextView txt_time;

    private long time;
    //private long doctorId;
    //private String doctorName;
    private long hospitalId;
    private String hospitalName;
    //private ArrayList<String> categoryIds;
    private final ArrayList<AppInit.Category> categoryList = new ArrayList<AppInit.Category>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_angel);
        findViews();
        setViewsContent();
    }

    protected void findViews() {
        txt_nickname = (TextView) findViewById(R.id.txt_nickname);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        txt_hospital = (TextView) findViewById(R.id.txt_hospital);
        txt_project = (TextView) findViewById(R.id.txt_project);
        txt_time = (TextView) findViewById(R.id.txt_time);
    }

    protected void setViewsContent() {
        setTitle(R.string.become_angel);
        txt_nickname.setText(AppInitManger.getUserName());
        txt_phone.setText(AppInitManger.getPhoneNum());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_phone:
                EditActivity.start(this, R.string.phone, txt_phone.getText().toString(), new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        txt_phone.setText(getDataString(data));
                    }
                });
                break;
            case R.id.layout_hospital:
                search(getString(R.string.title_hospital_info), CommBiz.ACTION_HOSPITAL_OPTION, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        hospitalId = data.getLongExtra(SearchActivity.KEY_ID, 0);
                        hospitalName = data.getStringExtra(SearchActivity.KEY_NAME);
                        txt_hospital.setText(hospitalName);
                    }
                });
                break;
            case R.id.layout_project:
//                Intent intent = new Intent(this, SelectProjectActivity.class);
//                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
//                startActivityForResult(intent, new ActivityResult() {
//                    @Override
//                    public void onActivityResult(Intent data) {
//                        categoryList = data.getParcelableArrayListExtra(SelectProjectActivity.KEY_categories);
//                        txt_project.setText(AppInit.Category.toNamesString(categoryList));
//                    }
//                });
                Intent intent = new Intent(this, SearchCategoryActivity.class);
                intent.putExtra(SearchCategoryActivity.KEY_TITLE,"");
                startActivityForResult(intent, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        AppInit.Category category = new AppInit.Category();
                        category.id = (int) data.getLongExtra(SearchActivity.KEY_ID, 0);
                        category.name = data.getStringExtra(SearchActivity.KEY_NAME);
                        categoryList.clear();
                        categoryList.add(category);
                        txt_project.setText(AppInit.Category.toNamesString(categoryList));
                    }
                });
                break;
            case R.id.layout_time:
                new DateTimeHelper(this).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(Calendar calendar) {
                        time = calendar.getTimeInMillis() / 1000 / 60 * 60;
                        txt_time.setText(TimeUtil.getStrTimeBySecond(time));
                    }
                });
                break;
            case R.id.btn_ok:
                String nickname = AppInitManger.getUserName();
                String phone = AppInitManger.getPhoneNum();

//                if (hospitalId == 0 && TextUtils.isEmpty(hospitalName)) {
//                    ToastUtil.showMessage(R.string.hint_input_hospital);
//                    return;
//                }
                if (categoryList == null || categoryList.size() == 0) {
                    ToastUtil.showMessage(R.string.hint_input_project);
                    return;
                }
//                if (time == 0) {
//                    ToastUtil.showMessage(R.string.hint_input_time);
//                    return;
//                }

                String title = null;
                String desc = null;
                long applyBeginTime = 0;
                long applyEndTime = 0;

                long beginTime = time;

                long endTime = 0;

                String categoryIds = categoryList.toString();
                long doctorId = 1;
                String doctorName = null;

                String photos = null;
                String province = null;
                String city = null;
                String district = null;
                String addr = null;

                EventBiz.create(nickname, phone, title, desc, applyBeginTime, applyEndTime, beginTime, endTime, categoryIds, hospitalId, hospitalName,
                        doctorId, doctorName, photos, province, city, district, addr, new SimpleResponseListener<BaseAppResponse>(_this) {
                            @Override
                            public void onResponse(BaseAppResponse appResponse) {
                                super.onResponse(appResponse);
                                ToastUtil.showMessage(R.string.msg_sign_up_success);
                                ActivityResult.onFinishResult(_this);

                                ApplyAngelFinishEvent event = new ApplyAngelFinishEvent();
                                event.category = AppInit.Category.toNamesString(categoryList);

                                EventBus.getDefault().post(event);
                            }
                        });
                break;
        }
    }

    private void search(String title, String action, ActivityResult activityResult) {
        SearchActivity.start(this, title, action, activityResult);
    }
}
