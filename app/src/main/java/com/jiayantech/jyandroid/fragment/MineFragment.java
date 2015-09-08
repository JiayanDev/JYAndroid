package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.AboutAngelActivity;
import com.jiayantech.jyandroid.activity.CompanyEventActivity;
import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.MessagesActivity;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.SettingActivity;
import com.jiayantech.jyandroid.activity.UserInfoActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.library.comm.ActivityResult;
import com.jiayantech.umeng_push.UnreadMessageEvent;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.ToastUtil;

import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/6/25.
 *
 * @Update by janseon on 15/7/7
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "MineFragment";

    public static MineFragment newInstance(Bundle args) {
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View layout_info;
    private ImageView img_avatar;
    private TextView txt_nickname;
    private TextView txt_info;

    private TextView txt_home_page;
    private View divider_home_page;

    private TextView mTxtUnreadNotification;
    private ImageView mImageUnreadCompany;
    private ImageView mImageUnreadAngel;

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onInitView() {
        layout_info = findViewById(R.id.layout_info);
        img_avatar = (ImageView) findViewById(R.id.img_avatar);
        txt_nickname = (TextView) findViewById(R.id.txt_nickname);
        txt_info = (TextView) findViewById(R.id.txt_info);

        txt_home_page = (TextView) findViewById(R.id.txt_home_page);
//<<<<<<< HEAD
        //txt_home_page.setOnClickListener(this);

//        txt_events = (TextView) findViewById(R.id.txt_angel);
//        txt_events.setOnClickListener(this);
//
//        txt_notifications = (TextView) findViewById(R.id.txt_notification);
//        txt_notifications.setOnClickListener(this);
//
//        txt_setting = (TextView)findViewById(R.id.txt_setting);
//        txt_setting.setOnClickListener(this);
//
//        txt_logout = (TextView) findViewById(R.id.txt_logout);
//        txt_logout.setOnClickListener(this);
//
//        txt_delete = (TextView) findViewById(R.id.txt_delete);
//        txt_delete.setOnClickListener(this);
//
//        txt_mine = (TextView) findViewById(R.id.txt_my_company);
//        txt_mine.setOnClickListener(this);

        mTxtUnreadNotification = (TextView) findViewById(R.id.txt_unread_notification);
        mImageUnreadCompany = (ImageView) findViewById(R.id.image_unread_company);
        mImageUnreadAngel = (ImageView) findViewById(R.id.image_unread_angel);
        divider_home_page = findViewById(R.id.divider_home_page);

        findViewById(R.id.layout_info).setOnClickListener(this);
        findViewById(R.id.txt_home_page).setOnClickListener(this);
        findViewById(R.id.txt_angel).setOnClickListener(this);
        findViewById(R.id.txt_notification).setOnClickListener(this);
        findViewById(R.id.txt_my_company).setOnClickListener(this);
        findViewById(R.id.txt_setting).setOnClickListener(this);
        findViewById(R.id.txt_delete).setOnClickListener(this);

        int unreadNotificationCount = UmengPushManager.getInstance().getUnreadNotificationCount();
        boolean unreadCompany = UmengPushManager.getInstance().getUnreadCompanyCount();
        boolean unreadAngel = UmengPushManager.getInstance().getUnreadAngelCount();
        UnreadMessageEvent event = new UnreadMessageEvent(unreadNotificationCount,
                unreadCompany, unreadAngel);
        onEvent(event);
        resume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);


//        EventBus.getDefault().post(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void resume() {
        if (img_avatar != null && AppInitManger.isRegister()) {
            setUserInfo();
            UserBiz.detail(new ResponseListener<AppResponse<AppInit>>() {
                @Override
                public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                    appInitAppResponse.data.register = true;
                    AppInitManger.save(appInitAppResponse.data);
                    setUserInfo();
                }
            });
        }
        setHomePageVisible(AppInitManger.getRole());
    }

    public void onEvent(EditFinishEvent event) {
        switch (event.action) {
            case EditFinishEvent.ACTION_EDIT_AVATAR:
                BitmapBiz.display(img_avatar, event.avatar);
                break;
        }
    }

    public void onEvent(UnreadMessageEvent event) {
        LogUtil.i(TAG, "handling UnreadMessageEvent");
        if (event.unreadNotificaition > 0) {
            mTxtUnreadNotification.setVisibility(View.VISIBLE);
            mTxtUnreadNotification.setText(String.valueOf(event.unreadNotificaition));
        } else {
            mTxtUnreadNotification.setVisibility(View.INVISIBLE);
        }
        //setHomePageVisible(true);
        if (event.unreadCompany) {
            mImageUnreadCompany.setVisibility(View.VISIBLE);
        } else {
            mImageUnreadCompany.setVisibility(View.INVISIBLE);
        }

        if (event.unreadAngel) {
            mImageUnreadAngel.setVisibility(View.VISIBLE);
        } else {
            mImageUnreadAngel.setVisibility(View.INVISIBLE);
        }
    }

    private void setUserInfo() {
        AppInit appInit = AppInitManger.getAppInit();
        BitmapBiz.display(img_avatar, appInit.avatar);
        txt_nickname.setText(appInit.name);
        String info = appInit.gender == 0 ? getString(R.string.gender_female) : getString(R.string.gender_male)
                + "  " + AppInitManger.getProvince() + AppInitManger.getCity();
        if (AppInitManger.getBirthday() != 0) {
            try {
                int age = TimeUtil.getAge(new Date(AppInitManger.getBirthday() * 1000));
                info += "  " + age + "岁";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        txt_info.setText(info);
        setHomePageVisible(appInit.role);

    }

    public void setHomePageVisible(String role) {
        divider_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(role) ? View.VISIBLE : View.GONE);
        txt_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(role) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_info:
                startActivity(UserInfoActivity.class);
                break;
//            case R.id.txt_home_page:
//                Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_PERSONAL_PAGE);
//                startActivity(intent);
//                break;
            case R.id.txt_angel:
                startActivity(MyEventsActivity.class);
//                if (AppInit.ROLE_ANGEL.equals(AppInitManger.getAppInit().role)) {
//                    startActivity(MyEventsActivity.class);
//                } else {
//                    startActivity(AboutAngelActivity.class);
//                }
                break;
            case R.id.txt_notification:
                startActivity(MessagesActivity.class);
                break;
//            case R.id.txt_my_company:
//                startActivity(CompanyEventActivity.class);
//                break;
            case R.id.txt_setting:
                startActivityForResult(SettingActivity.class, new ActivityResult() {
                    @Override
                    public void onActivityResult(Intent data) {
                        ((MainActivity) getActivity()).resetCheck();
                    }
                });
                //startActivity(SettingActivity.class);
                break;
            case R.id.txt_delete:
                ((BaseActivity) getActivity()).showProgressDialog();
                UserBiz.delete(new ResponseListener<BaseAppResponse>() {
                    @Override
                    public void onResponse(BaseAppResponse baseAppResponse) {
                        ((BaseActivity) getActivity()).dismissProgressDialog();
                        ToastUtil.showMessage("delete");
                    }
                });
                break;
            case R.id.txt_my_company:
                startActivity(CompanyEventActivity.class);
                break;
            case R.id.txt_home_page:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                //intent.putExtra(WebViewFragment.EXTRA_USER_ID, AppInitManger.getUserId());
                //intent.putExtra(WebViewFragment.EXTRA_USERNAME, AppInitManger.getUserName());
                intent.putExtra(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_PERSONAL_PAGE);
                startActivity(intent);
                break;
        }
    }
}
