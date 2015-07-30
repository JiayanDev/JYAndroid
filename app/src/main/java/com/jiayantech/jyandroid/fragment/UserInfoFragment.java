package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.MessagesActivity;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseFragment;
import com.jiayantech.library.http.BaseAppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/6/25.
 *
 * @Update by janseon on 15/7/7
 */
public class UserInfoFragment extends BaseFragment implements View.OnClickListener {
    public static UserInfoFragment newInstance(Bundle args) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView txt_events;
    private TextView txt_notifications;
    private TextView txt_logout;
    private TextView txt_delete;

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void onInitView() {
        txt_events = (TextView) findViewById(R.id.txt_events);
        txt_events.setOnClickListener(this);

        txt_notifications = (TextView) findViewById(R.id.txt_notification);
        txt_notifications.setOnClickListener(this);

        txt_logout = (TextView) findViewById(R.id.txt_logout);
        txt_logout.setOnClickListener(this);

        txt_delete = (TextView) findViewById(R.id.txt_delete);
        txt_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_events:
                startActivity(MyEventsActivity.class);
                break;
            case R.id.txt_notification:
                startActivity(MessagesActivity.class);
                break;
            case R.id.txt_logout:
                ((BaseActivity) getActivity()).showProgressDialog();
                UserBiz.logout(new ResponseListener<BaseAppResponse>() {
                    @Override
                    public void onResponse(BaseAppResponse baseAppResponse) {
                        ((BaseActivity) getActivity()).dismissProgressDialog();
                        ToastUtil.showMessage("logout");
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
        }
    }
}
