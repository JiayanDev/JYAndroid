package com.jiayantech.library.http.imageupload;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by liangzili on 15/6/30.
 */
public class PostUploadRequest2 extends JsonObjectRequest {
    private Response.Listener<JSONObject> mListener;
    private String BOUNDARY = "-------JIAYAN-----------2015-06-30";
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    private FormImage mFormImage;


    public PostUploadRequest2(int method, String url, FormImage formImage,
                             Response.ErrorListener errListener, Response.Listener<JSONObject> listener) {
        super(Method.POST, url, listener, errListener);
        mListener = listener;
        setShouldCache(false);
        mFormImage = formImage;
        setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String mString = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));

            LogUtil.i("PostUploadRequest", mString);
            JSONObject json = new JSONObject(mString);
            return Response.success(json, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError());
        }
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        mListener.onResponse(jsonObject);
    }

    @Override
    public byte[] getBody()  {
        if(mFormImage == null){
            return super.getBody();
        }
        LogUtil.i("PostUploadRequest", Thread.currentThread().getName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuffer sb = new StringBuffer();

        sb.append("--" + BOUNDARY);
        sb.append("\r\n");

        sb.append("Content-Disposition: form-data;");
        sb.append(" name=\"");
        sb.append(mFormImage.getName());
        sb.append("\"");
        sb.append("; filename=\"");
        sb.append(mFormImage.getFileName());
        sb.append("\"");
        sb.append("\r\n");

        sb.append("Content-Type: ");
        sb.append(mFormImage.getMime());
        sb.append("\r\n");

        sb.append("\r\n");

        try{
            baos.write(sb.toString().getBytes("utf-8"));
            baos.write(mFormImage.getValue());
            baos.write("\r\n".getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String endLine = "--" + BOUNDARY + "--" + "\r\n";
        try{
            baos.write(endLine.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY;
    }
}
