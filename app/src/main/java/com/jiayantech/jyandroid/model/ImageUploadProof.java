package com.jiayantech.jyandroid.model;

import com.jiayantech.library.base.BaseModel;

/**
 * Created by liangzili on 15/7/10.
 */
public class ImageUploadProof {
    public String policy;
    public String path;
    public long expiration;
    public String signature;

    public boolean isExpired(){
        return expiration >= System.currentTimeMillis();
    }
}
