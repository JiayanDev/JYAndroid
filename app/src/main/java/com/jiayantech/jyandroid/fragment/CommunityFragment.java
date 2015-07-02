package com.jiayantech.jyandroid.fragment;

import android.os.Bundle;
import android.view.View;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.DiaryHeaderAdapter;
import com.jiayantech.jyandroid.adapter.PostAdapter;
import com.jiayantech.jyandroid.biz.ActivityBiz;
import com.jiayantech.jyandroid.model.DiaryHeader;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.model.PostHeader;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/6/25.
 * *
 * Update by janseon on 15/6/30
 */
public class CommunityFragment extends RefreshListFragment<PostHeader, AppResponse<List<PostHeader>>> {
    public static CommunityFragment newInstance(Bundle args) {
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onInitView() {
        super.onInitView();
        List<DiaryHeader> list = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            DiaryHeader header = new DiaryHeader();
            header.currentPhoto = "https://c4.staticflickr.com/8/7471/15386610694_ef7a87aca2.jpg";
            header.projectName = "自体脂肪隆胸" + i;
            list.add(header);
        }
        setParams(new PostAdapter(null), ActivityBiz.ACTION_ACTIVITY_LIST);
        //setParams(new DiaryHeaderAdapter(getActivity(), list), ActivityBiz.ACTION_ACTIVITY_LIST);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false);
        ultimateRecyclerView.setParallaxHeader(headerView);
    }

}