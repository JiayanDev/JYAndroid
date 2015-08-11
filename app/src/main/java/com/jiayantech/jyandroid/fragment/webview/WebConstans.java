package com.jiayantech.jyandroid.fragment.webview;

import com.jiayantech.library.comm.Property;

/**
 * Created by liangzili on 15/8/10.
 */
public class WebConstans {
    /* webview显示内容的类型 */
    public static final String BASE_URL = Property.getProperty("html.url");

    public interface Type{
        String TYPE_EVENT = "event";
        String TYPE_TOPIC = "topic";
        String TYPE_DIARY = "diary";
        String TYPE_PERSONAL_PAGE ="personal_page";
    }

    public interface Action{
        String ACTION_DIARY = "diary.html";
        String ACTION_TOPIC = "topic.html";
        String ACTION_EVENT = "eventdetail.html";
    }
}
