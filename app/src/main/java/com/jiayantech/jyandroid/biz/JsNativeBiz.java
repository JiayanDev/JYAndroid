package com.jiayantech.jyandroid.biz;

import android.net.Uri;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.jyandroid.model.web.ReplyJsCall;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangzili on 15/7/15.
 */
public class JsNativeBiz {
    public static final String ACTION_OPEN_COMMENT_PANEL = "openCommentPanel";
    public static final String ACTION_TEST = "testForCallNativePleaseGiveBackWhatIHadSend";

    private static Gson sGson = new Gson();

    public static <T> T parse(String url, Class<? extends BaseJsCall> classType) {
        String urlDecode = Uri.decode(url);
        String jsonString = urlDecode.substring(urlDecode.indexOf("{"), urlDecode.lastIndexOf("}") + 1);
        jsonString = jsonString.replace("\"", "\'");
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            T jsCall = (T) sGson.fromJson(jsonObject.toString(), classType);
            return jsCall;
        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static String getJsAction(String url) {
        String urlDecode = Uri.decode(url);
        String jsonString = urlDecode.substring(urlDecode.indexOf("{"), urlDecode.lastIndexOf("}") + 1);
        jsonString = jsonString.replace("\"", "\'");
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.getString("action");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
