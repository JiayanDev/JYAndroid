package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.MyEventsActivity;
import com.jiayantech.jyandroid.activity.NotificationListActivity;
import com.jiayantech.library.base.BaseFragment;

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

    @Override
    protected int getInflaterResId() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void onInitView() {
        txt_events = (TextView) findViewById(R.id.txt_events);
        txt_events.setOnClickListener(this);

        txt_notifications = (TextView)findViewById(R.id.txt_notification);
        txt_notifications.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_events:
                startActivity(MyEventsActivity.class);
                break;
            case R.id.txt_notification:
                startActivity(NotificationListActivity.class);
                break;
        }
    }
}
