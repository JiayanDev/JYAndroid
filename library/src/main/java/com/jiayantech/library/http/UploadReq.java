package com.jiayantech.library.http;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.jiayantech.library.base.BaseApplication;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ByteArrayRequest override getBody() and getParams()
 *
 * @author steven pan
 */
public class UploadReq extends Request<NetworkResponse> {

    /**
     * Initialise Volley Request Queue.
     */
    private static final RequestQueue sVolleyQueue = Volley.newRequestQueue(BaseApplication.getContext());

    public static void request(RequestMap params, Listener<NetworkResponse> listener) {
        UploadReq request = new UploadReq(Method.POST, "http://m0.api.upyun.com/jiayanimg/", params, listener, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof HttpReq.MsgError) {
                } else if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                } else if (error instanceof AuthFailureError) {
                } else if (error instanceof ParseError) {
                } else if (error instanceof NoConnectionError) {
                } else if (error instanceof TimeoutError) {
                }
                Toast.makeText(BaseApplication.getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.setShouldCache(false);
        sVolleyQueue.add(request);
    }

    private final Listener<NetworkResponse> mListener;

    private Object mPostBody = null;

    private HttpEntity httpEntity = null;

    public UploadReq(int method, String url, RequestMap postBody, Listener<NetworkResponse> listener, ErrorListener errorListener) {
        this(method, url, (Object) postBody, listener, errorListener);
    }

    public UploadReq(int method, String url, Object postBody, Listener<NetworkResponse> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mPostBody = postBody;
        this.mListener = listener;

        if (this.mPostBody != null && this.mPostBody instanceof RequestMap) {// contains file
            this.httpEntity = ((RequestMap) this.mPostBody).getEntity();
        }
    }

    /**
     * mPostBody is null or Map<String, String>, then execute this method
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> getParams() throws AuthFailureError {
        if (this.httpEntity == null && this.mPostBody != null && this.mPostBody instanceof Map<?, ?>) {
            return ((Map<String, String>) this.mPostBody);//common Map<String, String>
        }
        return null;//process as json, xml or MultipartRequestParams
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (null == headers || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        return headers;
    }

    @Override
    public String getBodyContentType() {
        if (httpEntity != null) {
            return httpEntity.getContentType().getValue();
        }
        return null;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (this.mPostBody != null && this.mPostBody instanceof String) {//process as json or xml
            String postString = (String) mPostBody;
            if (postString.length() != 0) {
                try {
                    return postString.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        if (this.httpEntity != null) {//process as MultipartRequestParams
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                httpEntity.writeTo(baos);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return baos.toByteArray();
        }
        return super.getBody();// mPostBody is null or Map<String, String>
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        this.mListener.onResponse(response);
    }

}