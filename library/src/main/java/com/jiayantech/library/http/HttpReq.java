package com.jiayantech.library.http;

import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.comm.ConfigManager;
import com.jiayantech.library.comm.DataManager;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 健兴 on 2015/6/26.
 *
 * @Description: http request
 * *
 * @Copyright: Copyright (c) 2015 Shenzhen Jiayan Tech Co., Ltd. Inc. All
 * rights reserved.
 */
public class HttpReq<T> extends Request<T> {
    private static final String TAG = HttpReq.class.getSimpleName();
    private static final int PAGE_NUM = 10;
    private static final int MAX_CACHE_NUM = 40;

    private static final int CODE_SUCCESS = 0;
    private static final int CODE_OVERDUE = -36;


    public static void post(String action, Map<String, String> params, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, null, false, false, null, l);
    }

    public static void post(String action, Map<String, String> params, Type classType, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, null, false, false, classType, l);
    }

    public static void post(String action, Map<String, String> params, boolean toLoad, boolean toSave, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, null, toLoad, toSave, null, l);
    }

    public static void post(String action, Map<String, String> params, boolean toLoad, boolean toSave, Type classType, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, null, toLoad, toSave, classType, l);
    }

    public static void get(String action, Map<String, String> params, ResponseListener<?> l) {
        request(Request.Method.GET, action, params, null, false, false, null, l);
    }

    public static void get(String action, Map<String, String> params, boolean toLoad, boolean toSave, ResponseListener<?> l) {
        request(Request.Method.GET, action, params, null, toLoad, toSave, null, l);
    }

    public static void get(String action, Map<String, String> params, Map<String, String> page, boolean toLoad, boolean toSave, Type classType, ResponseListener<?> l) {
        request(Request.Method.GET, action, params, page, toLoad, toSave, classType, l);
    }

    public static Map<String, String> getInitParams(String key, String value) {
        Map<String, String> params = new HashMap<>();
        params.put(key, value);
        return params;
    }

    public static void putParams(Map<String, String> params, String key, Object value) {
        if (value != null) {
            params.put(key, value.toString());
        }
    }

    public static void putParams(Map<String, String> params, String key, Object value, Object error) {
        if (value != null && !error.equals(value)) {
            params.put(key, value.toString());
        }
    }

    public static Map<String, String> putParams(Map<String, String> params, Map<String, String> other) {
        if (params == null) {
            return other;
        }
        if (other != null) {
            params.putAll(other);
        }
        return params;
    }

    ///////////////////////////////////////private static class and method
    /**
     * Initialise Volley Request Queue.
     */
    public static final RequestQueue sVolleyQueue = Volley.newRequestQueue(BaseApplication.getContext());

    /**
     * @param method
     * @param action
     * @param params
     * @param page
     * @param toLoad
     * @param toSave
     * @param classType
     * @param l
     */
    public static void request(int method, String action, Map<String, String> params, Map<String, String> page, boolean toLoad, boolean toSave, Type classType, ResponseListener<?> l) {
        //method = Request.Method.GET;
//        if (params == null) {
//            params = getInitParams("daddy", "8");
//        } else {
//            params.put("daddy", "8");
//        }

        Uri.Builder builderAction = Uri.parse(HttpConfig.BASE_URL + action).buildUpon();
        //builder.appendQueryParameter("time", System.currentTimeMillis() + "");
        new HttpReq<>(method, builderAction.toString(), params, page, toLoad, toSave, classType, l);
    }

    public static void _request(int method, String url, Map<String, String> params, Map<String, String> page, boolean toLoad, boolean toSave, Type classType, ResponseListener<?> l) {
        new HttpReq<>(method, url, params, page, toLoad, toSave, classType, l);
    }

    public static void request(HttpReq httpReq) {
        new HttpReq<>(httpReq);
    }

    public static <T> T getCache(String action, Map<String, String> params, Class<T> cls) {
        String value = DataManager.get(getUrlKey(action, params));
        if (TextUtils.isEmpty(value)) return null;
        return new Gson().fromJson(value, cls);
    }

    private static String getUrlKey(String action, Map<String, String> params) {
        Uri.Builder builderAction = Uri.parse(HttpConfig.BASE_URL + action).buildUpon();
        return params == null ? builderAction.toString() : builderAction.toString() + params.toString();
    }

    private static class ErrorListener implements Response.ErrorListener {
        ResponseListener mResponseListener;

        ErrorListener(ResponseListener<?> l) {
            mResponseListener = l;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mResponseListener.onErrorResponse(error);
            if (error instanceof OverdueError) {
                OverdueError overdueError = (OverdueError) error;
                if (overdueError.mHttpReq != null) {
                    BaseApplication.getContext().onOverdue(overdueError.mHttpReq);
                }
                return;
//            } else if (error instanceof MsgError) {
//            } else if (error instanceof NetworkError) {
//            } else if (error instanceof ServerError) {
//            } else if (error instanceof AuthFailureError) {
//            } else if (error instanceof ParseError) {
//            } else if (error instanceof NoConnectionError) {
//            } else if (error instanceof TimeoutError) {
            }
            Toast.makeText(BaseApplication.getContext(), toErrorString(error), Toast.LENGTH_SHORT).show();
        }
    }

    private static String toErrorString(VolleyError error) {
        String msg = "";
//        String name = error.getClass().getSimpleName();
//        if (msg == null) {
//            return name;
//        }
//        return name + ": " + msg;
        if(error instanceof MsgError){
            msg = ((MsgError) error).getSimpleMsg();
        }else{
            msg = error.getLocalizedMessage();
        }
        return msg;
    }

    public static class MsgError extends VolleyError {
        public int code;
        public String msg;

        public MsgError(int code, String msg) {
            super(code + ": " + msg);
            this.code = code;
            this.msg = msg;
        }

        public String getSimpleMsg(){
            return msg;
        }
    }

    public static class OverdueError extends MsgError {
        private HttpReq mHttpReq;

        public OverdueError(int code, String msg, HttpReq httpReq) {
            super(code, msg);
            mHttpReq = httpReq;
        }

        public void clear() {
            mHttpReq = null;
        }
    }

    ///////////////////////////////////////class impl
    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";
    /**
     * Content type for request.
     */
    //private static final String PROTOCOL_CONTENT_TYPE =
    //        String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    private static final String PROTOCOL_CONTENT_TYPE =
            "application/x-www-form-urlencoded";

    private int mMethod;
    private final String mUrl;
    /**
     * request params
     */
    private final Map<String, String> mParams;
    private final Map<String, String> mPage;
    private final boolean mToSave;
    private final boolean mToLoad;

    /**
     * request header
     */
    private final Map<String, String> mHeaders = new HashMap<>();
    /**
     * success listener
     */
    private final ResponseListener<T> mListener;

    private Type mClassType;
    //private final Gson mGson = new Gson();
    private static final Gson mGson = GsonUtils.build();

    private String mUrlKey;
    private T mCache;


    private HttpReq(int method, String url, Map<String, String> params, Map<String, String> page, boolean toLoad, boolean toSave, Type classType, final ResponseListener<T> listener) {
        super(method, (method == Request.Method.GET && params != null || page != null) ?
                url + "?" + encodeParameters(putParams(page, params), PROTOCOL_CHARSET) : url, new ErrorListener(listener));
        mMethod = method;
        mUrl = url;
        mParams = params;
        mPage = page;
        mToLoad = toLoad;
        mToSave = toSave;
        mClassType = classType == null ? getClassType(listener, 0) : classType;
        mListener = listener;

        mHeaders.put(ConfigManager.KEY_TOKEN, ConfigManager.getToken());
        setShouldCache(false);

        mUrlKey = url + (params == null ? "" : params.toString()) + (page == null ? "" : page.toString());
        LogUtil.i(TAG, ConfigManager.KEY_TOKEN + ": " + ConfigManager.getToken());
        LogUtil.i(TAG, mUrlKey);

        //new GsonBuilder().
        if (toLoad) {
            /**
             * 加载缓存，加载后，调用http请求
             */
            new AsyncTask<Void, Void, T>() {
                @Override
                protected T doInBackground(Void... params) {
                    return loadCache();
                }

                @Override
                protected void onPostExecute(T t) {
                    mCache = t;
                    if (t != null) listener.onLoadResponse(t);
                    if (!isCancelled()) sVolleyQueue.add(HttpReq.this);
                }
            }.execute((Void) null);
        } else {
            sVolleyQueue.add(HttpReq.this);
        }
    }

    private HttpReq(HttpReq httpReq) {
        this(httpReq.mMethod, httpReq.mUrl, httpReq.mParams, httpReq.mPage, httpReq.mToLoad, httpReq.mToSave, httpReq.mClassType, httpReq.mListener);
    }

    private T loadCache() {
        String value = DataManager.get(mUrlKey);
        if (TextUtils.isEmpty(value)) return null;
        try {
            return mGson.fromJson(value, mClassType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存缓存，
     * ///////////////保存最大数目为 MAX_CACHE_NUM 的缓存数据
     *
     * @param jsonString
     * @param parsedGSON
     */
    private void saveCache(String jsonString, T parsedGSON) {
        AppResponse appResponse = (AppResponse) parsedGSON;
//        if (!mToLoad && appResponse.data instanceof ArrayList) {
//            if (mCache == null) {
//                mCache = loadCache();
//            }
//            if (mCache != null) {
//                ArrayList list = new ArrayList<>((ArrayList) appResponse.data);
//                int newSize = list.size();
//                if (newSize > 0 && newSize < PAGE_NUM) {
//                    AppResponse cache = (AppResponse) mCache;
//                    ArrayList cacheList = (ArrayList) cache.data;
//                    int cacheSize = cacheList.size();
//                    int size = newSize + cacheSize;
//                    if (size > MAX_CACHE_NUM) {
//                        list.addAll(cacheList.subList(0, MAX_CACHE_NUM / 2 - newSize));
//                        cache.data = list;
//                    } else {
//                        cacheList.addAll(0, list);
//                    }
//                    DataManager.put(mUrlKey, mGson.toJson(cache));
//                    return;
//                }
//            }
//        }
        DataManager.put(mUrlKey, jsonString);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtil.i(TAG, jsonString);
            BaseAppResponse baseAppResponse = mGson.fromJson(jsonString, BaseAppResponse.class);
            //if (mUrl.contains("quick_login")) baseAppResponse.code = CODE_OVERDUE;
            if (baseAppResponse.code == CODE_SUCCESS) {
                T parsedGSON = mGson.fromJson(jsonString, mClassType);
                if (mToSave) saveCache(jsonString, parsedGSON);
                return Response.success(parsedGSON,
                        HttpHeaderParser.parseCacheHeaders(response));
            } else if (baseAppResponse.code == CODE_OVERDUE) {
                return Response.error(new OverdueError(baseAppResponse.code, baseAppResponse.msg, this));
            } else {
                return Response.error(new MsgError(baseAppResponse.code, baseAppResponse.msg));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params != null && params.size() > 0) {
            return encodeParameters(params, getParamsEncoding()).getBytes();
        }
        return null;
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            String encoded;
            if (encodedParams.length() > 0) {
                encoded = encodedParams.substring(0, encodedParams.length() - 1);
            } else {
                encoded = encodedParams.toString();
            }
            return encoded;
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    /**
     * @return class type
     */
    public static Type getClassType(Object obj, int index) {
        Type t = obj.getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            return p[index];
        }
        return null;
    }
}