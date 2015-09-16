package com.jiayantech.jyandroid.fragment.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.reflect.TypeToken;
import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.manager.AppInitManger;
import com.jiayantech.jyandroid.model.web.BaseNativeResponse;
import com.jiayantech.jyandroid.model.web.JsCallPostDetail;
import com.jiayantech.jyandroid.model.web.JsCallReply;
import com.jiayantech.jyandroid.model.web.PostComment;
import com.jiayantech.jyandroid.model.web.SwitchLike;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.HttpReq;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.LogUtil;
import com.jiayantech.library.utils.ToastUtil;
import com.jiayantech.library.utils.UIUtil;

import java.util.Map;


/**
 * Created by liangzili on 15/7/7.
 */
public class PostDetailFragment extends WebViewFragment {
    private static final String TAG = "PostDetailFragment";

    private View mBottomView;
    private EditText mContentEdit;
    private ImageButton mSendBtn;
    private ImageButton mLikeBtn;

    private long mReplyId = -1;
    private String mReplyTo;
    private String mReplyType;
    private PostComment mPostComment;
    private boolean hasLike;

    public static PostDetailFragment newInstance(long id, String type) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);
        args.putString(EXTRA_TYPE, type);
        fragment.setArguments(args);
        fragment.mReplyType = type;
        fragment.mReplyId = id;
        return fragment;
    }

    @Override
    protected String onGetUrl() {
        String url;
        switch (mType) {
            case WebConstans.Type.TYPE_DIARY:
                url = WebConstans.BASE_URL + WebConstans.Action.ACTION_DIARY;
                break;
            case WebConstans.Type.TYPE_TOPIC:
                url = WebConstans.BASE_URL + WebConstans.Action.ACTION_TOPIC;
                break;
            default:
                throw new IllegalArgumentException("PostDetailFragment mType is not compatible");
        }
        return url;
    }

    @Override
    protected String onGetUrlParams() {
        Map<String, String> params = new ArrayMap<>();
        params.put("id", String.valueOf(mId));
        return HttpReq.encodeParameters(params, "utf-8");
    }

    @Override
    protected String onSetTitle() {
        return null;
    }

    @Override
    protected View onBindBottomLayout(LayoutInflater inflater) {
        if(mType == WebConstans.Type.TYPE_DIARY) {
            getActivity().setTitle("日记详情");
        }else{
            getActivity().setTitle("话题详情");
        }

        mBottomView = inflater.inflate(R.layout.layout_post_detail_bottom0, null);
        mSendBtn= (ImageButton) mBottomView.findViewById(R.id.button_send);
        mContentEdit = (EditText) mBottomView.findViewById(R.id.edit_comment);
        mLikeBtn = (ImageButton) mBottomView.findViewById(R.id.button_like);

//        mContentEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CommentFragment fragment = CommentFragment.newInstance(mId, mType);
//                fragment.setTargetFragment(PostDetailFragment.this, REQUEST_CODE_COMMENT);
//                fragment.show(getActivity().getSupportFragmentManager(), "comment");
//            }
//        });
        mLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostBiz.like(String.valueOf(mId), hasLike, new ResponseListener<AppResponse>() {
                    @Override
                    public void onResponse(AppResponse response) {
                        if(hasLike) {
                            ToastUtil.showMessage("取消点赞成功");
                            mLikeBtn.setImageResource(R.mipmap.icon_has_not_like);
                        }else{
                            ToastUtil.showMessage("点赞成功");
                            mLikeBtn.setImageResource(R.mipmap.icon_has_like);
                        }
                        hasLike = !hasLike;
                        Map<String, String> params = new ArrayMap<String, String>();
                        params.put("hasLike", String.valueOf(hasLike));
                        BaseNativeResponse<SwitchLike> result = new BaseNativeResponse<>();
                        result.code = 0;
                        result.msg = "ok";
                        result.data = new SwitchLike();
                        result.data.hasLike = hasLike;

                        callJsMethod(JsNativeBiz.JS_METHOD_G_SWITCH_LIKE, result.toString());
                    }
                });
            }
        });

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContentEdit.getText().toString().length() < 5){
                    ToastUtil.showMessage("字数不足5字");
                    return;
                }
                PostBiz.comment(mReplyId, mReplyType, mContentEdit.getText().toString(),
                        new ResponseListener<AppResponse<BaseModel>>() {
                    @Override
                    public void onResponse(AppResponse<BaseModel> response) {
                        ToastUtil.showMessage("回复成功");

                        PostComment postComment = new PostComment();
                        postComment.content = mContentEdit.getText().toString();
                        postComment.createTime = System.currentTimeMillis() / 1000;
                        postComment.subject = mReplyType;
                        postComment.subjectId = mReplyId;
                        postComment.id = response.data.id;
                        postComment.toUserName = mReplyTo;
                        onCommentFinish(postComment);

                        mContentEdit.setText("");
                        mContentEdit.clearFocus();
                        UIUtil.hideSoftKeyboard(getActivity());
                    }
                });
            }
        });

        mContentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    onGetFocus();
                }else{
                    onLoseFocus();
                }
            }
        });



        return mBottomView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected View onBindHeaderLayout(LayoutInflater inflater) {
        return null;
    }

    private void onLoseFocus(){
        LogUtil.i(TAG, "EditText lost focus");
        mSendBtn.setVisibility(View.GONE);
        mLikeBtn.setVisibility(View.VISIBLE);
    }

    private void onGetFocus(){
        LogUtil.i(TAG, "EditText get focus");
        mSendBtn.setVisibility(View.VISIBLE);
        mLikeBtn.setVisibility(View.GONE);
    }

    private void onGetFocus(long id, String toUser){
        onGetFocus();
        mReplyId = id;
        mReplyTo = toUser;
        mReplyType = "comment";
        mContentEdit.setHint("回复:" + toUser);
        UIUtil.showSoftKeyBoard(getActivity(), mContentEdit);
    }

    @Override
    protected BaseWebChromeClient onSetWebChromeClient() {
        return new BaseWebChromeClient();
    }

    @Override
    public void onAddWebActionListener(BaseWebViewClient client) {
        super.onAddWebActionListener(client);
        //Js调用本地评论
        client.addActionListener(new WebActionListener<JsCallReply>(JsNativeBiz.ACTION_OPEN_COMMENT_PANEL,
                JsCallReply.class) {
            @Override
            public void execute(JsCallReply d) {
                JsCallReply data = d;
                mReplyId = d.data.subjectId;
                mReplyType = d.data.subject;
                mReplyTo = d.data.toUserName;
                onGetFocus(d.data.subjectId, d.data.toUserName);


//                CommentFragment fragment = CommentFragment.newInstance(data.data.subjectId,
//                        data.data.subject, data.data.toUserId, data.data.toUserName);
                //fragment.setTargetFragment(PostDetailFragment.this, WebViewFragment.REQUEST_CODE_COMMENT);
                //fragment.show(PostDetailFragment.this.getActivity().getSupportFragmentManager(), "comment");
            }
        });

        //Js传递PostDetail
        client.addActionListener(new WebActionListener<JsCallPostDetail>(
                JsNativeBiz.ACTION_POST_DETAIL_DATA,
                new TypeToken<JsCallPostDetail>(){}.getType()) {
            @Override
            public void execute(JsCallPostDetail data) {
                hasLike = data.data.isLike;
                if(data.data.isLike){
                    mLikeBtn.setImageResource(R.mipmap.icon_has_like);
                }else{
                    mLikeBtn.setImageResource(R.mipmap.icon_has_not_like);
                }
            }
        });
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
            //postComment.toUserId = mUserId;
            //postComment.toUserName = mUserName;
        }

        BaseNativeResponse<PostComment> comment = new BaseNativeResponse<>();
        comment.code = 0;
        comment.msg = "ok";
        comment.data = postComment;

        String result = comment.toString();
        callJsMethod(JsNativeBiz.JS_METHOD_G_renderPostComment, result);
    }

    @Override
    public void onResume() {
        super.onResume();
        ToastUtil.showMessage(String.format("PostDetail Id is %d", mId));
    }
}
