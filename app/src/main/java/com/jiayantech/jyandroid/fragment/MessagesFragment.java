package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.MessageAdapter;
import com.jiayantech.jyandroid.biz.UserBiz;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;
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
        setParams(new MessageAdapter(getActivity(), null), UserBiz.ACTION_MSG_LIST);
        ultimateRecyclerView.addItemDecoration(new DividerItemDecoration.Builder(getActivity())
                .showFirstEnable(true)
                .color(getResources().getColor(R.color.bg_gray))
                .size(UIUtil.dip2px(1))
                .margin(UIUtil.dip2px(20), UIUtil.dip2px(20))
                .build());

        getActivity().setTitle(R.string.title_mine_notification);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengPushManager.getInstance().setUnreadNotificationCount(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UmengPushManager.getInstance().setUnreadNotificationCount(0);
    }
}
