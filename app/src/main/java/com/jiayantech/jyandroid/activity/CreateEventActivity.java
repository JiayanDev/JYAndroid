package com.jiayantech.jyandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.fragment.CreateEventProjectFragment;
import com.jiayantech.jyandroid.fragment.CreateEventSuccessFragment;
import com.jiayantech.jyandroid.fragment.CreateEventSummaryFragment;
import com.jiayantech.library.base.SingleFragmentActivity;

/**
 * Created by janseon on 2015/7/7.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class CreateEventActivity extends SingleFragmentActivity {
    private ImageView img_progress;
    private TextView txt_project;
    private TextView txt_summary;
    private TextView txt_success;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创建活动");
        setDisplayHomeAsUpEnabled();
        LinearLayout layout_parent = (LinearLayout) findViewById(R.id.layout_parent);
        View header = getLayoutInflater().inflate(R.layout.layout_create_event_header, layout_parent, false);
        layout_parent.addView(header, 0);
        img_progress = (ImageView) header.findViewById(R.id.img_progress);
        txt_project = (TextView) header.findViewById(R.id.txt_project);
        txt_summary = (TextView) header.findViewById(R.id.txt_summary);
        txt_success = (TextView) header.findViewById(R.id.txt_success);
        txt_project.setSelected(true);

        int paddingRight = getResources().getDisplayMetrics().widthPixels * 3 / 4;
        img_progress.setPadding(0, 0, paddingRight, 0);
    }

    @Override
    protected Fragment createFragment() {
        return new CreateEventProjectFragment() {
            @Override
            protected void onNext(String nickname, String phone, String hospital, String doctor, String project, double time) {
                int paddingRight = img_progress.getWidth() * 2 / 5;
                img_progress.setPadding(0, 0, paddingRight, 0);
                img_progress.invalidate();
                txt_summary.setSelected(true);
                replace(new CreateEventSummaryFragment() {
                    @Override
                    public void onSuccess() {
                        img_progress.setPadding(0, 0, 0, 0);
                        img_progress.invalidate();
                        txt_success.setSelected(true);
                        replace(new CreateEventSuccessFragment());
                    }
                }.setInfo(nickname, phone, hospital, doctor, project, time));
            }
        };
    }
}
