package com.jiayantech.jyandroid.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.EventAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.Event;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.BitmapBiz;

import java.util.List;

/**
 * Created by liangzili on 15/8/6.
 */
public class PersonalPageFragment extends RefreshListFragment<Event, AppResponse<List<Event>>>{

    @Override
    public void onInitView() {
        super.onInitView();
        setParams(new EventAdapter(getActivity(), null), EventBiz.ACTION_LIST);
        LinearLayout header = (LinearLayout)setHeader(R.layout.header_personal_page);
        ImageView avatar = (ImageView)header.findViewById(R.id.avatar);
        BitmapBiz.display(avatar, AppInitManger.sAppInit.avatar);
    }
}
