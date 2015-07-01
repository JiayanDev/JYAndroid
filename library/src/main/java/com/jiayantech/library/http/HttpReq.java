package com.jiayantech.library.http;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.jiayantech.library.base.BaseApplication;
import com.jiayantech.library.comm.TokenManager;
import com.jiayantech.library.comm.imageupload.FormImage;
import com.jiayantech.library.comm.imageupload.PostUploadRequest;
import com.jiayantech.library.utils.LogUtil;

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

    /**
     * @param action
     * @param params
     * @param l
     */
    public static void post(String action, Map<String, String> params, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, null, l);
    }

    /**
     * @param action
     * @param params
     * @param classType
     * @param l
     */
    public static void post(String action, Map<String, String> params, Type classType, ResponseListener<?> l) {
        request(Request.Method.POST, action, params, classType, l);
    }

    /**
     * @param action
     * @param params
     * @param l
     */
    public static void get(String action, Map<String, String> params, ResponseListener<?> l) {
        request(Request.Method.GET, action, params, null, l);
    }

    /**
     * @param action
     * @param params
     * @param classType
     * @param l
     */
    public static void get(String action, Map<String, String> params, Type classType, ResponseListener<?> l) {
        request(Request.Method.GET, action, params, classType, l);
    }

    public static Map<String, String> getInitParams(String key, String value) {
        Map<String, String> params = new HashMap<>();
        params.put(key, value);
        return params;
    }

    ///////////////////////////////////////private static class and method
    /**
     * Initialise Volley Request Queue.
     */
    private static final RequestQueue sVolleyQueue = Volley.newRequestQueue(BaseApplication.getContext());

    /**
     * @param method
     * @param action
     * @param params
     * @param classType
     * @param l
     */
    public static void request(int method, String action, Map<String, String> params, Type classType, ResponseListener<?> l) {
        Uri.Builder builderAction = Uri.parse(HttpConfig.BASE_URL + action).buildUpon();
        //builder.appendQueryParameter("time", System.currentTimeMillis() + "");
        HttpReq request = new HttpReq<>(method, builderAction.toString(), params, classType, l, new ErrorListener(l));
        request.setShouldCache(false);
        sVolleyQueue.add(request);
    }

    /**
     *
     * @param bitmap
     * @param fileName
     * @param listener
     */
    public static void uploadImage(Bitmap bitmap, String fileName, ResponseListener listener){
        FormImage formImage = new FormImage(bitmap, fileName);
        uploadImage(formImage, listener);
    }

    /**
     *
     * @param filePath
     * @param listener
     */
    public static void uploadImage(String filePath, ResponseListener listener){
        FormImage formImage = new FormImage(filePath);
        uploadImage(formImage, listener);
    }

    private static void uploadImage(FormImage formImage, ResponseListener listener){
        Request request = new PostUploadRequest(Request.Method.POST, null, formImage,
                new ErrorListener(listener), listener);
        sVolleyQueue.add(request);
    }

    private static class ErrorListener implements Response.ErrorListener {
        ResponseListener mResponseListener;

        ErrorListener(ResponseListener<?> l) {
            mResponseListener = l;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mResponseListener.onErrorResponse(error);
            if (error instanceof NetworkError) {
            } else if (error instanceof ServerError) {
            } else if (error instanceof AuthFailureError) {
            } else if (error instanceof ParseError) {
            } else if (error instanceof NoConnectionError) {
            } else if (error instanceof TimeoutError) {
            }
            Toast.makeText(BaseApplication.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    /**
     * request params
     */
    private final Map<String, String> mParams;
    /**
     * request header
     */
    private final Map<String, String> mHeaders = new HashMap<>();
    /**
     * success listener
     */
    private final Response.Listener<T> mListener;

    private Type mClassType;
    private final Gson mGson = new Gson();

    private HttpReq(int method, String url, Map<String, String> params, Type classType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mParams = params;
        mHeaders.put(TokenManager.KEY_TOKEN, TokenManager.getToken());
        mClassType = classType == null ? getClassType(listener, 0) : classType;
        mListener = listener;

        LogUtil.i(TAG, "token: " + TokenManager.getToken());
        LogUtil.i(TAG, url + (params == null ? "" : params.toString()));
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtil.i(TAG, jsonString);
            T parsedGSON = mGson.fromJson(jsonString, mClassType);
            return Response.success(parsedGSON,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            return Response.error(new ParseError(je));
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
            return encodeParameters(params, getParamsEncoding());
        }
        return null;
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
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
            return encoded.getBytes(paramsEncoding);
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

