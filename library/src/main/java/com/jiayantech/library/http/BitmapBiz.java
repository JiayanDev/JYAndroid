package com.jiayantech.library.http;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.jiayantech.library.base.BaseApplication;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: image bitmap display
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class BitmapBiz {

    private static final int DEFAULT_SIZE = BaseApplication.getContext().getResources().getDisplayMetrics().widthPixels;

    /**
     * @param imageView
     * @param imageUrl
     */
    public static void display(ImageView imageView, String imageUrl, int size) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            return;
        }
        sImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView, HttpConfig.DEFAULT_IMAGE_ID, HttpConfig.ERROR_IMAGE_ID),
                //Specify width & height of the bitmap to be scaled down when the image is downloaded.
                size, size);
    }

    public static void display(ImageView imageView, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            return;
        }
        sImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView, HttpConfig.DEFAULT_IMAGE_ID, HttpConfig.ERROR_IMAGE_ID),
                //Specify width & height of the bitmap to be scaled down when the image is downloaded.
                DEFAULT_SIZE, DEFAULT_SIZE);
    }



    ///////////////////////////////////////private static class and method
    private static final int max_cache_size = 1000000;
    /**
     * Initialise Volley Request Queue. *
     */
    private static final RequestQueue sVolleyQueue = Volley.newRequestQueue(BaseApplication.getContext());
    private static final ImageLoader sImageLoader = new ImageLoader(sVolleyQueue, new BitmapLruCache(max_cache_size));

    private static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public BitmapLruCache(int maxSize) {
            super(maxSize);
            //or setLimit(Runtime.getRuntime().maxMemory()/4);
        }

        /*@Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }*/

        @Override
        public Bitmap getBitmap(String url) {
            //System.out.println("######## BitmapLruCache GET ######## " + url);
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            System.out.println("######## BitmapLruCache PUT ######## " + url);
            put(url, bitmap);
        }
    }
}