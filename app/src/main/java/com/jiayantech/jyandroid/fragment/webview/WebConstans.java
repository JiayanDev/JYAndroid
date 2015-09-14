package com.jiayantech.jyandroid.fragment.webview;

import com.jiayantech.library.comm.Property;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebConstans {
    /* webview显示内容的类型 */
    public static final String BASE_URL = Property.getProperty("html.url");

    public interface Type {
        String TYPE_EVENT = "event";
        String TYPE_TOPIC = "topic";
        String TYPE_DIARY = "diary";
        String TYPE_PERSONAL_PAGE = "timeline";
        String TYPE_EVENT_INTRO = "eventintro";
        String TYPE_HELP = "help";
    }

    public interface Action {
        String PREFIX = "/html";
        String ACTION_DIARY = PREFIX + "/diary.html";
        String ACTION_TOPIC = PREFIX + "/topic.html";
        String ACTION_EVENT = PREFIX + "/eventdetail.html";
        String ACTION_PERSONAL_PAGE = PREFIX + "/timeline.html";
        String ACTION_EVENT_INTRO = PREFIX + "/eventintro.html";
        String ACTION_HELP = PREFIX + "aboutmlts.html";
    }

//    public interface Path {
//        String PREFIX = "/html";
//        String PATH_TOPIC = PREFIX + "/topic.html";
//        String PATH_DIARY = PREFIX + "/diary.html";
//        String TYPE_HELP = "help";
//    }
}
