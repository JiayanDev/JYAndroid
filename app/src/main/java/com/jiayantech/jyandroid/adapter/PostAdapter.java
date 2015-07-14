package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/2.
 */
public class PostAdapter extends BaseSimpleModelAdapter<Post> {
    Context mContext;

    public PostAdapter(List<Post> list, Context context) {
        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Post> adapter, int position, Post item) {
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra(WebViewFragment.EXTRA_ID, item.id);
                intent.putExtra(WebViewFragment.EXTRA_TYPE, WebViewFragment.TYPE_TOPIC);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(mContext, viewGroup, R.layout.item_topic, this);
    }


    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Post> {
        public Context mContext;
        public ImageView mAvatar;
        public TextView mUsername;
        public ImageView mPhoto;
        public TextView mContent;
        public TextView mThumbsUpCount;
        public TextView mCommentCount;
        //public ViewHolderOnClickListener mListener;

        public ViewHolder(Context context, ViewGroup parent, int layoutId) {
            this(context, parent, layoutId, null);
            // mListener = listener;
            //itemView.setOnClickListener(listener);
        }

        public ViewHolder(Context context, ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Post> adapter) {
            super(parent, layoutId, adapter);
            mContext = context;
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mPhoto = (ImageView) itemView.findViewById(R.id.photo);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mThumbsUpCount = (TextView) itemView.findViewById(R.id.thumbs_up);
            mCommentCount = (TextView) itemView.findViewById(R.id.comment);
        }

        @Override
        public void onBind(Post item, int position) {
            mUsername.setText(String.valueOf(item.id));
            mContent.setText(item.content);
            mThumbsUpCount.setText(mContext.getResources().getString
                    (R.string.thumbs_up_count, new Object[]{String.valueOf(item.likeCount)}));
            mCommentCount.setText(mContext.getResources().getString
                    (R.string.comment_count, new Object[]{String.valueOf(item.commentCount)}));
//            mThumbsUpCount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    PostBiz.like(String.valueOf(item.id), PostBiz.MODE_LIKE,
//                            new ResponseListener<AppResponse>() {
//
//                                @Override
//                                public void onResponse(AppResponse baseModelAppResponse) {
//                                    ToastUtil.showMessage(baseModelAppResponse.toString());
//                                }
//                            });
//                }
//            });

//            mCommentCount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CommentFragment fragment = CommentFragment.newInstance(item.id, item.type);
//                    fragment.show(((BaseActivity) mContext).getSupportFragmentManager(), "comment");
//                }
//            });
            //mListener.setPosition(position);
        }

    }
}
