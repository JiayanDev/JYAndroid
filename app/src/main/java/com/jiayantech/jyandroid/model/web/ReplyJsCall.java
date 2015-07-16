package com.jiayantech.jyandroid.model.web;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by liangzili on 15/7/15.
 */
public class ReplyJsCall extends BaseJsCall<ReplyJsCall.ReplyData>{
    private static Gson sGson = new Gson();

    public static class ReplyData{
        public long subjectId;
        public String subject;
        public long toUserId;
        public String toUserName;

//        public String toString(){
//            return sGson.toJson(this).replace("\"", "\'");
//        }
    }
}
