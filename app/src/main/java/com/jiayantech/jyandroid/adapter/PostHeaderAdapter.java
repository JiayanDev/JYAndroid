package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.jyandroid.activity.WebViewActivity;
import com.jiayantech.jyandroid.activity.WebViewActivityOverlay;
import com.jiayantech.jyandroid.fragment.webview.WebConstans;
import com.jiayantech.jyandroid.misc.UIMisc;
import com.jiayantech.jyandroid.model.HomePagePost;
import com.jiayantech.jyandroid.model.Post;
import com.jiayantech.jyandroid.widget.AdaptiveGridView;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.http.HttpConfig;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.UIUtil;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by 健兴 on 2015/11/15.
 */
public class PostHeaderAdapter extends BaseSimpleModelAdapter<Post> {
    private static final int TYPE_HEADER = 4;

    Context mContext;
    Drawable mFemaleIcon;
    Drawable mMaleIcon;

    private View mHeader;

    public PostHeaderAdapter(Context context, View header) {
        super(null);
        mContext = context;
        mHeader = header;
        setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Post> adapter, int position, Post item) {
                if (position > 0) {
                    WebViewActivity.launchActivity(mContext, item.id, item.type);
                }
            }
        });

        mFemaleIcon = mContext.getResources().getDrawable(R.mipmap.icon_female);
        mFemaleIcon.setBounds(0, 0, UIUtil.dip2px(16), UIUtil.dip2px(16));
        mMaleIcon = mContext.getResources().getDrawable(R.mipmap.icon_male);
        mMaleIcon.setBounds(0, 0, UIUtil.dip2px(16), UIUtil.dip2px(16));
    }

    @Override
    public int getAdapterItemCount() {
        return super.getAdapterItemCount() + 1;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Post getItem(int position) {
        if (position == 0) {
            return null;
        }
        return super.getItem(position - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(mHeader);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(mContext, parent, R.layout.item_diary, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0 && position < getAdapterItemCount()) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mItem = getItem(position);
            onBind(viewHolder, getItem(position), position - 1);
        }
    }


    public static class HeaderHolder extends BaseSimpleModelAdapter.ViewHolder {
        public HeaderHolder(View header) {
            super(header);
        }
    }

    public class ViewHolder extends BaseSimpleModelAdapter.ViewHolder<Post> {
        public long id;

        public Context mContext;
        public ImageView mAvatar;
        public TextView mUsername;
        public TextView mDate;
        public AdaptiveGridView mPhotoLayout;
        public TextView mContent;
        public TextView mThumbsUpCount;
        public TextView mCommentCount;
        public ImageView mType;
        public TagGroup mTagGroupCategory;
        public ImageView mRoleTag;

        public PhotoAdapter mPhotoAdapter;

        public ViewHolder(Context context, ViewGroup parent, int layoutId) {
            this(context, parent, layoutId, null);
        }

        public ViewHolder(Context context, ViewGroup parent, int layoutId, BaseSimpleModelAdapter<Post> adapter) {
            super(parent, layoutId, adapter);
            mContext = context;
            mAvatar = (ImageView) itemView.findViewById(R.id.avatar);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mPhotoLayout = (AdaptiveGridView) itemView.findViewById(R.id.layout_photos);
            mContent = (TextView) itemView.findViewById(R.id.content);
            mThumbsUpCount = (TextView) itemView.findViewById(R.id.thumbs_up);
            mCommentCount = (TextView) itemView.findViewById(R.id.comment);
            mType = (ImageView) itemView.findViewById(R.id.ic_type);
            mTagGroupCategory = (TagGroup) itemView.findViewById(R.id.tag_group_category);
            mDate = (TextView) itemView.findViewById(R.id.txt_date);
            mRoleTag = (ImageView) itemView.findViewById(R.id.img_tag);

            mPhotoAdapter = new PhotoAdapter(mContext);

            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivityOverlay.launchActivity(mContext, id,
                            WebConstans.Type.TYPE_PERSONAL_PAGE);
                }
            });
        }

        @Override
        public void onBind(Post item, int position) {
            if (TextUtils.isEmpty(item.avatar)) {
                mAvatar.setImageResource(HttpConfig.DEFAULT_IMAGE_ID);
            } else {
                BitmapBiz.display(mAvatar, item.avatar, 150);
            }

            id = item.userId;

            mUsername.setText(String.valueOf(item.userName) + "    " + position);
            mContent.setText(item.content);
            mThumbsUpCount.setText(mContext.getResources().getString
                    (R.string.thumbs_up_count, new Object[]{String.valueOf(item.likeCount)}));
            mCommentCount.setText(mContext.getResources().getString
                    (R.string.comment_count, new Object[]{String.valueOf(item.commentCount)}));

            mTagGroupCategory.setTags(item.getCategoryNamesArray());

            mPhotoLayout.setAdapter(mPhotoAdapter);
            ((PhotoAdapter) mPhotoLayout.getAdapter()).setPhotoList(
                    item.photoes == null ? new ArrayList<String>() : item.photoes);

            mDate.setText(TimeUtil.stamp2MonthDay((long
                    ) item.createTime * 1000));
            if (item.gender == 0) {
                mUsername.setCompoundDrawables(null, null, mFemaleIcon, null);
            } else {
                mUsername.setCompoundDrawables(null, null, mMaleIcon, null);
            }

            UIMisc.setRoleTag(item.role, mRoleTag);
        }
    }
}


