package com.jiayantech.jyandroid.biz;

import android.net.Uri;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.jiayantech.jyandroid.model.web.BaseJsCall;
import com.jiayantech.library.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liangzili on 15/7/15.
 */
public class JsNativeBiz {
    public static final String ACTION_OPEN_COMMENT_PANEL = "openCommentPanel";
    public static final String ACTION_PLAY_IMAGE = "playImg";
    public static final String ACTION_SET_NAVIGATION_BAR_TITLE = "setNavigationBarTitle";
    public static final String ACTION_SCROLL_BOTTOM_TO_POS_Y = "scrollBottomToPosY";
    public static final String ACTION_TEST = "testForCallNativePleaseGiveBackWhatIHadSend";
    public static final String ACTION_HIDE_LOADING = "hideLoading";
    public static final String ACTION_APPLY_EVENT = "applymentEvent";
    public static final String ACTION_GET_USERINFO = "getUserInfo";
    public static final String ACTION_SHOW_USER_PROFILE_HEADER = "showUserProfileHeader";
    public static final String ACTION_POST_DETAIL_DATA = "postDetailData";

    public static final String JS_METHOD_G_renderPostComment = "G_renderPostComment";
    public static final String JS_METHOD_G_SHOW_ADD_POST_BUTTON = "G_showAddPostButton";
    public static final String JS_METHOD_G_SWITCH_LIKE = "G_switchLike";

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
        sb.append(params);
        sb.append(")");
        String result = "javascript:" + sb.toString();
        LogUtil.i("JsNativieBiz", "call js method: " + result);
        webView.loadUrl(result);
    }
}
