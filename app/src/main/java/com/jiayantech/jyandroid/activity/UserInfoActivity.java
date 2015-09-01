package com.jiayantech.jyandroid.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.fragment.EditGenderFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.ImageUploadResp;
import com.jiayantech.jyandroid.widget.ItemsLayout;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.library.comm.PicGetter;
import com.jiayantech.library.helper.DateTimeHelper;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.http.UploadReq;
import com.jiayantech.library.utils.AssertsUtil;
import com.jiayantech.library.utils.DialogUtils;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public static final int ACTION_EDIT_NAME = 0;

    private TextView mNameText;
    private TextView mGenderText;
    private TextView mLocationText;
    private TextView mBirthdayText;
    private TextView mPhoneText;
    private TextView txt_wechat;
    private TextView txt_qq;
    private TextView txt_weibo;
    private RoundedImageView mAvatarImg;

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
        mAvatarImg = (RoundedImageView) findViewById(R.id.img_avatar);
        txt_wechat = (TextView) findViewById(R.id.txt_wechat);
        txt_qq = (TextView) findViewById(R.id.txt_qq);
        txt_weibo = (TextView) findViewById(R.id.txt_weibo);
    }

    protected void setViewsContent() {
        setTitle(R.string.user_info);
        mNameText.setText(AppInitManger.getUserName());
        mGenderText.setText(AppInitManger.getUserGender() == 1 ? "男" : "女");
        mLocationText.setText(AppInitManger.getProvince() + AppInitManger.getCity());
        mLocationText.setText(AppInitManger.getProvince() + AppInitManger.getCity());
        mBirthdayText.setText(TimeUtil.stamp2YearMonthDay(AppInitManger.getBirthday() * 1000));
        mPhoneText.setText(AppInitManger.getPhoneNum());
        BitmapBiz.display(mAvatarImg, AppInitManger.getAvatar());
        AppInit appInit = AppInitManger.getAppInit();
        txt_wechat.setText(appInit.bindWX ? "已绑定" : "未绑定");
        txt_qq.setText(appInit.bindQQ ? "已绑定" : "未绑定");
        txt_weibo.setText(appInit.bindWB ? "已绑定" : "未绑定");
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
                        Map<String, String> params = new ArrayMap<String, String>();
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
            case R.id.layout_phone:

                break;
            case R.id.layout_wechat:

                break;
            case R.id.layout_qq:

                break;
            case R.id.layout_weibo:

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
        View view = LayoutInflater.from(this).inflate(R.layout.view_bottom_menus, null);
        ItemsLayout itemsLayout = (ItemsLayout) view.findViewById(R.id.layout_items);
        itemsLayout.setDriver();
        itemsLayout.setDriverLeftMargin(0);
        final Dialog dialog = DialogUtils.showViewDialog(view, true);
        itemsLayout.addMenuItem(getString(R.string.take_camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new PicGetter(UserInfoActivity.this, getActivityResultHelper(),
                        UserInfoActivity.this).startCropCamera();
            }
        });
        itemsLayout.addMenuItem(getString(R.string.take_photo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new PicGetter(UserInfoActivity.this, getActivityResultHelper(),
                        UserInfoActivity.this).startCropImage();
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
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
                AppInitManger.sAppInit.avatar = event.avatar;
                AppInitManger.save(AppInitManger.sAppInit);
                BitmapBiz.display(mAvatarImg, event.avatar);
                //ToastUtil.showMessage("头像上传成功成功:" + event.avatar);
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
                                BitmapBiz.clear(AppInitManger.sAppInit.avatar);
//                                AppInitManger.sAppInit.avatar =
//                                        HttpConfig.IMAGE_SHOW_URL + imageUploadResp.url;
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
