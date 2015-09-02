package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.fragment.EditGenderFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.ImageUploadResp;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by 健兴 on 2015/8/3.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener, PicGetter.PicGetListener {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_ACTION = "action";
    public static final String EXTRA_GENDER = "gender";
//    public static final String EXTRA_PROVINCE = "province";
//    public static final String EXTRA_CITY = "city";
//    public static final String EXTRA_BIRTHDAY = "birthday";

    public static final int ACTION_EDIT_NAME = 0;

    private TextView mNameText;
    private TextView mGenderText;
    private TextView mLocationText;
    private TextView mBirthdayText;
    private TextView mPhoneText;
    private RoundedImageView mAvatarImg;

    private TextView mTxtWechatAccount;
    private TextView mTxtQQAccount;
    private TextView mTxtWeiboAccount;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findViews();
        setViewsContent();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void findViews() {
        mNameText = (TextView) findViewById(R.id.txt_name);
        mGenderText = (TextView) findViewById(R.id.txt_gender);
        mLocationText = (TextView) findViewById(R.id.txt_location);
        mBirthdayText = (TextView) findViewById(R.id.txt_birthday);
        mPhoneText = (TextView) findViewById(R.id.txt_phone);
        mAvatarImg = (RoundedImageView)findViewById(R.id.img_avatar);

        mTxtWechatAccount = (TextView)findViewById(R.id.txt_wechat_username);
        mTxtQQAccount = (TextView)findViewById(R.id.txt_qq_username);
        mTxtWeiboAccount = (TextView)findViewById(R.id.txt_weibo_username);
    }

    protected void setViewsContent() {
        mNameText.setText(AppInitManger.getUserName());
        mGenderText.setText(AppInitManger.getUserGender() == 1 ? "男" : "女");
        mLocationText.setText(AppInitManger.getProvince() + " " + AppInitManger.getCity());
        mBirthdayText.setText(TimeUtil.stamp2YearMonthDay(AppInitManger.getBirthday() * 1000));
        BitmapBiz.display(mAvatarImg, AppInitManger.getAvatar());
        mPhoneText.setText(AppInitManger.getPhoneNum());

        if(AppInitManger.sAppInit.bindWX){
            mTxtWechatAccount.setText(R.string.account_status_bind);
            mTxtWechatAccount.setTextColor(getResources().getColor(R.color.text_normal_color));
        }
        if(AppInitManger.sAppInit.bindQQ){
            mTxtQQAccount.setText(R.string.account_status_bind);
            mTxtQQAccount.setTextColor(getResources().getColor(R.color.text_normal_color));
        }
        if(AppInitManger.sAppInit.bindWB){
            mTxtWeiboAccount.setText(R.string.account_status_bind);
            mTxtWeiboAccount.setTextColor(getResources().getColor(R.color.text_normal_color));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_avatar:
                showUploadDialog();
                break;
            case R.id.layout_name:
                startEditActivity(ACTION_EDIT_NAME, AppInitManger.getUserName());
                break;
            case R.id.layout_gender:
                EditGenderFragment fragment = EditGenderFragment.
                        createFragment(AppInitManger.getUserGender());
                fragment.show(getSupportFragmentManager(), "EditGender");
                break;
            case R.id.layout_location:
                Intent intent = new Intent(this, LocationSelectActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.layout_birthday:
                new DateTimeHelper(this).showDateDialog(new DateTimeHelper.OnSetDateTimeListener() {
                    @Override
                    public void onSetDateTime(final Calendar calendar) {
                        Map<String, String> params = new ArrayMap<>();
                        params.put("birthday", String.valueOf(calendar.getTimeInMillis() / 1000));
                        UserBiz.update(params, new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                mBirthdayText.setText(TimeUtil.
                                        stamp2YearMonthDay(calendar.getTimeInMillis()));
                            }
                        });
                    }
                }, false);
                break;
        }
    }

    private void startEditActivity(int action, String data) {
        Intent intent = new Intent(this, EditUserInfoActivity.class);
        intent.putExtra(EXTRA_ACTION, action);
        intent.putExtra(EXTRA_DATA, data);
        startActivity(intent);
    }

    private void showUploadDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.view_upload_menu, null);
        final Dialog dialog = DialogUtils.showViewDialog(view, true);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switch (v.getId()) {
                    case R.id.camera_button:
                        new PicGetter(UserInfoActivity.this, getActivityResultHelper(),
                                UserInfoActivity.this).startCamera();
                        break;
                    case R.id.local_button:
                        new PicGetter(UserInfoActivity.this, getActivityResultHelper(),
                                UserInfoActivity.this).startImage();
                        break;
                }
            }
        };
        view.findViewById(R.id.title_text).setVisibility(View.GONE);
        view.findViewById(R.id.camera_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.local_button).setOnClickListener(onClickListener);
        view.findViewById(R.id.cancel_button).setOnClickListener(onClickListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                final String province = data.getStringExtra("province");
                final String city = data.getStringExtra("city");
                Map<String, String> params = new ArrayMap<>();
                params.put("province", province);
                params.put("city", city);
                UserBiz.update(params, new ResponseListener<AppResponse>() {
                    @Override
                    public void onResponse(AppResponse appResponse) {
                        ToastUtil.showMessage("更新成功" + province + "" + city);
                        mLocationText.setText(province + " " + city);
                    }
                });
            }
        }
    }

    public void onEvent(EditFinishEvent event) {
        switch (event.action) {
            case EditFinishEvent.ACTION_EDIT_NAME:
                mNameText.setText(event.name);
                AppInitManger.sAppInit.name = event.name;
                break;
            case EditFinishEvent.ACTION_EDIT_GENDER:
                mGenderText.setText(event.gender == 1 ? "男" : "女");
                AppInitManger.sAppInit.gender = event.gender;
                ToastUtil.showMessage("更新性别成功");
                break;
            case EditFinishEvent.ACTION_EDIT_BIRTHDAY:
                mBirthdayText.setText(String.valueOf(event.birthday));
                AppInitManger.sAppInit.birthday = event.birthday;
                break;
            case EditFinishEvent.ACTION_EDIT_PHONE:
                mPhoneText.setText(event.phone);
                AppInitManger.sAppInit.phone = event.phone;
                AppInitManger.sAppInit.phoneNum = event.phone;
                break;
            case EditFinishEvent.ACTION_EDIT_AVATAR:
                BitmapBiz.display(mAvatarImg, event.avatar);
                ToastUtil.showMessage("头像上传成功成功:" + event.avatar);
                AppInitManger.sAppInit.avatar = event.avatar;
                break;
        }
    }


    @Override
    public void onPicGet(String path, Bitmap bitmap) {
        showProgressDialog();
        UploadImageBiz.uploadImage(UploadImageBiz.TYPE_AVATAR, bitmap, new File(path).getName(),
                new ResponseListener<ImageUploadResp>() {
                    @Override
                    public void onResponse(final ImageUploadResp imageUploadResp) {
                        Map<String, String> params = new ArrayMap<String, String>();
                        params.put("avatar", HttpConfig.IMAGE_SHOW_URL + imageUploadResp.url);
                        UserBiz.update(params, new ResponseListener<AppResponse>() {
                            @Override
                            public void onResponse(AppResponse appResponse) {
                                dismissProgressDialog();
                                EditFinishEvent event = new EditFinishEvent();
                                event.action = EditFinishEvent.ACTION_EDIT_AVATAR;
                                event.avatar = HttpConfig.IMAGE_SHOW_URL + imageUploadResp.url;
                                EventBus.getDefault().post(event);
                            }
                        });
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                        dismissProgressDialog();
                        ToastUtil.showMessage("头像更新失败");
                    }
                });
    }
}
