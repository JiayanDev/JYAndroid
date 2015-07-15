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
public class JsNativeParser {
    private static Gson sGson = new Gson();

    public static BaseJsCall parse(String url){
        String urlDecode = Uri.decode(url);
        String jsonString = urlDecode.substring(urlDecode.indexOf("{"), urlDecode.lastIndexOf("}") + 1);
        jsonString = jsonString.replace("\"", "\'");
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            switch(jsonObject.getString("action")){
                case "testForCallNativePleaseGiveBackWhatIHadSend":
                    ReplyJsCall jsCall  = sGson.fromJson(jsonObject.toString(), ReplyJsCall.class);
                    return jsCall;
                default:
                    return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
