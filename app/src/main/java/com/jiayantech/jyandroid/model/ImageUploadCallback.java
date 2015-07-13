package com.jiayantech.jyandroid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liangzili on 15/7/13.
 */
public class ImageUploadCallback {
    public int code;
    public String message;
    public String url;
    public long time;

    @SerializedName("image-width")
    public int image_width;

    @SerializedName("image-height")
    public int image_height;

    @SerializedName("image-frames")
    public int image_frames;

    @SerializedName("image-type")
    public String image_type;

    public String sign;
}
