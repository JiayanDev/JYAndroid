package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/2.
 */
public class PostAdapter extends BaseSimpleModelAdapter<Post>{
    Context mContext;
//    public PostAdapter(List<Post> list) {
//        super(list);
//    }

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
        public void onBind(Post item, int position) {
            mUsername.setText(String.valueOf(item.id));
            mContent.setText(item.content);
            mThumbsUpCount.setText(mContext.getResources().getString
                    (R.string.thumbs_up_count, new Object[]{String.valueOf(item.likeCount)}));
            mCommentCount.setText(mContext.getResources().getString
                    (R.string.comment_count, new Object[]{String.valueOf(item.commentCount)}));
        }
    }
}
