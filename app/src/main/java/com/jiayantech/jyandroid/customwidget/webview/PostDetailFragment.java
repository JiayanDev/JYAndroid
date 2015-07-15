package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.model.web.ReplyJsCall;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liangzili on 15/7/7.
 */
public class PostDetailFragment extends WebViewFragment{

    public static PostDetailFragment newInstance(long id, String type){
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putString(WebViewFragment.EXTRA_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    View mBottomView;
    EditText mContent;

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        mBottomView = inflater.inflate(R.layout.layout_detail_bottom, null);
        ImageButton sendButton = (ImageButton)mBottomView.findViewById(R.id.button_send);
        mContent = (EditText)mBottomView.findViewById(R.id.edit_comment);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mContent.getText() && (!"".equals(mContent.getText().toString()))){
                    PostBiz.comment(String.valueOf(mId), mType, mContent.getText().toString(),
                            new ResponseListener<AppResponse<BaseModel>>() {
                        @Override
                        public void onResponse(AppResponse<BaseModel> appResponse) {
                            if(appResponse.code == 0) {
                                ToastUtil.showMessage(getActivity(), "评论成功啦" + appResponse.data.id);
                                mContent.clearComposingText();
                                mContent.clearFocus();
                                mContent.setText("");
                            }
                        }
                    });
                    UIUtil.hideSoftKeyboard(getActivity());
                }else{
                    ToastUtil.showMessage(getActivity(), "评论不能为空");
                }
            }
        });

        return mBottomView;
    }

    @Override
    protected BaseWebChromeClient onSetWebChromeClient() {
        return new BaseWebChromeClient();
    }

    @Override
    protected JavascriptInterface onAddJavascriptInterface() {
        return new JavascriptInterface() {
            public void setVisibility(){
                mBottomView.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    protected WebViewClient onSetWebViewClient() {
        return new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("jiayan://js_call_native?")){
                    //ReplyJsCall jsCall = (ReplyJsCall) JsNativeBiz.parse(url);
                    String action = JsNativeBiz.getJsAction(url);
                    ToastUtil.showMessage(action);
                    switch (action){
                        case "testForCallNativePleaseGiveBackWhatIHadSend":
                            ReplyJsCall jsCall = JsNativeBiz.parse(url, ReplyJsCall.class);
                            List<String> params = new ArrayList<>();

                            params.add(jsCall.data.toString());
                            params.add("0");
                            params.add("ok");
                            //callJsMethod(jsCall.success, params);
                            UIUtil.showSoftKeyBoard(getActivity(),mContent);
                            break;
                        case JsNativeBiz.ACTION_OPEN_COMMENT_PANEL:
                            ReplyJsCall jsCall1 = JsNativeBiz.parse(url, ReplyJsCall.class);
                            break;
                    }

                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }
}
