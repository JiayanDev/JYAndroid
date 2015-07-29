package com.jiayantech.jyandroid.customwidget.webview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.web.BaseNativeResponse;
import com.jiayantech.jyandroid.model.web.PostComment;



/**
 * Created by liangzili on 15/7/7.
 */
public class PostDetailFragment extends WebViewFragment {


    private View mBottomView;
    private Button mContent;

    public static PostDetailFragment newInstance(long id, long userId, String userName, String type) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putLong(WebViewFragment.EXTRA_ID, id);
        args.putLong(WebViewFragment.EXTRA_USER_ID, userId);
        args.putString(WebViewFragment.EXTRA_USERNAME, userName);
        args.putString(WebViewFragment.EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {

        mBottomView = inflater.inflate(R.layout.layout_post_detail_bottom, null);
        ImageButton sendButton = (ImageButton) mBottomView.findViewById(R.id.button_send);
        mContent = (Button) mBottomView.findViewById(R.id.edit_comment);
        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment fragment = CommentFragment.newInstance(mId, mType);
                fragment.setTargetFragment(PostDetailFragment.this, REQUEST_CODE_COMMENT);
                fragment.show(getActivity().getSupportFragmentManager(), "comment");
            }
        });

        return mBottomView;
    }

    @Override
    protected BaseWebChromeClient onSetWebChromeClient() {
        return new BaseWebChromeClient();
    }

    /**
     * 评论完后的回调
     *
     * @param postComment
     */
    public void onCommentFinish(PostComment postComment) {
        //if(getActivity().
        postComment.userId = AppInitManger.getUserId();
        postComment.userName = AppInitManger.getUserName();

        //如果评论的是评论，在跳入CommentFragment时会带入要评论的该条评论的参数，并会返回来
        //如果toUserId为0， 则表示未有参数传入，将要回复的人设置为该post的作者
        if (postComment.toUserId == -1 || postComment.toUserId == 0) {
            postComment.toUserId = mUserId;
            postComment.toUserName = mUserName;
        }

        BaseNativeResponse<PostComment> comment = new BaseNativeResponse<>();
        comment.code = 0;
        comment.msg = "ok";
        comment.data = postComment;

        String result = comment.toString();
        callJsMethod(JsNativeBiz.JS_METHOD_G_renderPostComment, result);
    }

}
