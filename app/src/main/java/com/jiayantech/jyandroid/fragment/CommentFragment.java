package com.jiayantech.jyandroid.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.JsNativeBiz;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.model.web.PostComment;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by liangzili on 15/7/3.
 */
public class CommentFragment extends DialogFragment{
    public static final String EXTRA_SUBJECT = "extra_subject";
    public static final String EXTRA_SUBJECT_ID = "extra_subject_id";
    public static final String EXTRA_TO_USER_ID = "extra_to_user_id";
    public static final String EXTRA_TO_USER_NAME = "extra_to_user_name";

    private long mSubjectId;
    private String mSubject;
    private long mToUserId;
    private String mToUserName;

    public static CommentFragment newInstance(long subjectId, String subject){
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_SUBJECT_ID, subjectId);
        args.putString(EXTRA_SUBJECT, subject);

        fragment.setArguments(args);
        return fragment;
    }

    public static CommentFragment newInstance(long subjectId, String subject, long toUserId,
                                              String toUserName){
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_SUBJECT_ID, subjectId);
        args.putString(EXTRA_SUBJECT, subject);
        args.putLong(EXTRA_TO_USER_ID, toUserId);
        args.putString(EXTRA_TO_USER_NAME, toUserName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubjectId = getArguments().getLong(EXTRA_SUBJECT_ID);
        mSubject = getArguments().getString(EXTRA_SUBJECT);

        mToUserId = getArguments().getLong(EXTRA_TO_USER_ID, -1);
        mToUserName = getArguments().getString(EXTRA_TO_USER_NAME);

        if(-1 == mSubjectId || null == mSubject){
            ToastUtil.showMessage(getActivity(), "wrong id or type");
            dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText comment = new EditText(getActivity());
        if(mToUserName != null){
            comment.setHint(getResources().getString(R.string.hint_reply_to,
                    new Object[]{mToUserName}));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("评论")
                .setView(comment)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommentFragment.this.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (comment.getText() == null || comment.getText().equals("")) {
                            ToastUtil.showMessage(getActivity(), "不能为空");
                            CommentFragment.this.dismiss();
                        }
                        PostBiz.comment(mSubjectId, mSubject, comment.getText().toString()
                                , new ResponseListener<AppResponse<BaseModel>>(

                        ) {
                            @Override
                            public void onResponse(AppResponse<BaseModel> baseModelAppResponse) {
                                CommentFragment.this.dismiss();
                                ToastUtil.showMessage("评论成功啦 "
                                        + baseModelAppResponse.data.id);
                                PostComment postComment = new PostComment();
                                postComment.content = comment.getText().toString();
                                postComment.createTime = System.currentTimeMillis() / 1000;
                                postComment.subject = mSubject;
                                postComment.subjectId = mSubjectId;
                                postComment.id = baseModelAppResponse.data.id;
                                if(mToUserId != -1){
                                    postComment.toUserId = mToUserId;
                                    postComment.toUserName = mToUserName;
                                }

                                EventBus.getDefault().post(postComment);
                            }

                        });
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        ToastUtil.showMessage("subject: " + mSubject + " subjectId: " + mSubjectId +
            "mToUserId:  " + mToUserId + " toUsername: " + mToUserName);

        return dialog;
    }
}
