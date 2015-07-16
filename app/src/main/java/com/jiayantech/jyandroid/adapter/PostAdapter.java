package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.PostDetailActivity;
import com.jiayantech.jyandroid.customwidget.webview.WebViewFragment;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
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
                intent.putExtra(WebViewFragment.EXTRA_USER_ID, item.userId);
                intent.putExtra(WebViewFragment.EXTRA_USERNAME, item.userName);
                intent.putExtra(WebViewFragment.EXTRA_TYPE, item.type);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(mContext, viewGroup, R.layout.item_post, this);
    }

    public static class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Post> {
        public Context mContext;
        public ImageView mAvatar;
        public TextView mUsername;
        public LinearLayout mPhotoLayout;
        public TextView mContent;
        public TextView mThumbsUpCount;
        public TextView mCommentCount;
        public ImageView mType;
        public TextView txt_category;

        public ViewHolder(Context context, ViewGroup parent, int layoutId) {
            this(context, parent, layoutId, null);
        }

        public ViewHolder(Context context, ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Post> adapter) {
            super(parent, layoutId, adapter);
            mContext = context;
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mPhotoLayout = (LinearLayout) itemView.findViewById(R.id.layout_photos);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mThumbsUpCount = (TextView) itemView.findViewById(R.id.thumbs_up);
            mCommentCount = (TextView) itemView.findViewById(R.id.comment);
            mType = (ImageView) itemView.findViewById(R.id.ic_type);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }

        @Override
        public void onBind(Post item, int position) {
            mUsername.setText(String.valueOf(item.userName));
            mContent.setText(item.content);
            mThumbsUpCount.setText(mContext.getResources().getString
                    (R.string.thumbs_up_count, new Object[]{String.valueOf(item.likeCount)}));
            mCommentCount.setText(mContext.getResources().getString
                    (R.string.comment_count, new Object[]{String.valueOf(item.commentCount)}));
            mPhotoLayout.removeAllViews();
            if (item.photoes != null) {
                for (int i = 0; i < item.photoes.length && i < 3; i++) {
                    ImageView image = (ImageView) LayoutInflater.
                            from(mContext).inflate(R.layout.layout_photo, mPhotoLayout, false);
                    mPhotoLayout.addView(image);
                    BitmapBiz.display(image, item.photoes[i]);
                }
            }

            if (item.type.equals("diary")) {
                mType.setImageResource(R.drawable.ic_post_type_diary);
            } else {
                mType.setImageResource(R.drawable.ic_post_type_topic);
            }
            txt_category.setText(item.getCategoryNames());

        }


    }
}
