package com.jiayantech.jyandroid.customwidget.webview;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PhotosActivity;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.manager.UserManger;
import com.jiayantech.jyandroid.model.Photo;
import com.jiayantech.jyandroid.model.web.BaseNativeResponse;
import com.jiayantech.jyandroid.model.web.PostComment;
import com.jiayantech.jyandroid.model.web.JsCallReply;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by liangzili on 15/7/7.
 */
public class PostDetailFragment extends WebViewFragment {

    public static final int REQUEST_CODE_COMMENT = 1;

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
        //EventBus.getDefault().register(this);

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

    @Override
    protected JavascriptInterface onAddJavascriptInterface() {
        return new JavascriptInterface() {
            public void setVisibility() {
                mBottomView.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    protected WebViewClient onSetWebViewClient() {
        return new BaseWebViewClient(getActivity()) {
            @Override
            protected void onJsCallNativeOpenCommentPanel(JsCallReply call) {

                CommentFragment fragment = CommentFragment.newInstance(call.data.subjectId,
                        call.data.subject, call.data.toUserId, call.data.toUserName);
                fragment.setTargetFragment(PostDetailFragment.this, REQUEST_CODE_COMMENT);
                fragment.show(getActivity().getSupportFragmentManager(), "comment");
            }

            @Override
            protected void onJsCallNativeTest(JsCallReply call) {
                super.onJsCallNativeTest(call);
                List<String> params = new ArrayList<>();
                params.add(call.data.toString());
                params.add("0");
                params.add("ok");
                UIUtil.showSoftKeyBoard(getActivity(), mContent);
            }

            @Override
            protected void onJsCallNativeNavigateToDiaryHeader(long id) {
                super.onJsCallNativeNavigateToDiaryHeader(id);
                navigate(id, WebViewFragment.TYPE_DIARY_HEADER, PostDetailActivity.class);
            }

            @Override
            protected void onJsCallNativePlayImage(int index, ArrayList<String> images) {
               PhotosActivity.start(getActivity(),"",images, index);
            }

            @Override
            protected void onJsCallNativeNavigateToDiary(long id) {
                super.onJsCallNativeNavigateToDiary(id);
                navigate(id, WebViewFragment.TYPE_DIARY, PostDetailActivity.class);
            }

            @Override
            protected void onJsCallNativeSetTitle(String title) {
                getActivity().setTitle(title);
            }

            @Override
            protected void onJscallNativeScroll(int posY) {
                scrollToY(posY);
            }

            private void navigate(long id, String type, Class<? extends BaseActivity> clazz) {
                Intent intent = new Intent(getActivity(), clazz);
                intent.putExtra(WebViewFragment.EXTRA_ID, id);
                intent.putExtra(WebViewFragment.EXTRA_TYPE, type);
                intent.putExtra(WebViewFragment.EXTRA_USER_ID, mUserId);
                intent.putExtra(WebViewFragment.EXTRA_USERNAME, mUserName);
                getActivity().startActivity(intent);
            }
        };
    }

    /**
     * 评论完后的回调
     *
     * @param postComment
     */
    public void onCommentFinish(PostComment postComment) {
        //if(getActivity().
        postComment.userId = UserManger.getUserId();
        postComment.userName = UserManger.getUserName();

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
