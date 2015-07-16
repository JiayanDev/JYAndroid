package com.jiayantech.jyandroid.biz;

import android.net.Uri;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.jyandroid.model.web.ReplyJsCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by liangzili on 15/7/15.
 */
public class JsNativeBiz {
    public static final String ACTION_OPEN_COMMENT_PANEL = "openCommentPanel";
    public static final String ACTION_PLAY_IMAGE = "viewImage";
    public static final String ACTION_TEST = "testForCallNativePleaseGiveBackWhatIHadSend";

    public static final String JS_METHOD_G_renderPostComment = "G_renderPostComment";

    private static Gson sGson = new Gson();

    /**
     * 将Url中的option转换为自定义的对象
     * @param url 点击的Js Url
     * @param classType 要转换成Json的classType
     * @param <T> 返回的类型
     * @return
     */
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

    /**
     * 获取该Url的action
     * @param url
     * @return
     */
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


    /**
     * 调用js方法
     * @param method Js方法名
     * @param params Js方法参数
     * @param webView 调用Js方法的WebView
     */
    public static void callJsMethod(String method, String params, WebView webView) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("(");
//        if (params != null) {
//            for (String param : params) {
//                sb.append("\"");
//                sb.append(param);
//                sb.append("\",");
//            }
//            sb.deleteCharAt(sb.length() - dfasdfa
//        }
        sb.append(params);
        sb.append(")");

        webView.loadUrl("javascript:" + sb.toString());
    }
}
