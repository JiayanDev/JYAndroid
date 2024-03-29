package com.jiayantech.jyandroid.fragment;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.adapter.HomePagePostAdapter;
import com.jiayantech.jyandroid.biz.EventBiz;
import com.jiayantech.jyandroid.model.HomePagePost;
import com.jiayantech.jyandroid.widget.commons.DividerItemDecoration;
import com.jiayantech.library.base.RefreshListFragment;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.utils.UIUtil;

import java.util.List;

/**
 * Created by 健兴 on 2015/8/1.
 */
public class HomePagePostFragment extends RefreshListFragment<HomePagePost,
        AppResponse<List<HomePagePost>>> {
    @Override
    public void onInitView() {
        super.onInitView();
        addItemDecoration(new DividerItemDecoration.Builder(getActivity()).build());
        setParams(new HomePagePostAdapter(getActivity(), null), EventBiz.ACTION_HOMEPAGE_LIST);
        //setHeader(R.layout.banner);
        setEnablePaging(false);
    }
}