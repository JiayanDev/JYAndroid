package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
 * Created by liangzili on 15/7/2.
 */
public class PostAdapter extends BaseSimpleModelAdapter<Post> {
    Context mContext;
    Drawable mFemaleIcon;
    Drawable mMaleIcon;

    public PostAdapter(List<Post> list, Context context) {
        super(list);
        mContext = context;
        setOnItemClickListener(new OnItemClickListener<Post>() {
            @Override
            public void onItemClick(BaseSimpleModelAdapter<Post> adapter, int position, Post item) {
                    WebViewActivity.launchActivity(mContext, item.id, item.type);
            }
        });

        mFemaleIcon = mContext.getResources().getDrawable(R.mipmap.icon_female);
        mFemaleIcon.setBounds(0, 0, UIUtil.dip2px(16), UIUtil.dip2px(16));
        mMaleIcon = mContext.getResources().getDrawable(R.mipmap.icon_male);
        mMaleIcon.setBounds(0, 0, UIUtil.dip2px(16), UIUtil.dip2px(16));
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(mContext, viewGroup, R.layout.item_diary, this);
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

        }

        @Override
        public void onBind(final Post item, int position) {
            if (TextUtils.isEmpty(item.avatar)) {
                mAvatar.setImageResource(HttpConfig.DEFAULT_IMAGE_ID);
            } else {
                BitmapBiz.display(mAvatar, item.avatar);
            }

            id = item.userId;

            mUsername.setText(String.valueOf(item.userName));
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

            mAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.role != null && item.role.equals(UIMisc.ROLE_ANGEL)) {
                        WebViewActivityOverlay.launchActivity(mContext, id,
                                WebConstans.Type.TYPE_PERSONAL_PAGE);
                    }
                }
            });
        }

//        private View.OnClickListener mImageClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mItem == null && mItem.photoes.size() <= 0) {
//                    return;
//                }
//                Integer position = (Integer) v.getTag();
//                PhotosActivity.start(mContext, mItem.content, mItem.photoes, position);
//            }
//        };
    }
}
