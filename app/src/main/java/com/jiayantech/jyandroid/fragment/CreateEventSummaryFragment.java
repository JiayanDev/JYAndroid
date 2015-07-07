package com.jiayantech.jyandroid.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public abstract class CreateEventSummaryFragment extends BaseFragment implements View.OnClickListener {
    private EditText edit_content;
    private Button btn_next;
    private String nickname, phone, hospital, doctor, project;
    private long time;


    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_create_event_summary;
    }

    @Override
    protected void onInitView() {
        edit_content = (EditText) findViewById(R.id.edit_content);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                String title = null;
                String desc = null;
                long applyBeginTime = 0;
                long applyEndTime = 0;

                long beginTime = time;

                long endTime = 0;

                String categoryIds = project;
                String hospitalId = hospital;
                String doctorId = doctor;

                String photos = null;
                String province = null;
                String city = null;
                String district = null;
                String addr = null;

                EventBiz.create(title, desc, applyBeginTime, applyEndTime, beginTime, endTime, categoryIds, hospitalId,
                        doctorId, photos, province, city, district, addr, new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                onSuccess();
                            }
                        });
                break;
        }
    }

    public abstract void onSuccess();

    public CreateEventSummaryFragment setInfo(String nickname, String phone, String hospital,
                                              String doctor, String project, long time) {
        this.nickname = nickname;
        this.phone = phone;
        this.hospital = hospital;
        this.doctor = doctor;
        this.project = project;
        this.time = time;
        return this;
    }
}
