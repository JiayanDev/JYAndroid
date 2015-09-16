package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.adapter.MessageAdapter;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.umeng_push.model.BasePushMessage;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class MessagesFragment extends RefreshListFragment<BasePushMessage,
        AppResponse<List<BasePushMessage>>>{
    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new MessageAdapter(null), UserBiz.ACTION_MSG_LIST);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengPushManager.getInstance().setUnreadNotificationCount(0);
    }
}
