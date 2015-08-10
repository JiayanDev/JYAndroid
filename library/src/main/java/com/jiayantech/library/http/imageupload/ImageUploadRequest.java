package com.jiayantech.library.http.imageupload;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 图片上传的Request
 * Created by liangzili on 15/6/30.
 */
public class ImageUploadRequest<T> extends Request<T> {
    private Response.Listener mListener;
    private Map<String, String> mParams;
    private String BOUNDARY = "-----WebKitFormBoundaryBEW5au1hqfyoJyYz";

    private FormImage mFormImage;
    private final Gson mGson = new Gson();


    public ImageUploadRequest(int method, String url, FormImage formImage, Map<String, String> params,
                              Response.ErrorListener errListener, Response.Listener listener) {
        //super(method, url, listener, errListener);
        super(method, url, errListener);
        mListener = listener;
        mFormImage = formImage;
        mParams = params;

        setShouldCache(false);
        setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String mString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));

            LogUtil.i("ImageUploadRequest", mString);
            Type classType = HttpReq.getClassType(mListener, 0);
            //JSONObject json = new JSONObject(mString);
            T parsedGson = mGson.fromJson(mString, classType);

            return Response.success(parsedGson, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }
    }

    @Override
    protected void deliverResponse(T jsonObject) {
        mListener.onResponse(jsonObject);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public byte[] getBody() {
        StringBuilder sb = new StringBuilder();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //写入post表单参数
        sb.append(encodeParameters(mParams, getParamsEncoding()));
        sb.append("\r\n");
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");

        //写入图片二进制数据
        sb.append("Content-Disposition: form-data;");
        sb.append(" name=\"");
        sb.append(mFormImage.getName());
        sb.append("\"");
        sb.append("; filename=\"");
        sb.append(mFormImage.getFileName());
        sb.append("\";");

        sb.append("\r\n");

        sb.append("Content-Type: ");
        sb.append(mFormImage.getMime());
        sb.append("\r\n");

        sb.append("\r\n");

        try {
            baos.write(sb.toString().getBytes("utf-8"));
            baos.write(mFormImage.getValue());
            baos.write("\r\n".getBytes("utf-8"));
        }  catch (IOException e) {
            e.printStackTrace();
        }

        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try {
            baos.write(endLine.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    private String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() == null) {
                    continue;
                }
                encodedParams.append("--");
                encodedParams.append(BOUNDARY);
                encodedParams.append("\r\n");
                encodedParams.append("Content-Disposition: form-data; name=\"");
                encodedParams.append(entry.getKey());
                encodedParams.append("\"\r\n\r\n");
                //        + URLEncoder.encode(entry.getKey(), paramsEncoding) + "\"\r\n\r\n");
                encodedParams.append(entry.getValue());
                encodedParams.append("\n");

            }
            String encoded;
            if (encodedParams.length() > 0) {
                encoded = encodedParams.substring(0, encodedParams.length() - 1);
            } else {
                encoded = encodedParams.toString();
            }
            return encoded;
        }finally{

        }
    }

    @Override
    public String getBodyContentType() {
        String MULTIPART_FORM_DATA = "multipart/form-data";
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }

}
