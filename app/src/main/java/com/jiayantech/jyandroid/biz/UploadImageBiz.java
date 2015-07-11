package com.jiayantech.jyandroid.biz;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jiayantech.jyandroid.app.JYApplication;
import com.jiayantech.jyandroid.model.ImageUploadProof;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.http.imageupload.FormImage;
import com.jiayantech.library.http.imageupload.PostUploadRequest2;

import java.util.Map;

/**
 * Created by liangzili on 15/7/10.
 */
public class UploadImageBiz {
    public static final String TYPE_AVATAR = "avatar";
    public static final String TYPE_DIARY = "diary";
    public static final String TYPE_TOPIC = "topic";
    public static final String TYPE_EVENT = "event";

    public static final String ACTION_GET_PROOF = "uploader/sign";
    private static ImageUploadProof PROOF_AVATAR;
    private static ImageUploadProof PROOF_DIARY;
    private static ImageUploadProof PROOF_TOPIC;
    private static ImageUploadProof PROOF_EVENT;

    private static final RequestQueue sVolleyQueue = Volley.newRequestQueue(JYApplication.getContext());

    /**
     * @param bitmap
     * @param fileName
     * @param listener
     */
    public static void uploadImage(String type, Bitmap bitmap, String fileName,
                                   ResponseListener listener) {
        FormImage formImage = new FormImage(bitmap, fileName);
        uploadImage(type, formImage, listener);
    }

    /**
     * @param filePath
     * @param listener
     */
    public static void uploadImage(String type, String filePath, ResponseListener listener) {
        FormImage formImage = new FormImage(filePath);
        uploadImage(type, formImage, listener);
    }


    private static void uploadImage(String type, final FormImage formImage, final ResponseListener listener) {
        OnGetUploadProofListener uploadProofListener = new OnGetUploadProofListener() {
            @Override
            public void onGetUploadProof(ImageUploadProof proof) {
                Map<String, String> params = new ArrayMap<>();
                params.put("policy", proof.policy);
                params.put("signature", proof.signature);
                startUpload(formImage, listener, params);
            }
        };

        getImageUploadProof(type, uploadProofListener);
//        ImageUploadProof proof = getImageUploadProof(type);
//        if (proof != null) {
//            Map<String, String> params = new ArrayMap<>();
//            params.put("policy", proof.policy);
//            params.put("signature", proof.signature);
//            Request request = new PostUploadRequest2(Request.Method.POST,
//                    HttpConfig.IMAGE_UPLOAD_URL, formImage, params, null, listener);
//            sVolleyQueue.add(request);
//        } else {
//
    }

    private static void startUpload(FormImage formImage, ResponseListener listener,
                                    Map<String, String> params) {

        Request request = new PostUploadRequest2(Request.Method.POST,
                HttpConfig.IMAGE_UPLOAD_URL, formImage, params, null, listener);
        //sVolleyQueue.add(request);
        HttpReq.sVolleyQueue.add(request);

    }

    private static void getImageUploadProof(String type,
                                            final OnGetUploadProofListener listener) {
        ImageUploadProof proof = null;
        switch (type) {
            case TYPE_AVATAR:
                proof = PROOF_AVATAR;
                break;
            case TYPE_DIARY:
                proof = PROOF_DIARY;
                break;
            case TYPE_TOPIC:
                proof = PROOF_TOPIC;
                break;
            case TYPE_EVENT:
                proof = PROOF_EVENT;
        }
        if (null == proof || proof.isExpired()) {
            Map<String, String> params = new ArrayMap<>();
            params.put("mod", type);
            HttpReq.get(ACTION_GET_PROOF, params,
                    new ResponseListener<AppResponse<ImageUploadProof>>() {
                        @Override
                        public void onResponse(AppResponse<ImageUploadProof> imageUploadProofAppResponse) {
                            listener.onGetUploadProof(imageUploadProofAppResponse.data);
                        }
                    });
        } else {
            listener.onGetUploadProof(proof);
        }
    }

//    private static void requestImageUploadProof(
//            String type, ResponseListener<AppResponse<ImageUploadProof>> listener){
//        Map<String, String> params = new ArrayMap<>();
//        params.put("mod", type);
//        HttpReq.get(ACTION_GET_PROOF, params, listener);
//    }

    interface OnGetUploadProofListener {
        void onGetUploadProof(ImageUploadProof proof);
    }
}
