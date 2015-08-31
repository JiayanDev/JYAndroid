package com.jiayantech.jyandroid.model.web;

/**
 * Created by liangzili on 15/8/6.
 */
public class JsCallApplyEvent extends BaseJsCall<JsCallApplyEvent.ApplyEvent>{
    public static class ApplyEvent{
        public long id;
        public EventInfo eventInfo;
    }

    public static class EventInfo{
        public long id;
        public String hospitalName;
        public String doctorName;
        public long beginTime;
        public CategoryId[] categoryIds;
        public AngelUserInfo angelUserInfo;
    }

    public static class CategoryId{
        public long id;
        public String name;
    }

    public static class AngelUserInfo{
        public long id;
        public String phone;
        public String nickname;
        public String avatar;
        public String name;
    }
}
