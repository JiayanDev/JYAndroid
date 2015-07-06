package com.jiayantech.jyandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.biz.PostBiz;
import com.jiayantech.jyandroid.fragment.CommentFragment;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseActivity;
import com.jiayantech.library.base.BaseModel;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.AppResponse;
import com.jiayantech.library.http.ResponseListener;
import com.jiayantech.library.utils.ToastUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/2.
 */
public class PostAdapter extends BaseSimpleModelAdapter<Post>{
    Context mContext;

    public PostAdapter(List<Post> list, Context context){
        super(list);
        mContext = context;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_topic, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Post> {
        public ImageView mAvatar;
        public TextView mUsername;
        public ImageView mPhoto;
        public TextView mContent;
        public TextView mThumbsUpCount;
        public TextView mCommentCount;

        public ViewHolder(View itemView) {
            super(itemView);
            mAvatar = (ImageView)itemView.findViewById(R.id.avatar);
            mUsername = (TextView)itemView.findViewById(R.id.username);
            mPhoto = (ImageView)itemView.findViewById(R.id.photo);
            mContent = (TextView)itemView.findViewById(R.id.content);
            mThumbsUpCount = (TextView)itemView.findViewById(R.id.thumbs_up);
            mCommentCount = (TextView)itemView.findViewById(R.id.comment);
        }

        @Override
        public void onBind(final Post item, int position) {
            mUsername.setText(String.valueOf(item.id));
            mContent.setText(item.content);
            mThumbsUpCount.setText(mContext.getResources().getString
                    (R.string.thumbs_up_count, new Object[]{String.valueOf(item.likeCount)}));
            mCommentCount.setText(mContext.getResources().getString
                    (R.string.comment_count, new Object[]{String.valueOf(item.commentCount)}));
            mThumbsUpCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostBiz.like(String.valueOf(item.id), PostBiz.MODE_LIKE, new ResponseListener<AppResponse>(){

                        @Override
                        public void onResponse(AppResponse baseModelAppResponse) {
                            ToastUtil.showMessage(baseModelAppResponse.toString());
                        }
                    });
                }
            });

            mCommentCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    CommentFragment fragment = CommentFragment.newInstance(item.id, item.type);
                    fragment.show(((BaseActivity)mContext).getSupportFragmentManager(), "comment");
                }
            });
        }


    }
}
