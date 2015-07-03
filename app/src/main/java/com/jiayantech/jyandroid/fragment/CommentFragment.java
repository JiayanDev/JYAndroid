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

import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;

/**
 * Created by liangzili on 15/7/3.
 */
public class CommentFragment extends DialogFragment{
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_CONTENT = "extra_content";

    private long mId;
    private String mType;
    private String mContent;

    public static CommentFragment newInstance(long id, String type){
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_ID, id);
        args.putString(EXTRA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(EXTRA_ID, -1);
        mType = getArguments().getString(EXTRA_TYPE, null);
        if(-1 == mId || null == mType){
            ToastUtil.showMessage(getActivity(), "wrong id or type");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText comment = new EditText(getActivity());

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
                        PostBiz.comment(String.valueOf(mId), mType, comment.getText().toString()
                                , new ResponseListener<AppResponse<BaseModel>>(

                        ) {
                            @Override
                            public void onResponse(AppResponse<BaseModel> baseModelAppResponse) {
                                CommentFragment.this.dismiss();
                                ToastUtil.showMessage("评论成功啦 "
                                        + baseModelAppResponse.data.id);
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
        return dialog;
    }
}
