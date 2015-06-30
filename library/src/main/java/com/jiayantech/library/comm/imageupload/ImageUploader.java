package com.jiayantech.library.comm.imageupload;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by liangzili on 15/6/30.
 */
public class ImageUploader {
    public static final String TAG = "ImageUploader";

    private Handler mHandler;
    private ExecutorService mUploadThreadPool;

    private static ImageUploader sImageUploader;
    private ImageUploader(){

    }

    public static ImageUploader getInstance(){
        if(sImageUploader == null){
            Log.d(TAG, "First Init ImageUploader");
            sImageUploader = new ImageUploader();
        }
        return sImageUploader;
    }

    public void startUpload(int code, String localPath, OnUpLoadCompleteListener listener){
        Log.d(TAG, "start upload, imagePath: " + localPath + " code is " + code);
        UploadImageThread thread = new UploadImageThread(code, localPath, listener);
        mUploadThreadPool.execute(thread);
    }

    public void init(){
        mHandler = new Handler(Looper.getMainLooper());
        mUploadThreadPool = Executors.newCachedThreadPool();
    }

    private class UploadImageThread extends Thread{
        private int mImageCode;
        private String mLocalPath;
        private OnUpLoadCompleteListener mOnUpLoadCompleteListener;

        public UploadImageThread(int code, String localPath, OnUpLoadCompleteListener listener){
            mImageCode = code;
            mLocalPath = localPath;
            mOnUpLoadCompleteListener = listener;
        }

        @Override
        public void run() {
            Log.d(TAG, "I am uploading, the code is " + mImageCode);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mOnUpLoadCompleteListener.
                            onUploadSucess(mImageCode, mLocalPath, "http://www.baidu.com");
                }
            });
        }
    }
}
