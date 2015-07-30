package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.adapter.MessageAdapter;
import com.jiayantech.jyandroid.model.Message;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class MessagesFragment extends RefreshListFragment<Message, AppResponse<List<Message>>>{
    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new MessageAdapter(null), "action");
    }
}