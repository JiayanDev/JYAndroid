package com.jiayantech.jyandroid.model;

import com.jiayantech.jyandroid.adapter.ImagePagerAdapter;

/**
 * Created by janseon on 2015/7/8.
 *
 * @Description:
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class Photo implements ImagePagerAdapter.IUrl {
    public String url;

    public Photo() {
    }

    public Photo(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
