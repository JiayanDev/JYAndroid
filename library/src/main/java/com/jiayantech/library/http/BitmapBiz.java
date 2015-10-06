package com.jiayantech.library.http;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
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
    public static void display(ImageView imageView, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            return;
        }
        sImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView, HttpConfig.DEFAULT_IMAGE_ID, HttpConfig.ERROR_IMAGE_ID),
                //Specify width & height of the bitmap to be scaled down when the image is downloaded.
                DEFAULT_SIZE, DEFAULT_SIZE);
    }

    /**
     * 加载图片
     * @param imageUrl 图片url
     * @param listener 加载完成后的回调
     */
    public static void loadImage(String imageUrl, ImageLoader.ImageListener listener){
        sImageLoader.get(imageUrl, listener);
    }

    /**
     * @param imageView
     * @param imageUrl
     * @param size
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

    public static void display(ImageView imageView, String imageUrl, SimpleImageListener l) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            return;
        }
        l.view = imageView;
        sImageLoader.get(imageUrl, l, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public static void displayWithScaleSize(final ImageView imageView, String imageUrl, final int imageWidth) {
        display(imageView, imageUrl, new BitmapBiz.SimpleImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                    int width = response.getBitmap().getWidth();
                    int height = response.getBitmap().getHeight();
                    int imageHeight = imageWidth * height / width;
                    ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                    lp.height = imageHeight;
                    imageView.setLayoutParams(lp);
                } else if (HttpConfig.DEFAULT_IMAGE_ID != 0) {
                    view.setImageResource(HttpConfig.DEFAULT_IMAGE_ID);
                }
            }
        });
    }

    public static Bitmap getBitmap(String key) {
        return sBitmapLruCache.get(getCacheKey(key, DEFAULT_SIZE, DEFAULT_SIZE));
    }

    public static void clear(String imageUrl) {
        clear(imageUrl, DEFAULT_SIZE);
    }

    public static void clear(String imageUrl, int size) {
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        final String cacheKey = getCacheKey(imageUrl, size, size);
        sBitmapLruCache.remove(cacheKey);
        sVolleyQueue.getCache().remove(imageUrl);
    }

    private static String getCacheKey(String url, int maxWidth, int maxHeight) {
        return (new StringBuilder(url.length() + 12)).append("#W").append(maxWidth).append("#H").append(maxHeight).append(url).toString();
    }


    ///////////////////////////////////////private static class and method
    private static final int max_cache_size = 1000000;
    /**
     * Initialise Volley Request Queue. *
     */
    private static final RequestQueue sVolleyQueue = Volley.newRequestQueue(BaseApplication.getContext());
    private static final BitmapLruCache sBitmapLruCache = new BitmapLruCache(max_cache_size);
    private static final ImageLoader sImageLoader = new ImageLoader(sVolleyQueue, sBitmapLruCache);

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

    ////////////////
    public static abstract class SimpleImageListener implements ImageLoader.ImageListener {
        protected ImageView view;

        public void onErrorResponse(VolleyError error) {
            if (HttpConfig.ERROR_IMAGE_ID != 0) {
                view.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            }

        }

        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            if (response.getBitmap() != null) {
                view.setImageBitmap(response.getBitmap());
            } else if (HttpConfig.DEFAULT_IMAGE_ID != 0) {
                view.setImageResource(HttpConfig.DEFAULT_IMAGE_ID);
            }

        }
    }
}