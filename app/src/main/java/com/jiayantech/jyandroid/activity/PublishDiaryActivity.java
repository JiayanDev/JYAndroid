package com.jiayantech.jyandroid.activity;

import android.content.Intent;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.DiaryBiz;
import com.jiayantech.jyandroid.commons.Broadcasts;
import com.jiayantech.jyandroid.eventbus.AddPostFinishEvent;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.helper.BroadcastHelper;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by janseon on 2015/7/2.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class PublishDiaryActivity extends PublishPostActivity {
    private final String UPLOAD_TYPE_DIARY = "diary";
    //private LinearLayout layout_container;
    //private TextView txt_project;
    //private TextView txt_time;

    private String doctorId;
    private String doctorName;
    private String hospitalId;
    private String hospitalName;
    private ArrayList<AppInit.Category> categoryList;
    private long operationTime;
    private String price;
    private float satisfyLevel;

    @Override
    protected void findViews() {
        super.findViews();
        //layout_container = (LinearLayout) findViewById(R.id.layout_container);
        //View header = getLayoutInflater().inflate(R.layout.layout_publish_diary_header, layout_container, false);
        //layout_container.addView(header, 0);
        //txt_project = (TextView) header.findViewById(R.id.txt_project);
        //txt_time = (TextView) header.findViewById(R.id.txt_time);
        findViewById(R.id.layout_category).setVisibility(View.GONE);
    }

    @Override
    protected void setViewsContent() {
        super.setViewsContent();
        setTitle(R.string.title_publish_diary);
        edit_content.setHint(R.string.hint_publish_diary);
        Intent intent = getIntent();
        doctorId = intent.getStringExtra(NewDiaryInfoActivity.KEY_doctorId);
        doctorName = intent.getStringExtra(NewDiaryInfoActivity.KEY_doctorName);
        hospitalId = intent.getStringExtra(NewDiaryInfoActivity.KEY_hospitalId);
        hospitalName = intent.getStringExtra(NewDiaryInfoActivity.KEY_hospitalName);

        setCategories(intent);
        setTime(intent);
        price = intent.getStringExtra(NewDiaryInfoActivity.KEY_price);
        satisfyLevel = intent.getFloatExtra(NewDiaryInfoActivity.KEY_satisfyLevel, 0);
        UPLOAD_TYPE = UPLOAD_TYPE_DIARY;

        //txt_project.setVisibility(View.GONE);
        //txt_time.setVisibility(View.GONE);
    }

    @Override
    protected void setViewsListener() {
        super.setViewsListener();
        //txt_project.setOnClickListener(this);
        //txt_time.setOnClickListener(this);
    }

    private void setCategories(Intent intent) {
        //categoryList = intent.getParcelableArrayListExtra(SelectProjectActivity.KEY_categories);
        //txt_project.setText(AppInit.Category.toNamesString(categoryList));
    }

    private void setTime(Intent intent) {
        //operationTime = intent.getLongExtra(NewDiaryInfoActivity.KEY_operationTime, 0);
        //txt_time.setText(TimeUtil.getStrTime(operationTime * 1000));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.txt_project:
//                Intent intent = new Intent(this, SelectProjectActivity.class);
//                intent.putExtra(SelectProjectActivity.KEY_TO_PICK, true);
//                startActivityForResult(intent, new ActivityResult() {
//                    @Override
//                    public void onActivityResult(Intent data) {
//                        setCategories(data);
//                    }
//                });
//                break;
//            case R.id.txt_time:
//                new DateTimeHelper(this).showDateTimeDialog(new DateTimeHelper.OnSetDateTimeListener() {
//                    @Override
//                    public void onSetDateTime(Calendar calendar) {
//                        txt_time.setText(TimeUtil.getStrTime(calendar.getTimeInMillis()));
//                        operationTime = calendar.getTimeInMillis() / 1000;
//                    }
//                });
//                break;
//        }
    }

    @Override
    protected void onPost(String content) {
        String photoUrls = toString(mImageAdapter.urlList);
        DiaryBiz.post(content, photoUrls, new ResponseListener<BaseAppResponse>() {
            @Override
            public void onResponse(BaseAppResponse response) {
                ToastUtil.showMessage(R.string.msg_publish_diary_success);
                BroadcastHelper.send(Broadcasts.ACTION_PUBLISH_DIARY_BOOK);
                EventBus.getDefault().post(new AddPostFinishEvent());
                //finish();
                //finish();
            }
        });
//        String categoryIds = categoryList.toString();
//        showProgressDialog();
//        DiaryBiz.create(categoryIds, operationTime, hospitalId, doctorId, doctorName, price, satisfyLevel, content, photoUrls, new ResponseListener<BaseModel>() {
//            @Override
//            public void onResponse(BaseModel response) {
//                dismissProgressDialog();
//                BroadcastHelper.send(Broadcasts.ACTION_PUBLISH_DIARY_BOOK);
//                finish();
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                super.onErrorResponse(error);
//                dismissProgressDialog();
//            }
//        });
    }
}
