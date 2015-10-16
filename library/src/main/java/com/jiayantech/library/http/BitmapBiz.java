package com.jiayantech.library.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.utils.LogUtil;

import java.io.File;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
    public static void display(final ImageView imageView, final String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(HttpConfig.ERROR_IMAGE_ID);
            return;
        }

        final WeakReference<ImageView> imageRef = new WeakReference<>(imageView);
        Observable.create(new Observable.OnSubscribe<ImageSize>() {
            @Override
            public void call(final Subscriber<? super ImageSize> subscriber) {
                imageView.getViewTreeObserver().addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        int width;
                        int height;
                        if (imageRef != null) ;
                        ImageView image = imageRef.get();
                        image.getViewTreeObserver().removeOnPreDrawListener(this);
                        width = image.getMeasuredWidth();
                        height = image.getMeasuredHeight();
                        ImageSize size = new ImageSize(width, height);
                        subscriber.onNext(size);
                        subscriber.onCompleted();
                        return true;
                    }
                });
            }
        }).subscribe(new Action1<ImageSize>() {
            @Override
            public void call(ImageSize imageSize) {
                if(imageRef != null){
                    ImageView image = imageRef.get();
                    sImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView,
                                    HttpConfig.DEFAULT_IMAGE_ID, HttpConfig.ERROR_IMAGE_ID),
                            //Specify width & height of the bitmap to be scaled down when the image is downloaded.
                            imageSize.width, imageSize.height);
                }
            }
        });

////
//        imageView.getViewTreeObserver().addOnPreDrawListener(
//                new ViewTreeObserver.OnPreDrawListener() {
//                    @Override
//                    public boolean onPreDraw() {
//                        int width;
//                        int height;
//                        if (imageRef != null) ;
//                        ImageView image = imageRef.get();
//                        image.getViewTreeObserver().removeOnPreDrawListener(this);
//                        width = image.getMeasuredWidth();
//                        height = image.getMeasuredHeight();
//                        return true;
//                    }
//                });
//
//        sImageLoader.get(imageUrl, ImageLoader.getImageListener(imageView, HttpConfig.DEFAULT_IMAGE_ID, HttpConfig.ERROR_IMAGE_ID),
//                //Specify width & height of the bitmap to be scaled down when the image is downloaded.
//                DEFAULT_SIZE, DEFAULT_SIZE);
    }

    static class ImageSize {
        int width;
        int height;

        public ImageSize(int width, int height){
            this.width = width;
            this.height = height;
        }
    }

    /**
     * 加载图片
     *
     * @param imageUrl 图片url
     * @param listener 加载完成后的回调
     */
    public static void loadImage(String imageUrl, ImageLoader.ImageListener listener) {
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
    //获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
    private static final int max_cache_size = (int) (Runtime.getRuntime().maxMemory() / 8);
    //private static final int max_cache_size = 10 * 1024 * 1024;
    /**
     * Initialise Volley Request Queue. *
     */
    private static final RequestQueue sVolleyQueue = newRequestQueue(BaseApplication.getContext());
    private static final BitmapLruCache sBitmapLruCache = new BitmapLruCache(max_cache_size);
    private static final ImageLoader sImageLoader = new ImageLoader(sVolleyQueue, sBitmapLruCache);

    private static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public BitmapLruCache(int maxSize) {
            super(maxSize);
            //or setLimit(Runtime.getRuntime().maxMemory()/4);
        }


        @Override
        protected int sizeOf(String key, Bitmap value) {
            int size = value.getRowBytes() * value.getHeight();
            LogUtil.i("BitmapBiz", key + " size is " + size);
            return value.getRowBytes() * value.getHeight();
        }


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

    private static final String DEFAULT_CACHE_DIR = "image";
    /**
     * Default maximum disk usage in bytes.
     */
    private static final int DEFAULT_DISK_USAGE_BYTES = 50 * 1024 * 1024;

    public static RequestQueue newRequestQueue(Context context) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        HttpStack stack;
        if (Build.VERSION.SDK_INT >= 9) {
            stack = new HurlStack();
        } else {
            // Prior to Gingerbread, HttpUrlConnection was unreliable.
            // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
            stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
        }

        Network network = new BasicNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES), network);
        queue.start();

        return queue;
    }
}