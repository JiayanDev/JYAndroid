package com.jiayantech.jyandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.CompanyEventActivity;
import com.jiayantech.jyandroid.activity.MessagesActivity;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.SettingActivity;
import com.jiayantech.jyandroid.activity.UserInfoActivity;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.eventbus.EditFinishEvent;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.fragment.webview.WebViewFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.AppInit;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
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

    private TextView txt_home_page;

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

        findViewById(R.id.layout_info).setOnClickListener(this);
        findViewById(R.id.txt_home_page).setOnClickListener(this);
        findViewById(R.id.txt_events).setOnClickListener(this);
        findViewById(R.id.txt_notification).setOnClickListener(this);
        findViewById(R.id.txt_company).setOnClickListener(this);
        findViewById(R.id.txt_setting).setOnClickListener(this);
        findViewById(R.id.txt_delete).setOnClickListener(this);

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
        if (AppInitManger.getRole() == AppInit.ROLE_ANGEL) {
            setHomePageVisible(true);
        }
    }

    public void onEvent(EditFinishEvent event) {
        switch (event.action) {
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
        setHomePageVisible(appInit.role == AppInit.ROLE_ANGEL);

    }

    public void setHomePageVisible(boolean flag) {
        AppInit appInit = AppInitManger.getAppInit();
//        divider_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(appInit.role) ? View.VISIBLE : View.GONE);
//        txt_home_page.setVisibility(AppInit.ROLE_ANGEL.equals(appInit.role) ? View.VISIBLE : View.GONE);
        txt_home_page.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_info:
                startActivity(UserInfoActivity.class);
                break;
            case R.id.txt_home_page:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewFragment.EXTRA_USER_ID, AppInitManger.getUserId());
                intent.putExtra(WebViewFragment.EXTRA_USERNAME, AppInitManger.getUserName());
                intent.putExtra(WebViewFragment.EXTRA_TYPE, WebConstans.Type.TYPE_PERSONAL_PAGE);
                startActivity(intent);
                break;
            case R.id.txt_events:
                startActivity(MyEventsActivity.class);
                break;
            case R.id.txt_notification:
                startActivity(MessagesActivity.class);
                break;
            case R.id.txt_company:
                startActivity(CompanyEventActivity.class);
                break;
            case R.id.txt_setting:
                startActivity(SettingActivity.class);
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
        }
    }
}