package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.CompanyEventActivity;
import com.jiayantech.jyandroid.activity.MainActivity;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.MessagesActivity;
import com.jiayantech.jyandroid.activity.UserInfoActivity;
import com.jiayantech.jyandroid.biz.CommBiz;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/6/25.
 *
 * @Update by janseon on 15/7/7
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    public static MineFragment newInstance(Bundle args) {
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View layout_info;
    private ImageView img_avatar;
    private TextView txt_nickname;
    private TextView txt_info;

    private View divider_home_page;
    private TextView txt_home_page;
    private TextView txt_events;
    private TextView txt_notifications;
    private TextView txt_mine;
    private TextView txt_logout;
    private TextView txt_delete;

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

        layout_info.setOnClickListener(this);

        divider_home_page = findViewById(R.id.divider_home_page);
        txt_home_page = (TextView) findViewById(R.id.txt_home_page);

        txt_events = (TextView) findViewById(R.id.txt_events);
        txt_events.setOnClickListener(this);

        txt_notifications = (TextView) findViewById(R.id.txt_notification);
        txt_notifications.setOnClickListener(this);

        txt_logout = (TextView) findViewById(R.id.txt_logout);
        txt_logout.setOnClickListener(this);

        txt_delete = (TextView) findViewById(R.id.txt_delete);
        txt_delete.setOnClickListener(this);

        txt_mine = (TextView) findViewById(R.id.txt_mime);
        txt_mine.setOnClickListener(this);

        resume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
    }

    public void onEvent(EditFinishEvent event){
        switch (event.action){
            case EditFinishEvent.ACTION_EDIT_AVATAR:
                BitmapBiz.display(img_avatar, event.avatar);
                break;
        }
    }

    private void setUserInfo() {
        AppInit appInit = AppInitManger.getAppInit();
        BitmapBiz.display(img_avatar, appInit.avatar);
        txt_nickname.setText(appInit.name);
        //txt_info.setText(AppInitManger.getUserName());
        divider_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(appInit.role) ? View.VISIBLE : View.GONE);
        txt_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(appInit.role) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_info:
                startActivity(UserInfoActivity.class);
                break;
            case R.id.txt_events:
                startActivity(MyEventsActivity.class);
                break;
            case R.id.txt_notification:
                startActivity(MessagesActivity.class);
                break;
            case R.id.txt_logout:
                ((BaseActivity) getActivity()).showProgressDialog();
//                UserBiz.logout(new ResponseListener<BaseAppResponse>() {
//                    @Override
//                    public void onResponse(BaseAppResponse baseAppResponse) {
//                        ((BaseActivity) getActivity()).dismissProgressDialog();
//                        ToastUtil.showMessage("logout");
//                    }
//                });
                ConfigManager.putToken("");
                CommBiz.appInit(new ResponseListener<AppResponse<AppInit>>() {
                    @Override
                    public void onResponse(AppResponse<AppInit> appInitAppResponse) {
                        AppInit appInit = appInitAppResponse.data;
                        AppInitManger.save(appInit);
                        ((BaseActivity) getActivity()).dismissProgressDialog();
                        ((MainActivity) getActivity()).onCheckedChanged(null, R.id.radio_activity);
                    }
                });
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
            case R.id.txt_mime:
                startActivity(CompanyEventActivity.class);
                break;
        }
    }
}
