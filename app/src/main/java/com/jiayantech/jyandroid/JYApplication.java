package com.jiayantech.jyandroid;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by liangzili on 15/6/24.
 */
public class JYApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(5)
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .build();

        ImageLoader.getInstance().init(config);
    }
}
