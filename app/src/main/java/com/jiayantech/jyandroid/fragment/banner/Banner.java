package com.jiayantech.jyandroid.fragment.banner;

import android.content.Context;

import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.model.Topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/6/30.
 */
public class Banner {
    public String imageUrl;
    public BannerOnClickListener listener;
    public long id;
    public String type;

    public static List<Banner> createBannerList(List<Topic> topicList){
        List<Banner> result = new ArrayList<>();
        for(Topic topic: topicList){
            final Banner banner = new Banner();
            banner.imageUrl = topic.coverImg;
            banner.type = "topic";
            banner.id = topic.id;
            banner.listener = new BannerOnClickListener() {
                @Override
                public void onClick(Context context) {
                    WebViewActivity.launchActivity(context, banner.id, banner.type);
                }
            };
            result.add(banner);
        }
        return result;
    }
}
