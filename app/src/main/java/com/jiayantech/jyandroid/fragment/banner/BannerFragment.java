package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.model.Topic;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by liangzili on 15/6/29.
 */
public class BannerFragment extends Fragment {
    private static int BANNER_SCROLL_INTERVAL = 2000;

    private Context mContext;
    private BannerViewPager mAutoScrollViewPager;
    private CirclePageIndicator mIndicator;

    private int index;
    private List<Banner> mBannerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_banner, container, false);
        mAutoScrollViewPager = (BannerViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initBannerList();


    }

    public void setBannerList(List<Banner> list) {
        mBannerList = list;
    }

    private void initBannerList() {
//        mBannerList = new ArrayList<>();
//        for(int i = 0; i < BannerUrl.BannerUrls.length; i++){
//            Banner banner = new Banner();
//            banner.imageUrl = BannerUrl.BannerUrls[i];
//            mBannerList.add(banner);
//        }
        PostBiz.getTopicList(new ResponseListener<AppResponse<List<Topic>>>() {
            @Override
            public void onResponse(AppResponse<List<Topic>> response) {
                show(response.data);
            }

            private List<Topic> mCacheList;

            @Override
            public void onLoadResponse(AppResponse<List<Topic>> response) {
                mCacheList = response.data;
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (mCacheList != null) {
                    show(mCacheList);
                    mCacheList = null;
                }
            }

            private void show(List<Topic> list) {
                mBannerList = Banner.createBannerList(list);
                mAutoScrollViewPager.setAdapter(new BannerPagerAdapter(mContext, mBannerList));
                mIndicator.setViewPager(mAutoScrollViewPager);

                mAutoScrollViewPager.setInterval(BANNER_SCROLL_INTERVAL);
                mAutoScrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
                mAutoScrollViewPager.startAutoScroll();
            }
        });
    }
}
