package com.jiayantech.jyandroid.model.web;

import java.util.ArrayList;

/**
 * Created by liangzili on 15/9/15.
 */
public class JsCallPostDetail extends BaseJsCall<JsCallPostDetail.JsPost> {
    public static class JsPost {
        public String userName;
        public int commentCount;
        public String province;
        public String city;
        public long userBirthday;
        public long userId;
        public long createTime;
        public int likeCount;
        public String content;
        public String role;
        public String avatar;
        public ArrayList<String> photoes;
        public String type;
        public long id;
        public boolean isLike;
        public String gender;
    }
}
