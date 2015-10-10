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
import com.jiayantech.jyandroid.biz.SocialLoginBiz;
import com.jiayantech.jyandroid.biz.UploadImageBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.fragment.EditGenderFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.jyandroid.model.ImageUploadResp;
import com.jiayantech.jyandroid.widget.ItemsLayout;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.comm.ActivityResult;
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

    public static final String EXTRA_TEXT = "extra_text";
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
        mAvatarImg = (RoundedImageView) findViewById(R.id.img_avatar);

        mTxtWechatAccount = (TextView) findViewById(R.id.txt_wechat_username);
        mTxtQQAccount = (TextView) findViewById(R.id.txt_qq_username);
        mTxtWeiboAccount = (TextView) findViewById(R.id.txt_weibo_username);
//=======
//        mAvatarImg = (RoundedImageView) findViewById(R.id.img_avatar);
//        txt_wechat = (TextView) findViewById(R.id.txt_wechat);
//        txt_qq = (TextView) findViewById(R.id.txt_qq);
//        txt_weibo = (TextView) findViewById(R.id.txt_weibo);
//>>>>>>> upstream/master
    }

    protected void setViewsContent() {
        setTitle(R.string.user_info);
        mNameText.setText(AppInitManger.getUserName());
        mGenderText.setText(AppInitManger.getUserGender() == 1 ? "男" : "女");
        mLocationText.setText(AppInitManger.getProvince() + AppInitManger.getCity());
        mLocationText.setText(AppInitManger.getProvince() + AppInitManger.getCity());
        mBirthdayText.setText(AppInitManger.getBirthday() == null ? "" : TimeUtil.stamp2YearMonthDay(AppInitManger.getBirthday() * 1000));
        mPhoneText.setText(AppInitManger.getPhoneNum());
        BitmapBiz.display(mAvatarImg, AppInitManger.getAvatar());
        mPhoneText.setText(AppInitManger.getPhoneNum());

        if (AppInitManger.sAppInit.bindWX) {
            mTxtWechatAccount.setText(R.string.account_status_bind);
            mTxtWechatAccount.setTextColor(getResources().getColor(R.color.text_normal_color));
        } else {
            mTxtWechatAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SocialLoginBiz.wechatLogin(new SocialLoginBiz.GetCodeListener() {
                        @Override
                        public void onGetCode(String code) {
                            UserBiz.bindWechat(code, new ResponseListener<AppInit>() {
                                @Override
                                public void onResponse(AppInit response) {
                                    mTxtWechatAccount.setText(R.string.account_status_bind);
                                    mTxtWechatAccount.setTextColor(getResources().
                                            getColor(R.color.text_normal_color));
                                }
                            });
                        }

                        @Override
                        public void onUninstalled() {
                            ToastUtil.showMessage("未安装微信！");
                        }
                    });
                }
            });
        }
        if (AppInitManger.sAppInit.bindQQ) {
            mTxtQQAccount.setText(R.string.account_status_bind);
            mTxtQQAccount.setTextColor(getResources().getColor(R.color.text_normal_color));
        }
        if (AppInitManger.sAppInit.bindWB) {
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
                startActivityForResult(intent, new ActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                                    AppInitManger.sAppInit.province = province;
                                    AppInitManger.sAppInit.city = city;
                                    EditFinishEvent event = new EditFinishEvent();
                                    event.action = EditFinishEvent.ACTION_EDIT_LOCATION;
                                    event.province = province;
                                    event.city = city;
                                    EventBus.getDefault().post(event);
                                }
                            });
                        }
                    }
                });
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
                                AppInitManger.sAppInit.birthday = calendar.getTimeInMillis() / 1000;
                                EditFinishEvent event = new EditFinishEvent();
                                event.action = EditFinishEvent.ACTION_EDIT_BIRTHDAY;
                                event.birthday = calendar.getTimeInMillis() / 1000;
                                EventBus.getDefault().post(event);
                            }
                        });
                    }
                }, false);
                break;
            case R.id.layout_phone:
                Intent intent1 = new Intent(this, VerifyPhoneActivity.class);
                intent1.putExtra(VerifyPhoneActivity.KEY_TYPE, VerifyPhoneActivity.TYPE_UPDATE_PHONE);
                startActivityForResult(intent1, new ActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        String phone = null;
                        if (data != null && data.getStringArrayExtra("phone") != null) {
                            phone = data.getStringExtra("phone");
                            if (phone != null) {
                                mPhoneText.setText(phone);
                            }
                        }
                    }
                });
                break;
//            case R.id.layout_wechat:
//
//                break;
//            case R.id.layout_qq:
//
//                break;
//            case R.id.layout_weibo:
//
//                break;
        }
    }

    private void startEditActivity(int action, String data) {
        Intent intent = new Intent(this, EditUserInfoActivity.class);
        intent.putExtra(EXTRA_ACTION, action);
        intent.putExtra(EXTRA_TEXT, data);
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


    public void onEvent(EditFinishEvent event) {
        switch (event.action) {
            case EditFinishEvent.ACTION_EDIT_NAME:
                mNameText.setText(event.name);
                ToastUtil.showMessage(R.string.update_success_username);
                break;
            case EditFinishEvent.ACTION_EDIT_GENDER:
                mGenderText.setText(event.gender == 1 ? "男" : "女");
                ToastUtil.showMessage(R.string.update_success_gender);
                break;
            case EditFinishEvent.ACTION_EDIT_BIRTHDAY:
                mBirthdayText.setText(TimeUtil.
                        stamp2YearMonthDay(event.birthday * 1000));
                ToastUtil.showMessage(R.string.update_success_birthday);
                break;
            case EditFinishEvent.ACTION_EDIT_PHONE:
                mPhoneText.setText(event.phone);
                ToastUtil.showMessage(R.string.update_success_cellphone);
                break;
            case EditFinishEvent.ACTION_EDIT_AVATAR:
                BitmapBiz.display(mAvatarImg, event.avatar);
                ToastUtil.showMessage(R.string.update_success_avatar);
                break;
            case EditFinishEvent.ACTION_EDIT_LOCATION:
                mLocationText.setText(event.province + " " + event.city);
                ToastUtil.showMessage(R.string.update_success_location);

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
                                AppInitManger.sAppInit.avatar =
                                        HttpConfig.IMAGE_SHOW_URL + imageUploadResp.url;
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
