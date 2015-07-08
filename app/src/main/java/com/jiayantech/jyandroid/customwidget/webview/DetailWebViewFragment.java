package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;


/**
 * Created by liangzili on 15/7/7.
 */
public class DetailWebViewFragment extends WebViewFragment{

    public static DetailWebViewFragment newInstance(long id, String type){
        DetailWebViewFragment fragment = new DetailWebViewFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putString(WebViewFragment.EXTRA_TYPE, type);
        fragment.setArguments(args);

        return fragment;
    }

    View mBottomView;

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        mBottomView = inflater.inflate(R.layout.layout_detail_bottom, null);
        ImageButton sendButton = (ImageButton)mBottomView.findViewById(R.id.button_send);
        final EditText mContent = (EditText)mBottomView.findViewById(R.id.edit_comment);
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
}
