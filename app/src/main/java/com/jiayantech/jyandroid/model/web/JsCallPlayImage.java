package com.jiayantech.jyandroid.model.web;

import java.util.ArrayList;

/**
 * Created by liangzili on 15/7/20.
 */
public class JsCallPlayImage extends BaseJsCall<JsCallPlayImage.ImageData>{
    public static class ImageData{
        public int defaultIndex;
        public ArrayList<String> imgList;
    }
}
