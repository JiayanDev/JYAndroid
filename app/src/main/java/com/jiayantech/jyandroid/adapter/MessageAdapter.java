package com.jiayantech.jyandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.umeng_push.model.BasePushMessage;
import com.jiayantech.umeng_push.model.JumpToPageData;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class MessageAdapter extends BaseSimpleModelAdapter<BasePushMessage> {
    public static final int TYPE_REPLY = 0;
    public static final int TYPE_NORMAL = 1;

    public MessageAdapter(List<BasePushMessage> list) {
        super(list);
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ReplyViewHolder(viewGroup, R.layout.item_message_normal, this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REPLY) {
            return new ReplyViewHolder(parent, R.layout.item_message_reply, this);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).code <= UmengPushManager.CODE_COMMENT_COMMENTED) {
            return TYPE_REPLY;
        } else {
            return TYPE_NORMAL;
        }
    }


    public static class ReplyViewHolder extends NormalViewHolder {
        private TextView mTxtType;
        private TextView mTxtReplyContent;

        public ReplyViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ReplyViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<BasePushMessage> adapter) {
            super(parent, layoutId, adapter);
            mTxtType = (TextView)itemView.findViewById(R.id.txt_type);
            mTxtReplyContent = (TextView)itemView.findViewById(R.id.txt_reply_content);
        }

        @Override
        public void onBind(BasePushMessage item, int position) {
            super.onBind(item, position);
            mTxtType.setText("回复了我的" + item.subject);
            mTxtReplyContent.setText(item.subjectContent);
        }
    }

    public static class NormalViewHolder extends BaseSimpleModelAdapter.ViewHolder<BasePushMessage>
        implements View.OnClickListener{
        private ImageView mImageAvatar;
        private TextView mTxtUsername;
        private TextView mTxtDate;
        private TextView mTxtContent;

        private String action;
        private long id;
        private String url;

        public NormalViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<BasePushMessage> adapter) {
            super(parent, layoutId, adapter);
            mImageAvatar = (ImageView)itemView.findViewById(R.id.img_avatar);
            mTxtUsername = (TextView)itemView.findViewById(R.id.txt_username);
            mTxtDate = (TextView)itemView.findViewById(R.id.txt_date);
            mTxtContent = (TextView)itemView.findViewById(R.id.txt_content);
        }

        @Override
        public void onBind(BasePushMessage item, int position) {
            super.onBind(item, position);
            if(item.data instanceof JumpToPageData){
                action = ((JumpToPageData) item.data).page;
                id = ((JumpToPageData) item.data).id;
            }else{
                action = item.action;
            }
            itemView.setOnClickListener(this);

            BitmapBiz.display(mImageAvatar, item.fromUserAvatar);
            mTxtUsername.setText(item.fromUserName);
            mTxtDate.setText(TimeUtil.stamp2Date(item.createTime * 1000));
            mTxtContent.setText(item.commentContent);
        }

        @Override
        public void onClick(View v) {
            UmengPushManager.getInstance().handleClickAction(action, id, url);
        }
    }


}
